package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;
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
public class SgjsTechnicalNormalTopicServiceImpl implements ISgjsTechnicalNormalTopicService{

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
        //字表查询
        SgjsTechnicalNormalTopicCost param = new SgjsTechnicalNormalTopicCost();
        param.setIds(resultList.stream().map(SgjsTechnicalNormalTopic::getId).toArray(Long[]::new));
        List<SgjsTechnicalNormalTopicCost> chidrenList = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCostList(param);
        if (CollUtil.isEmpty(chidrenList)) return resultList;
        Map<Long, List<SgjsTechnicalNormalTopicCost>> childrenMap = chidrenList.stream().collect(Collectors.groupingBy(SgjsTechnicalNormalTopicCost::getForeignId));
        return resultList;
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
        for (SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic : sgjsTechnicalNormalTopicList) {
            sgjsTechnicalNormalTopic.setId(IdWorker.createId());
            sgjsTechnicalNormalTopic.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalNormalTopic.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicList);
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
