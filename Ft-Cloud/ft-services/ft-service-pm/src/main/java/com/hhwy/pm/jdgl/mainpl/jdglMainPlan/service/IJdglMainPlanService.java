package com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service;

import java.util.List;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:20
 * @remark
 */
public interface IJdglMainPlanService {

    JdglMainPlan getJdglMainPlan(JdglMainPlan jdglMainPlan);

    List<JdglMainPlan> getJdglMainPlanList(JdglMainPlan jdglMainPlan);

    int insertJdglMainPlan(JdglMainPlan jdglMainPlan);

    int insertJdglMainPlanList(List<JdglMainPlan> jdglMainPlanList);

    int updateJdglMainPlan(JdglMainPlan jdglMainPlan);

    int updateJdglMainPlanList(List<JdglMainPlan> jdglMainPlanList);

    int deleteJdglMainPlan(JdglMainPlan jdglMainPlan);

    int deleteJdglMainPlanByPks(List<Long> jdglMainPlanPkList);
}
