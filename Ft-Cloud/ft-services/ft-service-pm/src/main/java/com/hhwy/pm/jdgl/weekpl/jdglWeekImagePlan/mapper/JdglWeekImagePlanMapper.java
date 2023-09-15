package com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.mapper;

import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
public interface JdglWeekImagePlanMapper {

    JdglWeekImagePlan getJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan);

    List<JdglWeekImagePlan> getJdglWeekImagePlanList(JdglWeekImagePlan jdglWeekImagePlan);

    int insertJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan);

    int insertJdglWeekImagePlanList(@Param("jdglWeekImagePlanList") List<JdglWeekImagePlan> jdglWeekImagePlanList);

    int updateJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan);

    int updateJdglWeekImagePlanList(@Param("jdglWeekImagePlanList") List<JdglWeekImagePlan> jdglWeekImagePlanList);

    int deleteJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan);

    int deleteJdglWeekImagePlanByPks(@Param("jdglWeekImagePlanPkList") List<Long> jdglWeekImagePlanPkList);

    List<JdglWeekImagePlan> getWbsListByYearAndWeek(@Param("year") String year,@Param("week")  String week);
}
