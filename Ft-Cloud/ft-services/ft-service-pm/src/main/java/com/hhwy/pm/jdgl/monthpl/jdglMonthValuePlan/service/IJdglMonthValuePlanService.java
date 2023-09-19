package com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service;

import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
public interface IJdglMonthValuePlanService {

    JdglMonthValuePlan getJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan);

    List<JdglMonthValuePlan> getJdglMonthValuePlanList(JdglMonthValuePlan jdglMonthValuePlan);

    List<JdglMonthValuePlan> getJdglMonthValuePlanListByPlanId(Long planId);

    int insertJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan);

    int insertJdglMonthValuePlanList(List<JdglMonthValuePlan> jdglMonthValuePlanList);

    int updateJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan);

    int updateJdglMonthValuePlanList(List<JdglMonthValuePlan> jdglMonthValuePlanList);

    int deleteJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan);

    int deleteJdglMonthValuePlanByPlanId(Long planId);

    int deleteJdglMonthValuePlanByPks(List<Long> jdglMonthValuePlanPkList);

    List<JdglMonthValuePlan> getBillListByYearAndMonth(String year, String month);

    List<JdglMonthValuePlan> updateValuePlanData(Long planId, List<JdglMonthImagePlan> jdglMonthImagePlanList);
}
