package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchTopicResearchPlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchTopicResearchPlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchTopicResearchPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:25
 * @remark
 */
@Service
public class QqchTopicResearchPlanServiceImpl implements IQqchTopicResearchPlanService {

    @Autowired
    private QqchTopicResearchPlanMapper qqchTopicResearchPlanMapper;


    public QqchTopicResearchPlan getQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan) {
        return qqchTopicResearchPlanMapper.getQqchTopicResearchPlan(qqchTopicResearchPlan);
    }

    public List<QqchTopicResearchPlan> getQqchTopicResearchPlanList(QqchTopicResearchPlan qqchTopicResearchPlan) {
        return qqchTopicResearchPlanMapper.getQqchTopicResearchPlanList(qqchTopicResearchPlan);
    }

    @Transactional
    public int insertQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan) {
        qqchTopicResearchPlan.setId(IdWorker.createId());
        qqchTopicResearchPlan.setCreateUser(SecurityUtils.getUserName());
        qqchTopicResearchPlan.setCreateTime(DateUtils.getNowDate());
        return qqchTopicResearchPlanMapper.insertQqchTopicResearchPlan(qqchTopicResearchPlan);
    }

    @Transactional
    public int insertQqchTopicResearchPlanList(List<QqchTopicResearchPlan> qqchTopicResearchPlanList) {
        for (QqchTopicResearchPlan qqchTopicResearchPlan : qqchTopicResearchPlanList) {
            qqchTopicResearchPlan.setId(IdWorker.createId());
            qqchTopicResearchPlan.setCreateUser(SecurityUtils.getUserName());
            qqchTopicResearchPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTopicResearchPlanMapper.insertQqchTopicResearchPlanList(qqchTopicResearchPlanList);
    }

    @Transactional
    public int updateQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan) {
        qqchTopicResearchPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchTopicResearchPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchTopicResearchPlanMapper.updateQqchTopicResearchPlan(qqchTopicResearchPlan);
    }

    @Transactional
    public int updateQqchTopicResearchPlanList(List<QqchTopicResearchPlan> qqchTopicResearchPlanList) {
        for (QqchTopicResearchPlan qqchTopicResearchPlan : qqchTopicResearchPlanList) {
            qqchTopicResearchPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchTopicResearchPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTopicResearchPlanMapper.updateQqchTopicResearchPlanList(qqchTopicResearchPlanList);
    }

    @Transactional
    public int deleteQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan) {
        qqchTopicResearchPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchTopicResearchPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchTopicResearchPlanMapper.deleteQqchTopicResearchPlan(qqchTopicResearchPlan);
    }

    @Transactional
    public int deleteQqchTopicResearchPlanByPks(List<Long> qqchTopicResearchPlanPkList) {
        return qqchTopicResearchPlanMapper.deleteQqchTopicResearchPlanByPks(qqchTopicResearchPlanPkList);
    }
}
