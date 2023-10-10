package com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.mapper;

import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain.JdglQuarterValuePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
public interface JdglQuarterValuePlanMapper {

    JdglQuarterValuePlan getJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan);

    List<JdglQuarterValuePlan> getJdglQuarterValuePlanList(JdglQuarterValuePlan jdglQuarterValuePlan);

    int insertJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan);

    int insertJdglQuarterValuePlanList(@Param("jdglQuarterValuePlanList") List<JdglQuarterValuePlan> jdglQuarterValuePlanList);

    int updateJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan);

    int updateJdglQuarterValuePlanList(@Param("jdglQuarterValuePlanList") List<JdglQuarterValuePlan> jdglQuarterValuePlanList);

    int deleteJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan);

    int deleteJdglQuarterValuePlanByPks(@Param("jdglQuarterValuePlanPkList") List<Long> jdglQuarterValuePlanPkList);

    List<JdglQuarterValuePlan> getBillListByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter);

    int deleteJdglQuarterValuePlanByPlanId(@Param("planId") Long planId);
}
