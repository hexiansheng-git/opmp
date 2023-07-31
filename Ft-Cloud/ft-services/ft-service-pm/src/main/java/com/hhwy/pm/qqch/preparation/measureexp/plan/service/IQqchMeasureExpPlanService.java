package com.hhwy.pm.qqch.preparation.measureexp.plan.service;

import java.util.List;

import com.hhwy.pm.qqch.preparation.measureexp.plan.domain.QqchMeasureExpPlan;

/**
 * @author mls
 * @date 2023-07-25 18:01:32
 * @remark
 */
public interface IQqchMeasureExpPlanService {

    QqchMeasureExpPlan getQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan);

    List<QqchMeasureExpPlan> getQqchMeasureExpPlanList(QqchMeasureExpPlan qqchMeasureExpPlan);

    int insertQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan);

    int insertQqchMeasureExpPlanList(List<QqchMeasureExpPlan> qqchMeasureExpPlanList);

    int updateQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan);

    int updateQqchMeasureExpPlanList(List<QqchMeasureExpPlan> qqchMeasureExpPlanList);

    int deleteQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan);

    int deleteQqchMeasureExpPlanByPks(List<Long> qqchMeasureExpPlanPkList);

    void saveTree(List<QqchMeasureExpPlan> dtos);

    List<QqchMeasureExpPlan> getQqchMeasureExpPlanListByVersion(QqchMeasureExpPlan qqchMeasureExpPlanParam);
}
