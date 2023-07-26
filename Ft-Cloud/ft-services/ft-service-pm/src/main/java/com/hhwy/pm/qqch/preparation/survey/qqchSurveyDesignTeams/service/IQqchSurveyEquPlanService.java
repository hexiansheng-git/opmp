package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service;

import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyEquPlan;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-25 11:09:40
 * @remark 2.1.3 勘察设计队伍配置 ---设备策划
 */
public interface IQqchSurveyEquPlanService {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchSurveyEquPlan getQqchSurveyEquPlan(QqchSurveyEquPlan qqchSurveyEquPlan);

    List<QqchSurveyEquPlan> getQqchSurveyEquPlanList(QqchSurveyEquPlan qqchSurveyEquPlan);

    int insertQqchSurveyEquPlan(QqchSurveyEquPlan qqchSurveyEquPlan);

    int insertQqchSurveyEquPlanList(List<QqchSurveyEquPlan> qqchSurveyEquPlanList);

    int updateQqchSurveyEquPlan(QqchSurveyEquPlan qqchSurveyEquPlan);

    int updateQqchSurveyEquPlanList(List<QqchSurveyEquPlan> qqchSurveyEquPlanList);
    
    int deleteQqchSurveyEquPlan(QqchSurveyEquPlan qqchSurveyEquPlan);

    int deleteQqchSurveyEquPlanByPks(List<Long> qqchSurveyEquPlanPkList);
    }
