package com.hhwy.pm.jdgl.service;

import com.hhwy.pm.jdgl.domain.JdglDatePlan;

import java.util.List;

/**
 * @author fushudong
 * @date 2023-08-14 17:55:25
 * @remark
 */
public interface IJdglDatePlanService {

    JdglDatePlan getJdglDatePlan(JdglDatePlan jdglDatePlan);

    List<JdglDatePlan> getJdglDatePlanList(JdglDatePlan jdglDatePlan);

    int insertJdglDatePlan(JdglDatePlan jdglDatePlan);

    int insertJdglDatePlanList(List<JdglDatePlan> jdglDatePlanList);

    int updateJdglDatePlan(JdglDatePlan jdglDatePlan);

    int updateJdglDatePlanList(List<JdglDatePlan> jdglDatePlanList);

    int deleteJdglDatePlan(JdglDatePlan jdglDatePlan);

    int deleteJdglDatePlanByPks(List<Long> jdglDatePlanPkList);
}
