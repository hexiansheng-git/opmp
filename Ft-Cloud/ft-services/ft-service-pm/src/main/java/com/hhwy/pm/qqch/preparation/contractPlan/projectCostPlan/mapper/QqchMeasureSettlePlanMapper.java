package com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.mapper;

import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.QqchMeasureSettlePlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:45:52
 * @remark
 */
@Repository
public interface QqchMeasureSettlePlanMapper {

    QqchMeasureSettlePlan getQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    List<QqchMeasureSettlePlan> getQqchMeasureSettlePlanList(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    int insertQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    int insertQqchMeasureSettlePlanList(@Param("qqchMeasureSettlePlanList") List<QqchMeasureSettlePlan> qqchMeasureSettlePlanList);

    int updateQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    int updateQqchMeasureSettlePlanList(@Param("list") List<QqchMeasureSettlePlan> qqchMeasureSettlePlanList);

    int deleteQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    int deleteQqchMeasureSettlePlanByPks(@Param("qqchMeasureSettlePlanPkList") List<Long> qqchMeasureSettlePlanPkList);
}
