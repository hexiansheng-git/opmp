package com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.mapper;

import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
public interface JdglQuarterPlanMapper {

    JdglQuarterPlan getJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan);

    List<JdglQuarterPlan> getJdglQuarterPlanList(JdglQuarterPlan jdglQuarterPlan);

    int insertJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan);

    int insertJdglQuarterPlanList(@Param("jdglQuarterPlanList") List<JdglQuarterPlan> jdglQuarterPlanList);

    int updateJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan);

    int updateJdglQuarterPlanList(@Param("jdglQuarterPlanList") List<JdglQuarterPlan> jdglQuarterPlanList);

    int deleteJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan);

    int deleteJdglQuarterPlanByPks(@Param("jdglQuarterPlanPkList") List<Long> jdglQuarterPlanPkList);
}
