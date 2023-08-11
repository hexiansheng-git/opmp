package com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.mapper;

import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.QqchStaffPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:36:01
 * @remark
 */
@Repository
public interface QqchStaffPlanMapper {

    QqchStaffPlan getQqchStaffPlan(QqchStaffPlan qqchStaffPlan);

    List<QqchStaffPlan> getQqchStaffPlanList(QqchStaffPlan qqchStaffPlan);

    int insertQqchStaffPlan(QqchStaffPlan qqchStaffPlan);

    int insertQqchStaffPlanList(@Param("qqchStaffPlanList") List<QqchStaffPlan> qqchStaffPlanList);

    int updateQqchStaffPlan(QqchStaffPlan qqchStaffPlan);

    int updateQqchStaffPlanList(@Param("list") List<QqchStaffPlan> qqchStaffPlanList);

    int deleteQqchStaffPlan(QqchStaffPlan qqchStaffPlan);

    int deleteQqchStaffPlanByPks(@Param("qqchStaffPlanPkList") List<Long> qqchStaffPlanPkList);
}
