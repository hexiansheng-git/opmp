package com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service;

import java.util.List;

import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
public interface IJdglYearPlanService {

    JdglYearPlan getJdglYearPlan(JdglYearPlan jdglYearPlan);

    List<JdglYearPlan> getJdglYearPlanList(JdglYearPlan jdglYearPlan);

    int insertJdglYearPlan(JdglYearPlan jdglYearPlan);

    int insertJdglYearPlanList(List<JdglYearPlan> jdglYearPlanList);

    int updateJdglYearPlan(JdglYearPlan jdglYearPlan);

    int updateJdglYearPlanList(List<JdglYearPlan> jdglYearPlanList);

    int deleteJdglYearPlan(JdglYearPlan jdglYearPlan);

    int deleteJdglYearPlanByPks(List<Long> jdglYearPlanPkList);

    JdglYearPlan getInitJdglYearPlan(JdglYearPlan jdglYearPlanParam);

    int adjust(JdglYearPlan jdglYearPlanParam);
}
