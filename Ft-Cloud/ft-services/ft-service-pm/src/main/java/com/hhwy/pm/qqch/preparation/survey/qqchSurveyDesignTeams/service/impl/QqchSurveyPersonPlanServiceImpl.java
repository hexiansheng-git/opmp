package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyPersonPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.mapper.QqchSurveyPersonPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.IQqchSurveyPersonPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-25 11:09:33
 * @remark  2.1.3 勘察设计队伍配置 ---人员策划
 */
@Service
public class QqchSurveyPersonPlanServiceImpl implements IQqchSurveyPersonPlanService {

    @Autowired
    private QqchSurveyPersonPlanMapper qqchSurveyPersonPlanMapper;

                                                                                                                                                                                                                                                                                                                                                                                                    
    public QqchSurveyPersonPlan getQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan) {
        return qqchSurveyPersonPlanMapper.getQqchSurveyPersonPlan(qqchSurveyPersonPlan);
    }

    public List<QqchSurveyPersonPlan> getQqchSurveyPersonPlanList(QqchSurveyPersonPlan qqchSurveyPersonPlan) {
        return qqchSurveyPersonPlanMapper.getQqchSurveyPersonPlanList(qqchSurveyPersonPlan);
    }

    @Transactional
    public int insertQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan) {
        qqchSurveyPersonPlan.setId(IdWorker.createId());
        qqchSurveyPersonPlan.setCreateUser(SecurityUtils.getUserName());
        qqchSurveyPersonPlan.setCreateTime(DateUtils.getNowDate());
        return qqchSurveyPersonPlanMapper.insertQqchSurveyPersonPlan(qqchSurveyPersonPlan);
    }

    @Transactional
    public int insertQqchSurveyPersonPlanList(List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList) {
        for (QqchSurveyPersonPlan qqchSurveyPersonPlan : qqchSurveyPersonPlanList) {
            qqchSurveyPersonPlan.setId(IdWorker.createId());
            qqchSurveyPersonPlan.setCreateUser(SecurityUtils.getUserName());
            qqchSurveyPersonPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSurveyPersonPlanMapper.insertQqchSurveyPersonPlanList(qqchSurveyPersonPlanList);
    }

    @Transactional
    public int updateQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan) {
        qqchSurveyPersonPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyPersonPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyPersonPlanMapper.updateQqchSurveyPersonPlan(qqchSurveyPersonPlan);
    }

            @Transactional
        public int updateQqchSurveyPersonPlanList(List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList) {
            for (QqchSurveyPersonPlan qqchSurveyPersonPlan : qqchSurveyPersonPlanList) {
                qqchSurveyPersonPlan.setUpdateUser(SecurityUtils.getUserName());
                qqchSurveyPersonPlan.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchSurveyPersonPlanMapper.updateQqchSurveyPersonPlanList(qqchSurveyPersonPlanList);
        }
    
    @Transactional
    public int deleteQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan) {
        qqchSurveyPersonPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyPersonPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyPersonPlanMapper.deleteQqchSurveyPersonPlan(qqchSurveyPersonPlan);
    }

            @Transactional
        public int deleteQqchSurveyPersonPlanByPks(List<Long> qqchSurveyPersonPlanPkList) {
            return qqchSurveyPersonPlanMapper.deleteQqchSurveyPersonPlanByPks(qqchSurveyPersonPlanPkList);
        }
    }
