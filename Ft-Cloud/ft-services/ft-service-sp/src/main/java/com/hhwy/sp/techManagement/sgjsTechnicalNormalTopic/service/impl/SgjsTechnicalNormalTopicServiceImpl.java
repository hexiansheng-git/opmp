package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.handler.HandleHelper;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCostDTO;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.service.ISgjsTechnicalNormalTopicCostService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.mapper.SgjsTechnicalNormalTopicMapper;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.ISgjsTechnicalNormalTopicService;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;
import com.hhwy.utils.idworker.IdWorker;

/***
 * 功能描述: 科技管理 - 一般课题研发管理
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Service
public class SgjsTechnicalNormalTopicServiceImpl implements ISgjsTechnicalNormalTopicService {

    @Autowired
    private SgjsTechnicalNormalTopicMapper sgjsTechnicalNormalTopicMapper;
    @Autowired
    private ISgjsTechnicalNormalTopicCostService sgjsTechnicalNormalTopicCostService;


    public SgjsTechnicalNormalTopic getSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        return sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    public List<SgjsTechnicalNormalTopic> getSgjsTechnicalNormalTopicList(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        List<SgjsTechnicalNormalTopic> resultList = sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopic);
        if (CollUtil.isEmpty(resultList)) return resultList;
        //子表查询
        SgjsTechnicalNormalTopicCost param = new SgjsTechnicalNormalTopicCost();
        param.setIds(resultList.stream().map(SgjsTechnicalNormalTopic::getId).toArray(Long[]::new));
        List<SgjsTechnicalNormalTopicCost> chidrenList = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCostList(param);
        if (CollUtil.isEmpty(chidrenList)) return resultList;
        //子表数据拼到主表，同时需要处理行列转换导致的列数不同问题，因为每个课题的研发预算年的数量可能不同
        SgjsTechnicalNormalTopicCost maxYear = chidrenList.stream()
                .filter(p -> null != p.getYear())
                .max(Comparator.comparingInt(SgjsTechnicalNormalTopicCost::getYear)).get();
        SgjsTechnicalNormalTopicCost minYear = chidrenList.stream()
                .filter(p -> null != p.getYear())
                .min(Comparator.comparingInt(SgjsTechnicalNormalTopicCost::getYear)).get();
        //得到最小年份与最大年份的区间列表，填入resultList每个对象中
        int[] range = NumberUtil.range(minYear.getYear(), maxYear.getYear());

        Map<Long, List<SgjsTechnicalNormalTopicCost>> childMap = chidrenList.stream()
                .collect(Collectors.groupingBy(SgjsTechnicalNormalTopicCost::getForeignId));
        Map<Long, SgjsTechnicalNormalTopic> mainMap = resultList.stream()
                .collect(Collectors.toMap(SgjsTechnicalNormalTopic::getId, value -> value));
        //遍历主表数据，将年份，预算数据填入，并对未包含全部年份的主表数据填充缺失年份和预算，预算默认0
        for (Map.Entry<Long, SgjsTechnicalNormalTopic> next : mainMap.entrySet()) {
            Long key = next.getKey();
            SgjsTechnicalNormalTopic value = next.getValue();
            List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCosts = childMap.get(key);
            List<SgjsTechnicalNormalTopicCost> objects = new ArrayList<>();
            if (CollUtil.isNotEmpty(sgjsTechnicalNormalTopicCosts) && sgjsTechnicalNormalTopicCosts.size() == range.length) {
                //当前主表数有年份及预算数据,并且包含了全部年份
                sgjsTechnicalNormalTopicCosts.forEach(p -> {
                    objects.add(p);
                });
                value.setChildList(objects);
                continue;
            }
            Map<String, BigDecimal> map = new HashMap<>();
            if (CollUtil.isEmpty(sgjsTechnicalNormalTopicCosts)) {
                //当前主表数没有年份及预算数据
                for (int i = 0; i < range.length; i++) {
                    SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost = new SgjsTechnicalNormalTopicCost();
                    sgjsTechnicalNormalTopicCost.setYear(range[i]);
                    sgjsTechnicalNormalTopicCost.setRdCost(BigDecimal.ZERO);
                    objects.add(sgjsTechnicalNormalTopicCost);
                }
                value.setChildList(objects);
                continue;
            }
            //当前主表有年份及预算数据,但部分缺失
            Set<Integer> collect = sgjsTechnicalNormalTopicCosts.stream().map(SgjsTechnicalNormalTopicCost::getYear).collect(Collectors.toSet());
            objects.addAll(sgjsTechnicalNormalTopicCosts);
            for (int i = 0; i < range.length; i++) {
                if (!collect.contains(range[i])) {
                    SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost = new SgjsTechnicalNormalTopicCost();
                    sgjsTechnicalNormalTopicCost.setYear(range[i]);
                    sgjsTechnicalNormalTopicCost.setRdCost(BigDecimal.ZERO);
                    objects.add(sgjsTechnicalNormalTopicCost);
                }
            }
            value.setChildList(objects);
        }
        return new ArrayList<>(mainMap.values());
    }

    @Transactional
    public int insertSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setId(IdWorker.createId());
        sgjsTechnicalNormalTopic.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    @Transactional
    public int insertSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList) {
        if (CollUtil.isEmpty(sgjsTechnicalNormalTopicList)) {
            SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic = new SgjsTechnicalNormalTopic();
            sgjsTechnicalNormalTopicMapper.deleteSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
            SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost = new SgjsTechnicalNormalTopicCost();
            sgjsTechnicalNormalTopicCostService.deleteSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCost);
            return 1;
        }
        List<SgjsTechnicalNormalTopicCost> childSave = new ArrayList<>();
        for (SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic : sgjsTechnicalNormalTopicList) {
            Long id = IdWorker.createId();
            sgjsTechnicalNormalTopic.setId(id);
            sgjsTechnicalNormalTopic.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalNormalTopic.setCreateTime(DateUtils.getNowDate());
            List<SgjsTechnicalNormalTopicCost> childList = sgjsTechnicalNormalTopic.getChildList();
            childList.forEach(p -> p.setForeignId(id));
            childSave.addAll(childList);
        }
        sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicList);
        return sgjsTechnicalNormalTopicCostService.insertSgjsTechnicalNormalTopicCostList(childSave);
    }

    @Transactional
    public int updateSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.updateSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    @Transactional
    public int updateSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList) {
        for (SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic : sgjsTechnicalNormalTopicList) {
            sgjsTechnicalNormalTopic.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalNormalTopic.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalNormalTopicMapper.updateSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicList);
    }

    @Transactional
    public int deleteSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.deleteSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    @Transactional
    public int deleteSgjsTechnicalNormalTopicByPks(List<Long> sgjsTechnicalNormalTopicPkList) {
        return sgjsTechnicalNormalTopicMapper.deleteSgjsTechnicalNormalTopicByPks(sgjsTechnicalNormalTopicPkList);
    }
}
