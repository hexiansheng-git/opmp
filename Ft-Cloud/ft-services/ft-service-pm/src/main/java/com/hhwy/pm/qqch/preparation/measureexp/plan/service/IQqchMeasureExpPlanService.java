package com.hhwy.pm.qqch.preparation.measureexp.plan.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.plan.domain.QqchMeasureExpPlan;

import java.util.List;

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

    void saveTree(CompileEntity<List<QqchMeasureExpPlan>> map);

    List<QqchMeasureExpPlan> getQqchMeasureExpPlanListByVersion(QqchMeasureExpPlan qqchMeasureExpPlanParam);
}
