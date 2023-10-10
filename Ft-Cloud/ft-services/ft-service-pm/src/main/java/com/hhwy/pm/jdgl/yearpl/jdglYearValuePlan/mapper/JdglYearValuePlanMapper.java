package com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.domain.JdglYearValuePlan;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
public interface JdglYearValuePlanMapper {

    JdglYearValuePlan getJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan);

    List<JdglYearValuePlan> getJdglYearValuePlanList(JdglYearValuePlan jdglYearValuePlan);

    int insertJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan);

    int insertJdglYearValuePlanList(@Param("jdglYearValuePlanList") List<JdglYearValuePlan> jdglYearValuePlanList);

    int updateJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan);

    int updateJdglYearValuePlanList(@Param("jdglYearValuePlanList") List<JdglYearValuePlan> jdglYearValuePlanList);

    int deleteJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan);

    int deleteJdglYearValuePlanByPks(@Param("jdglYearValuePlanPkList") List<Long> jdglYearValuePlanPkList);

    List<JdglYearValuePlan> getBillListByYear(@Param("year") String year);

    int deleteJdglYearValuePlanByYearPlanId(@Param("yearPlanId") Long yearPlanId);
}
