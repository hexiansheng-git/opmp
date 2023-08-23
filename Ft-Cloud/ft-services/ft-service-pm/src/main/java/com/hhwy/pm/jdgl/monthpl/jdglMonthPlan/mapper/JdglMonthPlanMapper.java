package com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.mapper;

import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
public interface JdglMonthPlanMapper {

    JdglMonthPlan getJdglMonthPlan(JdglMonthPlan jdglMonthPlan);

    List<JdglMonthPlan> getJdglMonthPlanList(JdglMonthPlan jdglMonthPlan);

    int insertJdglMonthPlan(JdglMonthPlan jdglMonthPlan);

    int insertJdglMonthPlanList(@Param("jdglMonthPlanList") List<JdglMonthPlan> jdglMonthPlanList);

    int updateJdglMonthPlan(JdglMonthPlan jdglMonthPlan);

    int updateJdglMonthPlanList(@Param("jdglMonthPlanList") List<JdglMonthPlan> jdglMonthPlanList);

    int deleteJdglMonthPlan(JdglMonthPlan jdglMonthPlan);

    int deleteJdglMonthPlanByPks(@Param("jdglMonthPlanPkList") List<Long> jdglMonthPlanPkList);
}
