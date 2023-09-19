package com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service;

import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
public interface IJdglQuarterPlanService {

    JdglQuarterPlan getJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan);

    JdglQuarterPlan getUsingQuarterPlanByYearAndQuarter(String year,String quarter);

    List<JdglQuarterPlan> getJdglQuarterPlanList(JdglQuarterPlan jdglQuarterPlan);

    int insertJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan);

    int insertJdglQuarterPlanList(List<JdglQuarterPlan> jdglQuarterPlanList);

    int updateJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan);

    int updateJdglQuarterPlanList(List<JdglQuarterPlan> jdglQuarterPlanList);

    int deleteJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan);

    int deleteJdglQuarterPlanByPks(List<Long> jdglQuarterPlanPkList);

    JdglQuarterPlan getInitJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlanParam);

    JdglQuarterPlan adjust(JdglQuarterPlan jdglQuarterPlanParam);

    void updateTaskStatus(Long id);
}
