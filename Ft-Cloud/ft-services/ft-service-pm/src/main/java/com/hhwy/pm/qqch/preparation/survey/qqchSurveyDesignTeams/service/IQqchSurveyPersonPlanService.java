package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service;

import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyPersonPlan;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-25 11:09:33
 * @remark 2.1.3 勘察设计队伍配置 ---人员策划
 */
public interface IQqchSurveyPersonPlanService {
                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchSurveyPersonPlan getQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    List<QqchSurveyPersonPlan> getQqchSurveyPersonPlanList(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    int insertQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    int insertQqchSurveyPersonPlanList(List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList);

    int updateQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    int updateQqchSurveyPersonPlanList(List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList);
    
    int deleteQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    int deleteQqchSurveyPersonPlanByPks(List<Long> qqchSurveyPersonPlanPkList);
    }
