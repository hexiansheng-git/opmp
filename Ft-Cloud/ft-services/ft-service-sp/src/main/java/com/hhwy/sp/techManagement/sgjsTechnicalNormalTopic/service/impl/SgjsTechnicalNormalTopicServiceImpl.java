package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopicDTO;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.mapper.SgjsTechnicalNormalTopicMapper;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.ISgjsTechnicalNormalTopicService;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.service.ISgjsTechnicalNormalTopicCostService;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PmServiceApi pmServiceApi;
    private static ProjectDto projectInfo;

    //向总部推送数据用
    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 2, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(5));

    private ProjectDto getProjectDto(){
        if (projectInfo != null) return projectInfo;
        return pmServiceApi.getProjectDto();
    }

    public SgjsTechnicalNormalTopic getSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        return sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    public List<SgjsTechnicalNormalTopic> getSgjsTechnicalNormalTopicList(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        //主表查询
        List<SgjsTechnicalNormalTopic> resultList = sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopic);
        if (CollUtil.isEmpty(resultList)) return resultList;
        //子表查询
        SgjsTechnicalNormalTopicCost param = new SgjsTechnicalNormalTopicCost();
        param.setIds(resultList.stream().map(SgjsTechnicalNormalTopic::getId).toArray(Long[]::new));
        List<SgjsTechnicalNormalTopicCost> chidrenList = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCostList(param);
        if (CollUtil.isEmpty(chidrenList)) return resultList;
        //合并
        Map<Long, List<SgjsTechnicalNormalTopicCost>> childMap = chidrenList.stream()
                .collect(Collectors.groupingBy(SgjsTechnicalNormalTopicCost::getForeignId));
        Map<Long, SgjsTechnicalNormalTopic> mainMap = resultList.stream()
                .collect(Collectors.toMap(SgjsTechnicalNormalTopic::getId, value -> value));
        for (Map.Entry<Long, SgjsTechnicalNormalTopic> next : mainMap.entrySet()) {
            Long id = next.getKey();
            SgjsTechnicalNormalTopic value = next.getValue();
            List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCosts = childMap.get(id);
            sgjsTechnicalNormalTopicCosts.sort(Comparator.comparing(SgjsTechnicalNormalTopicCost::getYear));
            value.setChildList(sgjsTechnicalNormalTopicCosts);
        }
        return new ArrayList<>(mainMap.values());
    }

    //得到给定范围内的整数列表，步进为1
    private int[] getInts(List<SgjsTechnicalNormalTopicCost> chidrenList) {
        if (CollUtil.isEmpty(chidrenList)) return new int[0];
        SgjsTechnicalNormalTopicCost maxYear = chidrenList.stream()
                .filter(p -> null != p.getYear())
                .max(Comparator.comparingInt(SgjsTechnicalNormalTopicCost::getYear)).get();
        SgjsTechnicalNormalTopicCost minYear = chidrenList.stream()
                .filter(p -> null != p.getYear())
                .min(Comparator.comparingInt(SgjsTechnicalNormalTopicCost::getYear)).get();
        //得到最小年份与最大年份的区间列表，填入resultList每个对象中
        return NumberUtil.range(minYear.getYear(), maxYear.getYear());
    }

    @Transactional
    public int insertSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setId(IdWorker.createId());
        sgjsTechnicalNormalTopic.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    //保存
    @Transactional
    public void insertSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList) {
        SgjsTechnicalNormalTopic param = new SgjsTechnicalNormalTopic();
        sgjsTechnicalNormalTopicMapper.deleteSgjsTechnicalNormalTopic(param);
        SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost = new SgjsTechnicalNormalTopicCost();
        sgjsTechnicalNormalTopicCostService.deleteSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCost);
        if (CollUtil.isEmpty(sgjsTechnicalNormalTopicList)) {
            return;
        }
        ProjectDto projectDto = getProjectDto();
        List<SgjsTechnicalNormalTopicCost> childSave = new ArrayList<>();
        for (SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic : sgjsTechnicalNormalTopicList) {
            Long id = IdWorker.createId();
            sgjsTechnicalNormalTopic.setId(id);
            sgjsTechnicalNormalTopic.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalNormalTopic.setCreateTime(DateUtils.getNowDate());
            sgjsTechnicalNormalTopic.setRegionId(projectDto.getRegionId());
            sgjsTechnicalNormalTopic.setRegionName(projectDto.getRegionName());
            sgjsTechnicalNormalTopic.setProjectId(projectDto.getProjectId());
            sgjsTechnicalNormalTopic.setPtVar5(projectDto.getProjectCode());
            List<SgjsTechnicalNormalTopicCost> childList = sgjsTechnicalNormalTopic.getChildList();
            childList.forEach(p -> {
                p.setForeignId(id);
                p.setPtVar5(projectDto.getProjectCode());
            });
            childSave.addAll(childList);
        }
        sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicList);
        sgjsTechnicalNormalTopicCostService.insertSgjsTechnicalNormalTopicCostList(childSave);
        //数据推送总部版
//        executorService.execute(this::doSendGm);
        doSendGm();
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

    @Override
    public void export(HttpServletResponse response, SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) throws Exception {
        //主表源数据
        List<SgjsTechnicalNormalTopic> resultList = sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicParam);
        if (CollUtil.isEmpty(resultList)) return;
        //子表源数据
        SgjsTechnicalNormalTopicCost param = new SgjsTechnicalNormalTopicCost();
        param.setIds(resultList.stream().map(SgjsTechnicalNormalTopic::getId).toArray(Long[]::new));
        List<SgjsTechnicalNormalTopicCost> chidrenList = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCostList(param);
        if (CollUtil.isEmpty(chidrenList)) {
            ExcelUtils<SgjsTechnicalNormalTopic> util = new ExcelUtils<>(SgjsTechnicalNormalTopic.class);
            util.exportExcel(response, resultList, DateUtils.getDate());
            return;
        }
        //表头
        List<List<String>> headList = getHead(SgjsTechnicalNormalTopicDTO.class, chidrenList);
        //数据
        List<List<String>> dataList = getData(resultList, chidrenList);
        EasyExcel.write(response.getOutputStream()).head(headList)
                .sheet(DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN))
                .doWrite(dataList);
    }

    /***
     * 功能描述: easyExcel所需数据组装
     * @param orginList     主表数据
     * @param chidrenList  子表数据
     * @return easyExcel    导出数据
     */
    private List<List<String>> getData(List<SgjsTechnicalNormalTopic> orginList, List<SgjsTechnicalNormalTopicCost> chidrenList) {
        //动态表头集合
        int[] range = getInts(chidrenList);
        //子表按foreign分组
        Map<Long, List<SgjsTechnicalNormalTopicCost>> childMap = chidrenList.stream().collect(Collectors.groupingBy(SgjsTechnicalNormalTopicCost::getForeignId));
        List<List<String>> dataList = new ArrayList<>();
        for (SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic : orginList) {
            List<String> objects = new ArrayList<>();
            //主表数据
            objects.add(sgjsTechnicalNormalTopic.getRegionName());
            objects.add(sgjsTechnicalNormalTopic.getProjectName());
            objects.add(sgjsTechnicalNormalTopic.getTopicCode());
            objects.add(sgjsTechnicalNormalTopic.getTopicName());
            objects.add(sgjsTechnicalNormalTopic.getHighCertificateName());
            objects.add(sgjsTechnicalNormalTopic.getTopicKindName());
            objects.add(sgjsTechnicalNormalTopic.getEcoTargetName());
            objects.add(sgjsTechnicalNormalTopic.getTopicStateName());
            objects.add(sgjsTechnicalNormalTopic.getAchievementKindName());
            objects.add(DateUtil.format(sgjsTechnicalNormalTopic.getStartDate(), DatePattern.CHINESE_DATE_PATTERN));
            objects.add(DateUtil.format(sgjsTechnicalNormalTopic.getEndDate(), DatePattern.CHINESE_DATE_PATTERN));
            //子表无数据
            if (ArrayUtil.isEmpty(range)) {
                objects.add(sgjsTechnicalNormalTopic.getPersonNameList());
                objects.add(sgjsTechnicalNormalTopic.getRemark());
                dataList.add(objects);
                continue;
            }
            //子表数据
            List<SgjsTechnicalNormalTopicCost> listById = childMap.get(sgjsTechnicalNormalTopic.getId());
            //理论上不需要这个判断，为了以防万一（脏数据）；因为range不为空，说明子表有数据，则这里不应该出现为空的情况
            if (CollUtil.isEmpty(listById)) {
                for (int j : range) {
                    objects.add("0.00");
                }
            } else {
                Map<Integer, SgjsTechnicalNormalTopicCost> collect = listById.stream().collect(Collectors.toMap(SgjsTechnicalNormalTopicCost::getYear, value -> value, (k1, k2) -> k1));
                for (int j : range) {
                    SgjsTechnicalNormalTopicCost bean = collect.get(j);
                    if (bean != null) {
                        BigDecimal rdCost = bean.getRdCost();
                        objects.add(String.valueOf(rdCost == null ? BigDecimal.ZERO : rdCost));
                    } else {
                        objects.add("0.00");
                    }
                }
            }
            objects.add(sgjsTechnicalNormalTopic.getPersonNameList());
            objects.add(sgjsTechnicalNormalTopic.getRemark());
            dataList.add(objects);
        }
        return dataList;
    }

    /***
     * 功能描述: 获取表头
     * @param clazz 主表对应实体类
     * @param chidrenList 子表数据
     * @return 表头列表
     */
    private <T> List<List<String>> getHead(Class<T> clazz, List<SgjsTechnicalNormalTopicCost> chidrenList) {
        List<List<String>> resultList = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        int index = 0;
        for (Field field : declaredFields) {
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (field.getName().equals("endDate")) {
                index = annotation.order();
            }
            if (null == annotation) continue;
            String collect = String.join("", annotation.value());
            resultList.add(Collections.singletonList(collect));
        }
        Set<Integer> collect = chidrenList.stream().map(SgjsTechnicalNormalTopicCost::getYear).collect(Collectors.toSet());
        for (Integer year : collect) {
            resultList.add(index, Collections.singletonList(year + "年"));
            index++;
        }
        return resultList;
    }

    /***
     * 功能描述: 导入功能
     * @param headList 表头
     * @param dataList 数据
     */
    @Transactional
    public AjaxResult importData(List<Map<Integer, String>> headList, List<Map<Integer, String>> dataList) {
        ProjectDto projectDto = getProjectDto();
        List<SgjsTechnicalNormalTopic> mainList = new ArrayList<>();
        List<SgjsTechnicalNormalTopicCost> childList = new ArrayList<>();
        for (Map<Integer, String> integerStringMap : dataList) {
            SgjsTechnicalNormalTopic bean = new SgjsTechnicalNormalTopic();
            Long id = IdWorker.createId();
            bean.setId(id);
            bean.setRegionId(projectDto.getRegionId());
            bean.setRegionName(projectDto.getRegionName());
            bean.setProjectId(projectDto.getProjectId());
            bean.setPtVar5(projectDto.getProjectCode());
            bean.setRegionName(integerStringMap.get(0));
            bean.setProjectName(integerStringMap.get(1));
            String topicCode = integerStringMap.get(2);
            String topicName = integerStringMap.get(3);
            String highCertificateName = integerStringMap.get(4);
            String topicKindName = integerStringMap.get(5);
            String ecoTargetName = integerStringMap.get(6);
            String topicStateName = integerStringMap.get(7);
            String achievementKindName = integerStringMap.get(8);
            String startDateStr = integerStringMap.get(9);
            String endDateStr = integerStringMap.get(10);
            if (StrUtil.isBlank(topicCode) || StrUtil.isBlank(topicName) || StrUtil.isBlank(topicKindName) || StrUtil.isBlank(ecoTargetName)
            || StrUtil.isBlank(topicStateName) || StrUtil.isBlank(achievementKindName) || StrUtil.isBlank(startDateStr) || StrUtil.isBlank(endDateStr)){
                return AjaxResult.error("必填项为空，请检查");
            }
            bean.setTopicCode(topicCode);
            bean.setTopicName(topicName);
            bean.setHighCertificateName(highCertificateName);
            LinkedHashMap<String, String> dictData = DictUtil.getDictData("high_certificate");
            bean.setTopicKind(dictData.get(highCertificateName));
            bean.setTopicKindName(highCertificateName);
            LinkedHashMap<String, String> highCertificate = DictUtil.getDictData("eco_target");
            bean.setEcoTarget(highCertificate.get(ecoTargetName));
            bean.setEcoTargetName(ecoTargetName);
            bean.setTopicStateName(topicStateName);
            bean.setAchievementKindName(achievementKindName);
            bean.setStartDate(DateUtil.parseDate(startDateStr));
            bean.setStartDate(DateUtil.parseDate(endDateStr));
            List<DateTime> dateTimes = DateUtil.rangeToList(DateUtil.parseDate(startDateStr), DateUtil.parseDate(endDateStr), DateField.YEAR, 1);
            List<String> collect = dateTimes.stream().map(p -> DateUtil.format(p, DatePattern.NORM_YEAR_PATTERN)).collect(Collectors.toList());
            int index = 10;
            for (int i = 0; i < collect.size(); i++) {
                SgjsTechnicalNormalTopicCost beanCost = new SgjsTechnicalNormalTopicCost();
                beanCost.setForeignId(id);
                beanCost.setYear(Integer.valueOf(collect.get(i)));
                String rdCost = integerStringMap.get(index + i + 1);
                beanCost.setRdCost(new BigDecimal(rdCost));
                beanCost.setCreateUser(SecurityUtils.getUserName());
                beanCost.setCreateTime(DateUtils.getNowDate());
                beanCost.setId(IdWorker.createId());
                childList.add(beanCost);
            }
            bean.setPersonNameList(integerStringMap.get(collect.size()+index+1));
            bean.setRemark(integerStringMap.get(collect.size()+index+2));
            bean.setCreateUser(SecurityUtils.getUserName());
            bean.setCreateTime(DateUtils.getNowDate());
            mainList.add(bean);
        }
        //保存主表
        sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopicList(mainList);
        //保存子表
        sgjsTechnicalNormalTopicCostService.insertSgjsTechnicalNormalTopicCostList(childList);
        //数据推送总部版
//        executorService.execute(this::doSendGm);
        doSendGm();
        return AjaxResult.success();
    }

    //数据推送总部版
    public void doSendGm(){
        //主表
        List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList = sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopicList(new SgjsTechnicalNormalTopic());
        if (CollUtil.isEmpty(sgjsTechnicalNormalTopicList)) {
            List<SgjsTechnicalNormalTopic> objects = new ArrayList<>();
            SgjsTechnicalNormalTopic param = new SgjsTechnicalNormalTopic();
            ProjectDto projectDto = getProjectDto();
            param.setProjectId(projectDto.getProjectId());
            param.setPtVar5(projectDto.getProjectCode());
            objects.add(param);
            rocketMQTemplate.convertAndSend("sgjs_technical_normal_topic:tenantSuccess", objects);
            return;
        }
        //子表
        SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost = new SgjsTechnicalNormalTopicCost();
        List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostList = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCostList(sgjsTechnicalNormalTopicCost);
        Map<Long, List<SgjsTechnicalNormalTopicCost>> childrenMap = sgjsTechnicalNormalTopicCostList.stream().collect(Collectors.groupingBy(SgjsTechnicalNormalTopicCost::getForeignId));
        sgjsTechnicalNormalTopicList.forEach(p -> {
            if (CollUtil.isNotEmpty(childrenMap.get(p.getId()))) {
                p.setChildList(childrenMap.get(p.getId()));
            }
        });
        rocketMQTemplate.convertAndSend("sgjs_technical_normal_topic:tenantSuccess", sgjsTechnicalNormalTopicList);
    }
}
