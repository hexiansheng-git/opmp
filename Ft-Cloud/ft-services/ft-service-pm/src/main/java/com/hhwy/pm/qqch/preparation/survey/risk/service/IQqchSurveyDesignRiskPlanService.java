package com.hhwy.pm.qqch.preparation.survey.risk.service;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchSurveyDesignRiskPlan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:39:34
 * @remark 勘察设计风险策划
 */
public interface IQqchSurveyDesignRiskPlanService {

    QqchSurveyDesignRiskPlan getQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    List<QqchSurveyDesignRiskPlan> getQqchSurveyDesignRiskPlanList(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    int insertQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    int insertQqchSurveyDesignRiskPlanList(List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList);

    int updateQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    int updateQqchSurveyDesignRiskPlanList(List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList);

    int deleteQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    int deleteQqchSurveyDesignRiskPlanByPks(List<Long> qqchSurveyDesignRiskPlanPkList);
}
