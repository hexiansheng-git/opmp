package com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.mapper;

import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
public interface JdglWeekPlanMapper {

    JdglWeekPlan getJdglWeekPlan(JdglWeekPlan jdglWeekPlan);

    List<JdglWeekPlan> getJdglWeekPlanList(JdglWeekPlan jdglWeekPlan);

    int insertJdglWeekPlan(JdglWeekPlan jdglWeekPlan);

    int insertJdglWeekPlanList(@Param("jdglWeekPlanList") List<JdglWeekPlan> jdglWeekPlanList);

    int updateJdglWeekPlan(JdglWeekPlan jdglWeekPlan);

    int updateJdglWeekPlanList(@Param("jdglWeekPlanList") List<JdglWeekPlan> jdglWeekPlanList);

    int deleteJdglWeekPlan(JdglWeekPlan jdglWeekPlan);

    int deleteJdglWeekPlanByPks(@Param("jdglWeekPlanPkList") List<Long> jdglWeekPlanPkList);
}
