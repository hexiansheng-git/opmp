package com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.service;

import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.domain.QqchSurveyResultPlan;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.domain.QqchSurveyResultPlanVo;

/**
 * @author ldd
 * @date 2023-07-21 16:45:04
 * @remark 2.3.1 勘测成果清单及计划
 */
public interface IQqchSurveyResultPlanService {



    QqchSurveyResultPlanVo getQqchSurveyResultPlanList(QqchSurveyResultPlan qqchSurveyResultPlan);


    void save(QqchSurveyResultPlanVo qqchSurveyResultPlanVo);

    void confirm(QqchSurveyResultPlanVo qqchSurveyResultPlanVo);
}
