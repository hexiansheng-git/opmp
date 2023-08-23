package com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.mapper;

import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
public interface JdglMonthValuePlanMapper {

    JdglMonthValuePlan getJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan);

    List<JdglMonthValuePlan> getJdglMonthValuePlanList(JdglMonthValuePlan jdglMonthValuePlan);

    int insertJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan);

    int insertJdglMonthValuePlanList(@Param("jdglMonthValuePlanList") List<JdglMonthValuePlan> jdglMonthValuePlanList);

    int updateJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan);

    int updateJdglMonthValuePlanList(@Param("jdglMonthValuePlanList") List<JdglMonthValuePlan> jdglMonthValuePlanList);

    int deleteJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan);

    int deleteJdglMonthValuePlanByPks(@Param("jdglMonthValuePlanPkList") List<Long> jdglMonthValuePlanPkList);
}
