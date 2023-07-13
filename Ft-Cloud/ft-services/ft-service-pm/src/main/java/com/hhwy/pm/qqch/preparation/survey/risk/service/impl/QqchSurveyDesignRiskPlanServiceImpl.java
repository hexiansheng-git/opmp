package com.hhwy.pm.qqch.preparation.survey.risk.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchSurveyDesignRiskPlan;
import com.hhwy.pm.qqch.preparation.survey.risk.mapper.QqchSurveyDesignRiskPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchSurveyDesignRiskPlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-13 11:39:34
 * @remark 勘察设计风险策划
 */
@Service
public class QqchSurveyDesignRiskPlanServiceImpl implements IQqchSurveyDesignRiskPlanService {

    @Autowired
    private QqchSurveyDesignRiskPlanMapper qqchSurveyDesignRiskPlanMapper;


    public QqchSurveyDesignRiskPlan getQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan) {
        return qqchSurveyDesignRiskPlanMapper.getQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlan);
    }

    public List<QqchSurveyDesignRiskPlan> getQqchSurveyDesignRiskPlanList(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan) {
        return qqchSurveyDesignRiskPlanMapper.getQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlan);
    }

    @Transactional
    public int insertQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan) {
        qqchSurveyDesignRiskPlan.setId(IdWorker.createId());
        qqchSurveyDesignRiskPlan.setCreateUser(SecurityUtils.getUserName());
        qqchSurveyDesignRiskPlan.setCreateTime(DateUtils.getNowDate());
        return qqchSurveyDesignRiskPlanMapper.insertQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlan);
    }

    @Transactional
    public int insertQqchSurveyDesignRiskPlanList(List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList) {
        for (QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan : qqchSurveyDesignRiskPlanList) {
            qqchSurveyDesignRiskPlan.setId(IdWorker.createId());
            qqchSurveyDesignRiskPlan.setCreateUser(SecurityUtils.getUserName());
            qqchSurveyDesignRiskPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSurveyDesignRiskPlanMapper.insertQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanList);
    }

    @Transactional
    public int updateQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan) {
        qqchSurveyDesignRiskPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyDesignRiskPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyDesignRiskPlanMapper.updateQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlan);
    }

    @Transactional
    public int updateQqchSurveyDesignRiskPlanList(List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList) {
        for (QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan : qqchSurveyDesignRiskPlanList) {
            qqchSurveyDesignRiskPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchSurveyDesignRiskPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSurveyDesignRiskPlanMapper.updateQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanList);
    }

    @Transactional
    public int deleteQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan) {
        qqchSurveyDesignRiskPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyDesignRiskPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyDesignRiskPlanMapper.deleteQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlan);
    }

    @Transactional
    public int deleteQqchSurveyDesignRiskPlanByPks(List<Long> qqchSurveyDesignRiskPlanPkList) {
        return qqchSurveyDesignRiskPlanMapper.deleteQqchSurveyDesignRiskPlanByPks(qqchSurveyDesignRiskPlanPkList);
    }
}
