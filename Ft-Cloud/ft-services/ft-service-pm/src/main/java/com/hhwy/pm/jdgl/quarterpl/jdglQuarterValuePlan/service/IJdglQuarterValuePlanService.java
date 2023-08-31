package com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service;

import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain.JdglQuarterValuePlan;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
public interface IJdglQuarterValuePlanService {

    JdglQuarterValuePlan getJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan);

    List<JdglQuarterValuePlan> getJdglQuarterValuePlanList(JdglQuarterValuePlan jdglQuarterValuePlan);

    List<JdglQuarterValuePlan> getJdglQuarterValuePlanListByPlanId(Long planId);

    int insertJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan);

    int insertJdglQuarterValuePlanList(List<JdglQuarterValuePlan> jdglQuarterValuePlanList);

    int updateJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan);

    int updateJdglQuarterValuePlanList(List<JdglQuarterValuePlan> jdglQuarterValuePlanList);

    int deleteJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan);

    int deleteJdglQuarterValuePlanByPlanId(Long planId);

    int deleteJdglQuarterValuePlanByPks(List<Long> jdglQuarterValuePlanPkList);

    List<JdglQuarterValuePlan> getBillListByYearAndQuarter(String year, String quarter);
}
