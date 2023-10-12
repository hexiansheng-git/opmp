package com.hhwy.pm.qqch.qqchWorkPlan.mapper;

import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hwj
 * @date 2023-07-12 15:30:52
 * @remark
 */
@Repository
public interface QqchWorkPlanMapper {

    QqchWorkPlan getQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

    /**
     * 获取第一个版本的工作计划数据
     * @return
     */
    QqchWorkPlan getFirstVersionQqchWorkPlan();

    QqchWorkPlan getValidMaxVersionWorkPlan();

    /**
     * 根据流程状态获取工作计划数据
     * @param flowStatus
     * @return
     */
    QqchWorkPlan getWorkPlanListByFlowStatus(@Param("flowStatus") String flowStatus);

    List<QqchWorkPlan> getQqchWorkPlanList(QqchWorkPlan qqchWorkPlan);
    int getQqchWorkPlanListCount(QqchWorkPlan qqchWorkPlan);

    int insertQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

    int insertQqchWorkPlanList(@Param("qqchWorkPlanList") List<QqchWorkPlan> qqchWorkPlanList);

    int updateQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

    int updateQqchWorkPlanList(@Param("qqchWorkPlanList") List<QqchWorkPlan> qqchWorkPlanList);

    int deleteQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

    int deleteQqchWorkPlanByPks(@Param("qqchWorkPlanPkList") List<Long> qqchWorkPlanPkList);

    void updateAllToInvalid();
}
