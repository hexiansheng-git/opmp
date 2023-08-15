package com.hhwy.pm.jdgl.mapper;

import java.util.List;

import com.hhwy.pm.jdgl.domain.JdglDatePlan;
import org.apache.ibatis.annotations.Param;

/**
 * @author fushudong
 * @date 2023-08-14 17:55:25
 * @remark 
 */
public interface JdglDatePlanMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                
    JdglDatePlan getJdglDatePlan(JdglDatePlan jdglDatePlan);

    List<JdglDatePlan> getJdglDatePlanList(JdglDatePlan jdglDatePlan);

    int insertJdglDatePlan(JdglDatePlan jdglDatePlan);

    int insertJdglDatePlanList(@Param("jdglDatePlanList") List<JdglDatePlan> jdglDatePlanList);

    int updateJdglDatePlan(JdglDatePlan jdglDatePlan);

    int updateJdglDatePlanList(@Param("jdglDatePlanList") List<JdglDatePlan> jdglDatePlanList);
    
    int deleteJdglDatePlan(JdglDatePlan jdglDatePlan);

    int deleteJdglDatePlanByPks(@Param("jdglDatePlanPkList") List<Long> jdglDatePlanPkList);
}
