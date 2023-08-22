package com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service;

import java.util.List;

import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.domain.JdglYearValuePlan;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
public interface IJdglYearValuePlanService {

    JdglYearValuePlan getJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan);

    List<JdglYearValuePlan> getJdglYearValuePlanList(JdglYearValuePlan jdglYearValuePlan);

    List<JdglYearValuePlan> getJdglYearValuePlanListByYearPlanId(Long yearPlanId);

    int insertJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan);

    int insertJdglYearValuePlanList(List<JdglYearValuePlan> jdglYearValuePlanList);

    int updateJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan);

    int updateJdglYearValuePlanList(List<JdglYearValuePlan> jdglYearValuePlanList);

    int deleteJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan);

    int deleteJdglYearValuePlanByYearPlanId(Long yearPlanId);

    int deleteJdglYearValuePlanByPks(List<Long> jdglYearValuePlanPkList);
}
