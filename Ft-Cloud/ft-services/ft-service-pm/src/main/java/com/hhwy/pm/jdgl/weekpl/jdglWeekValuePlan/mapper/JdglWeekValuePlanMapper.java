package com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.mapper;

import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.domain.JdglWeekValuePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
public interface JdglWeekValuePlanMapper {

    JdglWeekValuePlan getJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan);

    List<JdglWeekValuePlan> getJdglWeekValuePlanList(JdglWeekValuePlan jdglWeekValuePlan);

    int insertJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan);

    int insertJdglWeekValuePlanList(@Param("jdglWeekValuePlanList") List<JdglWeekValuePlan> jdglWeekValuePlanList);

    int updateJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan);

    int updateJdglWeekValuePlanList(@Param("jdglWeekValuePlanList") List<JdglWeekValuePlan> jdglWeekValuePlanList);

    int deleteJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan);

    int deleteJdglWeekValuePlanByPks(@Param("jdglWeekValuePlanPkList") List<Long> jdglWeekValuePlanPkList);

    List<JdglWeekValuePlan> getBillListByYearAndWeek(@Param("year") String year,@Param("week")  String week);

    int deleteJdglWeekValuePlanByPlanId(@Param("planId") Long planId);

    List<JdglWeekValuePlan> getBillListByNext(@Param("year") String year,@Param("week")  String week);
}
