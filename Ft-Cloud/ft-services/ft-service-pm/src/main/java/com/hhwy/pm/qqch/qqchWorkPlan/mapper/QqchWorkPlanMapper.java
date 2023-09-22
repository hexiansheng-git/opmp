package com.hhwy.pm.qqch.qqchWorkPlan.mapper;

import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hwj
 * @date 2023-07-12 15:30:52
 * @remark
 */
public interface QqchWorkPlanMapper {

    QqchWorkPlan getQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

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
