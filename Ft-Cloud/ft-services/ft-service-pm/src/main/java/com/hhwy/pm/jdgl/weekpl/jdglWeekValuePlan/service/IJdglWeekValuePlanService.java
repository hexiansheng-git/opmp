package com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.service;

import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.domain.JdglWeekValuePlan;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
public interface IJdglWeekValuePlanService {

    JdglWeekValuePlan getJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan);

    List<JdglWeekValuePlan> getJdglWeekValuePlanList(JdglWeekValuePlan jdglWeekValuePlan);

    List<JdglWeekValuePlan> getJdglWeekValuePlanListByPlanId(Long planId);

    int insertJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan);

    int insertJdglWeekValuePlanList(List<JdglWeekValuePlan> jdglWeekValuePlanList);

    int updateJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan);

    int updateJdglWeekValuePlanList(List<JdglWeekValuePlan> jdglWeekValuePlanList);

    int deleteJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan);

    int deleteJdglWeekValuePlanByPlanId(Long planId);

    int deleteJdglWeekValuePlanByPks(List<Long> jdglWeekValuePlanPkList);

    List<JdglWeekValuePlan> getBillListByYearAndWeek(String year, String week);

    List<JdglWeekValuePlan> updateValuePlanData(Long planId, List<JdglWeekImagePlan> jdglWeekImagePlanList);
}
