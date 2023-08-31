package com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service;

import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
public interface IJdglWeekImagePlanService {

    JdglWeekImagePlan getJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan);

    List<JdglWeekImagePlan> getJdglWeekImagePlanList(JdglWeekImagePlan jdglWeekImagePlan);

    List<JdglWeekImagePlan> getJdglWeekImagePlanListByPlanId(Long planId);

    int insertJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan);

    int insertJdglWeekImagePlanList(List<JdglWeekImagePlan> jdglWeekImagePlanList);

    int updateJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan);

    int updateJdglWeekImagePlanList(List<JdglWeekImagePlan> jdglWeekImagePlanList);

    int deleteJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan);

    int deleteJdglWeekImagePlanByPks(List<Long> jdglWeekImagePlanPkList);

    int deleteJdglWeekImagePlanByPlanId(Long yearPlanId);

    List<JdglWeekImagePlan> syncFromTotalPlan(JdglWeekPlan jdglWeekPlanParam);

    List<JdglWeekImagePlan> getWbsListByYearAndWeek(String year, String week);
}
