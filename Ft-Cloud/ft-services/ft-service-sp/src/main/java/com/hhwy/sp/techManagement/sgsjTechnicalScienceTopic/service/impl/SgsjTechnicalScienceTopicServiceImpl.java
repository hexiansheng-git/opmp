package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.nacos.api.config.filter.IFilterConfig;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.service.IShjsAuthenticateEvaluateService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.mapper.SgsjTechnicalScienceTopicMapper;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.ISgsjTechnicalScienceTopicService;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
import com.hhwy.utils.idworker.IdWorker;

/**
 * 功能描述: 科技管理 - 科研课题研发管理
 * @author fsd
 * @date 2024-01-29 14:11:17
 * @remark 
 */
@Service
public class SgsjTechnicalScienceTopicServiceImpl implements ISgsjTechnicalScienceTopicService{

    @Autowired
    private SgsjTechnicalScienceTopicMapper sgsjTechnicalScienceTopicMapper;

    @Autowired
    private ISgjsAchievementAwardService sgjsAchievementAwardService;
    @Autowired
    private IShjsAuthenticateEvaluateService shjsAuthenticateEvaluateService;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    public SgsjTechnicalScienceTopic getSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        return sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

    /***
     * 功能描述: 查询
     */
    public List<SgsjTechnicalScienceTopic> getSgsjTechnicalScienceTopicList(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        List<SgsjTechnicalScienceTopic> resultList = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopic);
        if (CollUtil.isEmpty(resultList)) return Collections.singletonList(new SgsjTechnicalScienceTopic());
        Long[] ids = resultList.stream().map(SgsjTechnicalScienceTopic::getId).toArray(Long[]::new);
        List<SgjsAchievementAward> awardList = sgjsAchievementAwardService.getListByForeignIds(ids);
        Map<Long, List<SgjsAchievementAward>> awardMap = new HashMap<>();
        if (CollUtil.isNotEmpty(awardList)){
            awardMap = awardList.stream().collect(Collectors.groupingBy(SgjsAchievementAward::getForeignId));
        }
        List<ShjsAuthenticateEvaluate> evaluateList = shjsAuthenticateEvaluateService.getListByForeignIds(ids);
        Map<Long, List<ShjsAuthenticateEvaluate>> evaluateMap = new HashMap<>();
        if (CollUtil.isNotEmpty(evaluateList)){
            evaluateMap = evaluateList.stream().collect(Collectors.groupingBy(ShjsAuthenticateEvaluate::getForeignId));
        }
        for (SgsjTechnicalScienceTopic bean : resultList){
            if (awardMap.containsKey(bean.getId())) {
                bean.setAwardList(awardMap.get(bean.getId()));
            }
            if (evaluateMap.containsKey(bean.getId())) {
                bean.setEvaluateList(evaluateMap.get(bean.getId()));
            }
        }
//        FlowInfoSearchUtil.getFlowInfo(resultList, FlowEnum.SGJS_TECH_SCIENCE_TOPIC);
        return resultList;
    }

    @Transactional
    public int insertSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        sgsjTechnicalScienceTopic.setId(IdWorker.createId());
        sgsjTechnicalScienceTopic.setCreateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopic.setCreateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicMapper.insertSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

    @Transactional
    public int insertSgsjTechnicalScienceTopicList(List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList) {
        for (SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic : sgsjTechnicalScienceTopicList) {
            sgsjTechnicalScienceTopic.setId(IdWorker.createId());
            sgsjTechnicalScienceTopic.setCreateUser(SecurityUtils.getUserName());
            sgsjTechnicalScienceTopic.setCreateTime(DateUtils.getNowDate());
        }
        return sgsjTechnicalScienceTopicMapper.insertSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicList);
    }

    @Transactional
    public int updateSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        sgsjTechnicalScienceTopic.setUpdateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopic.setUpdateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicMapper.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

            @Transactional
        public int updateSgsjTechnicalScienceTopicList(List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList) {
            for (SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic : sgsjTechnicalScienceTopicList) {
                sgsjTechnicalScienceTopic.setUpdateUser(SecurityUtils.getUserName());
                sgsjTechnicalScienceTopic.setUpdateTime(DateUtils.getNowDate());
            }
            return sgsjTechnicalScienceTopicMapper.updateSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicList);
        }
    
    @Transactional
    public int deleteSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        sgsjTechnicalScienceTopic.setUpdateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopic.setUpdateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicMapper.deleteSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

            @Transactional
        public int deleteSgsjTechnicalScienceTopicByPks(List<Long> sgsjTechnicalScienceTopicPkList) {
            return sgsjTechnicalScienceTopicMapper.deleteSgsjTechnicalScienceTopicByPks(sgsjTechnicalScienceTopicPkList);
        }
    }
