package com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.mapper;

import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
public interface JdglQuarterImagePlanMapper {

    JdglQuarterImagePlan getJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan);

    List<JdglQuarterImagePlan> getJdglQuarterImagePlanList(JdglQuarterImagePlan jdglQuarterImagePlan);

    int insertJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan);

    int insertJdglQuarterImagePlanList(@Param("jdglQuarterImagePlanList") List<JdglQuarterImagePlan> jdglQuarterImagePlanList);

    int updateJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan);

    int updateJdglQuarterImagePlanList(@Param("jdglQuarterImagePlanList") List<JdglQuarterImagePlan> jdglQuarterImagePlanList);

    int deleteJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan);

    int deleteJdglQuarterImagePlanByPks(@Param("jdglQuarterImagePlanPkList") List<Long> jdglQuarterImagePlanPkList);
}
