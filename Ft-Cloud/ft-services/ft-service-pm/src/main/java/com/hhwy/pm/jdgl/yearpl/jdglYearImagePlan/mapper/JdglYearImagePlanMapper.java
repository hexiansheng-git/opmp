package com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
public interface JdglYearImagePlanMapper {

    JdglYearImagePlan getJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan);

    List<JdglYearImagePlan> getJdglYearImagePlanList(JdglYearImagePlan jdglYearImagePlan);

    int insertJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan);

    int insertJdglYearImagePlanList(@Param("jdglYearImagePlanList") List<JdglYearImagePlan> jdglYearImagePlanList);

    int updateJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan);

    int updateJdglYearImagePlanList(@Param("jdglYearImagePlanList") List<JdglYearImagePlan> jdglYearImagePlanList);

    int deleteJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan);

    int deleteJdglYearImagePlanByPks(@Param("jdglYearImagePlanPkList") List<Long> jdglYearImagePlanPkList);

    List<JdglYearImagePlan> getWbsListByYear(String year);

    int deleteJdglYearImagePlanByYearPlanId(@Param("yearPlanId") Long yearPlanId);

    BigDecimal getThisPlanAmt(Long yearPlanId);
}
