package com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service;

import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlanVo;

/**
 * @author ldd
 * @date 2023-07-20 11:49:55
 * @remark  2.2 勘察设计工作计划
 */
public interface IQqchSurveyWorkPlanService {
                                                                                                                                                                                                                                                                                                                                                                                                                                        


    QqchSurveyWorkPlanVo getQqchSurveyWorkPlanList(QqchSurveyWorkPlan qqchSurveyWorkPlan);


    void save(QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo);

    void confirm(QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo);
}
