package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.mapper.SgjsTechnicalNormalTopicCostMapper;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.service.ISgjsTechnicalNormalTopicCostService;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;
import com.hhwy.utils.idworker.IdWorker;

/***
 * 功能描述: 科技管理 - 一般课题研发管理 - 年费用
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Service
public class SgjsTechnicalNormalTopicCostServiceImpl implements ISgjsTechnicalNormalTopicCostService {

    @Autowired
    private SgjsTechnicalNormalTopicCostMapper sgjsTechnicalNormalTopicCostMapper;


    public SgjsTechnicalNormalTopicCost getSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost) {
        return sgjsTechnicalNormalTopicCostMapper.getSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCost);
    }

    public List<SgjsTechnicalNormalTopicCost> getSgjsTechnicalNormalTopicCostList(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost) {
        return sgjsTechnicalNormalTopicCostMapper.getSgjsTechnicalNormalTopicCostList(sgjsTechnicalNormalTopicCost);
    }

    @Transactional
    public int insertSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost) {
        sgjsTechnicalNormalTopicCost.setId(IdWorker.createId());
        sgjsTechnicalNormalTopicCost.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopicCost.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicCostMapper.insertSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCost);
    }

    @Transactional
    public int insertSgjsTechnicalNormalTopicCostList(List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostList) {
        for (SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost : sgjsTechnicalNormalTopicCostList) {
            sgjsTechnicalNormalTopicCost.setId(IdWorker.createId());
            sgjsTechnicalNormalTopicCost.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalNormalTopicCost.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalNormalTopicCostMapper.insertSgjsTechnicalNormalTopicCostList(sgjsTechnicalNormalTopicCostList);
    }

    @Transactional
    public int updateSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost) {
        sgjsTechnicalNormalTopicCost.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopicCost.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicCostMapper.updateSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCost);
    }

    @Transactional
    public int updateSgjsTechnicalNormalTopicCostList(List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostList) {
        for (SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost : sgjsTechnicalNormalTopicCostList) {
            sgjsTechnicalNormalTopicCost.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalNormalTopicCost.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalNormalTopicCostMapper.updateSgjsTechnicalNormalTopicCostList(sgjsTechnicalNormalTopicCostList);
    }

    @Transactional
    public int deleteSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost) {
        return sgjsTechnicalNormalTopicCostMapper.deleteSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCost);
    }

    @Transactional
    public int deleteSgjsTechnicalNormalTopicCostByPks(List<Long> sgjsTechnicalNormalTopicCostPkList) {
        return sgjsTechnicalNormalTopicCostMapper.deleteSgjsTechnicalNormalTopicCostByPks(sgjsTechnicalNormalTopicCostPkList);
    }
}
