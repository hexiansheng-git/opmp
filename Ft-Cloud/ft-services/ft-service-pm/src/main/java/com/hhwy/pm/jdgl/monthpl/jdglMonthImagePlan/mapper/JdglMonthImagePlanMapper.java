package com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.mapper;

import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
public interface JdglMonthImagePlanMapper {

    JdglMonthImagePlan getJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan);

    List<JdglMonthImagePlan> getJdglMonthImagePlanList(JdglMonthImagePlan jdglMonthImagePlan);

    int insertJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan);

    int insertJdglMonthImagePlanList(@Param("jdglMonthImagePlanList") List<JdglMonthImagePlan> jdglMonthImagePlanList);

    int updateJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan);

    int updateJdglMonthImagePlanList(@Param("jdglMonthImagePlanList") List<JdglMonthImagePlan> jdglMonthImagePlanList);

    int deleteJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan);

    int deleteJdglMonthImagePlanByPks(@Param("jdglMonthImagePlanPkList") List<Long> jdglMonthImagePlanPkList);

    List<JdglMonthImagePlan> getWbsListByYearAndMonth(@Param("year") String year,@Param("month") String month);

    int deleteJdglMonthImagePlanByPlanId(@Param("planId") Long planId);

    BigDecimal getThisPlanAmt(@Param("planId") Long planId);
}
