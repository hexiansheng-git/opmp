package com.hhwy.pm.jdgl.yearpl.jdglYearPlan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
public interface JdglYearPlanMapper {

    JdglYearPlan getJdglYearPlan(JdglYearPlan jdglYearPlan);

    List<JdglYearPlan> getJdglYearPlanList(JdglYearPlan jdglYearPlan);

    int insertJdglYearPlan(JdglYearPlan jdglYearPlan);

    int insertJdglYearPlanList(@Param("jdglYearPlanList") List<JdglYearPlan> jdglYearPlanList);

    int updateJdglYearPlan(JdglYearPlan jdglYearPlan);

    int updateJdglYearPlanList(@Param("jdglYearPlanList") List<JdglYearPlan> jdglYearPlanList);

    int deleteJdglYearPlan(JdglYearPlan jdglYearPlan);

    int deleteJdglYearPlanByPks(@Param("jdglYearPlanPkList") List<Long> jdglYearPlanPkList);
}
