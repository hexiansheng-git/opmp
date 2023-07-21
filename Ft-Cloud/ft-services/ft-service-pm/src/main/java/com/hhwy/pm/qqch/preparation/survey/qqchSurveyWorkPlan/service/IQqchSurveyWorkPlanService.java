package com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service;

import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlan;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-20 11:49:55
 * @remark  2.2 勘察设计工作计划
 */
public interface IQqchSurveyWorkPlanService {
                                                                                                                                                                                                                                                                                                                                                                                                                                        
    QqchSurveyWorkPlan getQqchSurveyWorkPlan(QqchSurveyWorkPlan qqchSurveyWorkPlan);

    List<QqchSurveyWorkPlan> getQqchSurveyWorkPlanList(QqchSurveyWorkPlan qqchSurveyWorkPlan);

    int insertQqchSurveyWorkPlan(QqchSurveyWorkPlan qqchSurveyWorkPlan);

    int insertQqchSurveyWorkPlanList(List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList);

    int updateQqchSurveyWorkPlan(QqchSurveyWorkPlan qqchSurveyWorkPlan);

            int updateQqchSurveyWorkPlanList(List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList);
    
    int deleteQqchSurveyWorkPlan(QqchSurveyWorkPlan qqchSurveyWorkPlan);

            int deleteQqchSurveyWorkPlanByPks(List<Long> qqchSurveyWorkPlanPkList);
    }
