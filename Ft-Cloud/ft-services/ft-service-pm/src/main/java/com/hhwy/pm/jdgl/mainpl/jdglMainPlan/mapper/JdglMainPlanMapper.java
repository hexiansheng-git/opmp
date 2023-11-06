package com.hhwy.pm.jdgl.mainpl.jdglMainPlan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:20
 * @remark
 */
public interface JdglMainPlanMapper {

    JdglMainPlan getJdglMainPlan(JdglMainPlan jdglMainPlan);

    List<JdglMainPlan> getJdglMainPlanList(JdglMainPlan jdglMainPlan);

    int insertJdglMainPlan(JdglMainPlan jdglMainPlan);

    int insertJdglMainPlanList(@Param("jdglMainPlanList") List<JdglMainPlan> jdglMainPlanList);

    int updateJdglMainPlan(JdglMainPlan jdglMainPlan);

    int updateJdglMainPlanList(@Param("jdglMainPlanList") List<JdglMainPlan> jdglMainPlanList);

    int deleteJdglMainPlan(JdglMainPlan jdglMainPlan);

    int deleteJdglMainPlanByPks(@Param("jdglMainPlanPkList") List<Long> jdglMainPlanPkList);

    JdglMainPlan getMinVersionMainPlan();
}
