package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchTopicResearchPlan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:25
 * @remark
 */
public interface IQqchTopicResearchPlanService {

    QqchTopicResearchPlan getQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    List<QqchTopicResearchPlan> getQqchTopicResearchPlanList(QqchTopicResearchPlan qqchTopicResearchPlan);

    int insertQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    int insertQqchTopicResearchPlanList(List<QqchTopicResearchPlan> qqchTopicResearchPlanList);

    int updateQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    int updateQqchTopicResearchPlanList(List<QqchTopicResearchPlan> qqchTopicResearchPlanList);

    int deleteQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    int deleteQqchTopicResearchPlanByPks(List<Long> qqchTopicResearchPlanPkList);
}
