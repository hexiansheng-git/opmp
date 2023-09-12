package com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service;

import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
public interface IJdglMonthImagePlanService {

    JdglMonthImagePlan getJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan);

    List<JdglMonthImagePlan> getJdglMonthImagePlanList(JdglMonthImagePlan jdglMonthImagePlan);

    List<JdglMonthImagePlan> getJdglMonthImagePlanListByPlanId(Long planId);

    int insertJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan);

    int insertJdglMonthImagePlanList(List<JdglMonthImagePlan> jdglMonthImagePlanList);

    int updateJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan);

    int updateJdglMonthImagePlanList(List<JdglMonthImagePlan> jdglMonthImagePlanList);

    int deleteJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan);

    int deleteJdglMonthImagePlanByPks(List<Long> jdglMonthImagePlanPkList);

    int deleteJdglMonthImagePlanByPlanId(Long yearPlanId);

    JdglMonthPlan syncFromTotalPlan(JdglMonthPlan jdglMonthPlanParam);

    List<JdglMonthImagePlan> getWbsListByYearAndMonth(String year, String month);
}
