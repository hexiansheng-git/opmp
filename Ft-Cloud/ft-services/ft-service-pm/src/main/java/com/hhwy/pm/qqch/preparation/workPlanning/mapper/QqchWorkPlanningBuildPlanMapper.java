package com.hhwy.pm.qqch.preparation.workPlanning.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import org.apache.ibatis.annotations.Param;

/**
 * @author zq
 * @date 2023-07-19 11:30:27
 * @remark 
 */
public interface QqchWorkPlanningBuildPlanMapper {
                                                                                                                                                                                                                                                                                                                
    QqchWorkPlanningBuildPlan getQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    List<QqchWorkPlanningBuildPlan> getQqchWorkPlanningBuildPlanList(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    int insertQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    int insertQqchWorkPlanningBuildPlanList(@Param("qqchWorkPlanningBuildPlanList") List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList);

    int updateQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    int updateQqchWorkPlanningBuildPlanList(@Param("qqchWorkPlanningBuildPlanList") List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList);
    
    int deleteQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    int deleteQqchWorkPlanningBuildPlanByPks(@Param("qqchWorkPlanningBuildPlanPkList") List<Long> qqchWorkPlanningBuildPlanPkList);
    }
