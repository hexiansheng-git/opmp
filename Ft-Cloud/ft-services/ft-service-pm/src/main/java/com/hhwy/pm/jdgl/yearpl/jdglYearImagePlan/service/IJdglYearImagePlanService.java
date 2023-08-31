package com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service;

import java.util.List;

import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
public interface IJdglYearImagePlanService {

    JdglYearImagePlan getJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan);

    List<JdglYearImagePlan> getJdglYearImagePlanList(JdglYearImagePlan jdglYearImagePlan);

    List<JdglYearImagePlan> getJdglYearImagePlanListByYearPlanId(Long yearPlanId);

    int insertJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan);

    int insertJdglYearImagePlanList(List<JdglYearImagePlan> jdglYearImagePlanList);

    int updateJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan);

    int updateJdglYearImagePlanList(List<JdglYearImagePlan> jdglYearImagePlanList);

    int deleteJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan);

    int deleteJdglYearImagePlanByPks(List<Long> jdglYearImagePlanPkList);

    int deleteJdglYearImagePlanByYearPlanId(Long yearPlanId);

    List<JdglYearImagePlan> syncFromTotalPlan(JdglYearPlan jdglYearPlanParam);

    List<JdglYearImagePlan> getWbsListByYear(String year);
}
