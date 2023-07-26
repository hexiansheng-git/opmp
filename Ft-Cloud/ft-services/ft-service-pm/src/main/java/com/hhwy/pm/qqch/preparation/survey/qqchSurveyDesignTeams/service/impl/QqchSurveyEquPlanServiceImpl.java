package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyEquPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.mapper.QqchSurveyEquPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.IQqchSurveyEquPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-25 11:09:40
 * @remark  2.1.3 勘察设计队伍配置 ---设备策划
 */
@Service
public class QqchSurveyEquPlanServiceImpl implements IQqchSurveyEquPlanService {

    @Autowired
    private QqchSurveyEquPlanMapper qqchSurveyEquPlanMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                
    public QqchSurveyEquPlan getQqchSurveyEquPlan(QqchSurveyEquPlan qqchSurveyEquPlan) {
        return qqchSurveyEquPlanMapper.getQqchSurveyEquPlan(qqchSurveyEquPlan);
    }

    public List<QqchSurveyEquPlan> getQqchSurveyEquPlanList(QqchSurveyEquPlan qqchSurveyEquPlan) {
        return qqchSurveyEquPlanMapper.getQqchSurveyEquPlanList(qqchSurveyEquPlan);
    }

    @Transactional
    public int insertQqchSurveyEquPlan(QqchSurveyEquPlan qqchSurveyEquPlan) {
        qqchSurveyEquPlan.setId(IdWorker.createId());
        qqchSurveyEquPlan.setCreateUser(SecurityUtils.getUserName());
        qqchSurveyEquPlan.setCreateTime(DateUtils.getNowDate());
        return qqchSurveyEquPlanMapper.insertQqchSurveyEquPlan(qqchSurveyEquPlan);
    }

    @Transactional
    public int insertQqchSurveyEquPlanList(List<QqchSurveyEquPlan> qqchSurveyEquPlanList) {
        for (QqchSurveyEquPlan qqchSurveyEquPlan : qqchSurveyEquPlanList) {
            qqchSurveyEquPlan.setId(IdWorker.createId());
            qqchSurveyEquPlan.setCreateUser(SecurityUtils.getUserName());
            qqchSurveyEquPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSurveyEquPlanMapper.insertQqchSurveyEquPlanList(qqchSurveyEquPlanList);
    }

    @Transactional
    public int updateQqchSurveyEquPlan(QqchSurveyEquPlan qqchSurveyEquPlan) {
        qqchSurveyEquPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyEquPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyEquPlanMapper.updateQqchSurveyEquPlan(qqchSurveyEquPlan);
    }

            @Transactional
        public int updateQqchSurveyEquPlanList(List<QqchSurveyEquPlan> qqchSurveyEquPlanList) {
            for (QqchSurveyEquPlan qqchSurveyEquPlan : qqchSurveyEquPlanList) {
                qqchSurveyEquPlan.setUpdateUser(SecurityUtils.getUserName());
                qqchSurveyEquPlan.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchSurveyEquPlanMapper.updateQqchSurveyEquPlanList(qqchSurveyEquPlanList);
        }
    
    @Transactional
    public int deleteQqchSurveyEquPlan(QqchSurveyEquPlan qqchSurveyEquPlan) {
        qqchSurveyEquPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyEquPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyEquPlanMapper.deleteQqchSurveyEquPlan(qqchSurveyEquPlan);
    }

            @Transactional
        public int deleteQqchSurveyEquPlanByPks(List<Long> qqchSurveyEquPlanPkList) {
            return qqchSurveyEquPlanMapper.deleteQqchSurveyEquPlanByPks(qqchSurveyEquPlanPkList);
        }
    }
