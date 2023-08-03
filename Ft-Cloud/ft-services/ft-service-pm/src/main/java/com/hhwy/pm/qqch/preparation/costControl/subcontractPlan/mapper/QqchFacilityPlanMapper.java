package com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.mapper;

import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.domain.QqchFacilityPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:36:11
 * @remark
 */
@Repository
public interface QqchFacilityPlanMapper {

    QqchFacilityPlan getQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan);

    List<QqchFacilityPlan> getQqchFacilityPlanList(QqchFacilityPlan qqchFacilityPlan);

    int insertQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan);

    int insertQqchFacilityPlanList(@Param("qqchFacilityPlanList") List<QqchFacilityPlan> qqchFacilityPlanList);

    int updateQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan);

    int updateQqchFacilityPlanList(@Param("list") List<QqchFacilityPlan> qqchFacilityPlanList);

    int deleteQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan);

    int deleteQqchFacilityPlanByPks(@Param("qqchFacilityPlanPkList") List<Long> qqchFacilityPlanPkList);
}
