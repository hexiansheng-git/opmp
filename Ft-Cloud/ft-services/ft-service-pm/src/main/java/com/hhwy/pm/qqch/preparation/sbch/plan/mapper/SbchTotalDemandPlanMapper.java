package com.hhwy.pm.qqch.preparation.sbch.plan.mapper;

import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlan;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-23 16:42
 */
public interface SbchTotalDemandPlanMapper {
    List<SbchTotalDemandPlan> selectSbchTotalDemandPlanList(SbchTotalDemandPlan sbchTotalDemandPlan);

    void deletePlan(SbchTotalDemandPlan sbchTotalDemandPlan);

    void insertSbchTotalDemandPlan(SbchTotalDemandPlan plan);

    void updateSbchTotalDemandPlan(SbchTotalDemandPlan plan);
}
