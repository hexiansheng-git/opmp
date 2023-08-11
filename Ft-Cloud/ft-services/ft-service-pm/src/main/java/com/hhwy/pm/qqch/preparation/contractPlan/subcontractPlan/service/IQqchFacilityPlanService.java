package com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.service;

import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.domain.QqchFacilityPlan;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:36:11
 * @remark
 */
public interface IQqchFacilityPlanService {

    QqchFacilityPlan getQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan);

    List<QqchFacilityPlan> getQqchFacilityPlanList(QqchFacilityPlan qqchFacilityPlan);

    int insertQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan);

    int insertQqchFacilityPlanList(List<QqchFacilityPlan> qqchFacilityPlanList);

    int updateQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan);

    int updateQqchFacilityPlanList(List<QqchFacilityPlan> qqchFacilityPlanList);

    int deleteQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan);

    int deleteQqchFacilityPlanByPks(List<Long> qqchFacilityPlanPkList);
}
