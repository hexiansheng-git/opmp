package com.hhwy.pm.qqch.preparation.workPlanning.service;

import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlanVo;

import java.util.List;
import java.util.Map;

/**
 * @author zq
 * @date 2023-07-19 11:30:27
 * @remark
 */
public interface IQqchWorkPlanningBuildPlanService {

    QqchWorkPlanningBuildPlan getQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    List<QqchWorkPlanningBuildPlan> getQqchWorkPlanningBuildPlanList(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    int insertQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    int insertQqchWorkPlanningBuildPlanList(QqchWorkPlanningBuildPlanVo qqchWorkPlanningBuildPlanVo);

    int updateQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    int updateQqchWorkPlanningBuildPlanList(List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList);

    int deleteQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan);

    int deleteQqchWorkPlanningBuildPlanByPks(List<Long> qqchWorkPlanningBuildPlanPkList);

    List<QqchWorkPlanningBuildPlan> getQqchWorkPlanningBuildPlanListHistory(QqchWorkPlanningBuildPlan plan);

    /**
     * 最大版本且有效
     * @param plan
     * @return
     */
    List<QqchWorkPlanningBuildPlan> getMaxVVData(QqchWorkPlanningBuildPlan plan);
}
