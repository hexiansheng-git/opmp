package com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service;

import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
public interface IJdglQuarterImagePlanService {

    JdglQuarterImagePlan getJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan);

    List<JdglQuarterImagePlan> getJdglQuarterImagePlanList(JdglQuarterImagePlan jdglQuarterImagePlan);

    List<JdglQuarterImagePlan> getJdglQuarterImagePlanListByPlanId(Long planId);

    int insertJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan);

    int insertJdglQuarterImagePlanList(List<JdglQuarterImagePlan> jdglQuarterImagePlanList);

    int updateJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan);

    int updateJdglQuarterImagePlanList(List<JdglQuarterImagePlan> jdglQuarterImagePlanList);

    int deleteJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan);

    int deleteJdglQuarterImagePlanByPks(List<Long> jdglQuarterImagePlanPkList);

    int deleteJdglQuarterImagePlanByPlanId(Long planId);

    JdglQuarterPlan syncFromTotalPlan(JdglQuarterPlan jdglQuarterPlanParam);

    List<JdglQuarterImagePlan> getWbsListByYearAndQuarter(String quarter, String year);
}
