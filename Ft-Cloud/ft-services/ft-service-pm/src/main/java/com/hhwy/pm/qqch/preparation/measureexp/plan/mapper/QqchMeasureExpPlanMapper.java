package com.hhwy.pm.qqch.preparation.measureexp.plan.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.measureexp.plan.domain.QqchMeasureExpPlan;
import org.apache.ibatis.annotations.Param;

/**
 * @author mls
 * @date 2023-07-25 18:01:32
 * @remark
 */
public interface QqchMeasureExpPlanMapper {

    QqchMeasureExpPlan getQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan);

    List<QqchMeasureExpPlan> getQqchMeasureExpPlanList(QqchMeasureExpPlan qqchMeasureExpPlan);

    int insertQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan);

    int insertQqchMeasureExpPlanList(@Param("qqchMeasureExpPlanList") List<QqchMeasureExpPlan> qqchMeasureExpPlanList);

    int updateQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan);

    int updateQqchMeasureExpPlanList(@Param("qqchMeasureExpPlanList") List<QqchMeasureExpPlan> qqchMeasureExpPlanList);

    int deleteQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan);

    int deleteQqchMeasureExpPlanByPks(@Param("qqchMeasureExpPlanPkList") List<Long> qqchMeasureExpPlanPkList);
}
