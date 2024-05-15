package com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlanQueryVO;

import java.util.List;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:20
 * @remark
 */
public interface IJdglMainPlanService {

    JdglMainPlan getJdglMainPlan(JdglMainPlan jdglMainPlan);

    JdglMainPlan getUsingJdglMainPlan();

    JdglMainPlan getUsingJdglMainPlan(JdglMainPlanQueryVO queryVO);

    JdglMainPlan getUsingJdglMainPlanNoItem();

    List<JdglMainPlan> getJdglMainPlanList(JdglMainPlan jdglMainPlan);

    int insertJdglMainPlan(JdglMainPlan jdglMainPlan);

    int insertJdglMainPlanList(List<JdglMainPlan> jdglMainPlanList);

    int updateJdglMainPlan(JdglMainPlan jdglMainPlan);

    int updateJdglMainPlanList(List<JdglMainPlan> jdglMainPlanList);

    int deleteJdglMainPlan(JdglMainPlan jdglMainPlan);

    int deleteJdglMainPlanByPks(List<Long> jdglMainPlanPkList);

    JdglMainPlan getBaseMainPlan();

    void test(Long id);

    void updateJdglBaseMainPlan();

    List<JdglMainPlan> getBaseMainPlanList(String tenantKey);

    JdglMainPlan getBaseMainPlanDetail(String tenantKey, Long id);

}
