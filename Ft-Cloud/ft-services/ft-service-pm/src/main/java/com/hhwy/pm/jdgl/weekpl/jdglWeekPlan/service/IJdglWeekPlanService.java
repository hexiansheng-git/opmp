package com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service;

import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
public interface IJdglWeekPlanService {

    JdglWeekPlan getJdglWeekPlan(JdglWeekPlan jdglWeekPlan);

    JdglWeekPlan getUsingWeekPlanByYearAndWeek(String year, String week);

    List<JdglWeekPlan> getJdglWeekPlanList(JdglWeekPlan jdglWeekPlan);

    int insertJdglWeekPlan(JdglWeekPlan jdglWeekPlan);

    int insertJdglWeekPlanList(List<JdglWeekPlan> jdglWeekPlanList);

    int updateJdglWeekPlan(JdglWeekPlan jdglWeekPlan);

    int updateJdglWeekPlanList(List<JdglWeekPlan> jdglWeekPlanList);

    int deleteJdglWeekPlan(JdglWeekPlan jdglWeekPlan);

    int deleteJdglWeekPlanByPks(List<Long> jdglWeekPlanPkList);

    JdglWeekPlan getInitJdglWeekPlan(JdglWeekPlan jdglWeekPlanParam);

    int adjust(JdglWeekPlan jdglWeekPlanParam);

    void updateTaskStatus(Long id);
}
