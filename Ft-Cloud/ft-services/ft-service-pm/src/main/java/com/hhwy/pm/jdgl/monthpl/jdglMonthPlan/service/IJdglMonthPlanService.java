package com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service;

import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
public interface IJdglMonthPlanService {

    JdglMonthPlan getJdglMonthPlan(JdglMonthPlan jdglMonthPlan);

    JdglMonthPlan getUsingMonthPlanByYearAndMonth(String year,String month);

    List<JdglMonthPlan> getJdglMonthPlanList(JdglMonthPlan jdglMonthPlan);

    int insertJdglMonthPlan(JdglMonthPlan jdglMonthPlan);

    int insertJdglMonthPlanList(List<JdglMonthPlan> jdglMonthPlanList);

    int updateJdglMonthPlan(JdglMonthPlan jdglMonthPlan);

    int updateJdglMonthPlanList(List<JdglMonthPlan> jdglMonthPlanList);

    int deleteJdglMonthPlan(JdglMonthPlan jdglMonthPlan);

    int deleteJdglMonthPlanByPks(List<Long> jdglMonthPlanPkList);

    JdglMonthPlan getInitJdglMonthPlan(JdglMonthPlan jdglMonthPlanParam);

    JdglMonthPlan adjust(JdglMonthPlan jdglMonthPlanParam);

    void updateTaskStatus(Long id);

    JdglMonthPlan getJdglMonthPlanListById(Long planId);
}
