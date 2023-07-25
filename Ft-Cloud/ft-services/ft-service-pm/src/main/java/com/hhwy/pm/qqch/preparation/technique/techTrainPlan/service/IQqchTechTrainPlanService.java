package com.hhwy.pm.qqch.preparation.technique.techTrainPlan.service;

import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.QqchTechTrainPlan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:57:39
 * @remark 技术培训策划
 */
public interface IQqchTechTrainPlanService {

    QqchTechTrainPlan getQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    List<QqchTechTrainPlan> getQqchTechTrainPlanList(QqchTechTrainPlan qqchTechTrainPlan);

    int insertQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    int insertQqchTechTrainPlanList(List<QqchTechTrainPlan> qqchTechTrainPlanList);

    int updateQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    int updateQqchTechTrainPlanList(List<QqchTechTrainPlan> qqchTechTrainPlanList);

    int deleteQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    int deleteQqchTechTrainPlanByPks(List<Long> qqchTechTrainPlanPkList);
}
