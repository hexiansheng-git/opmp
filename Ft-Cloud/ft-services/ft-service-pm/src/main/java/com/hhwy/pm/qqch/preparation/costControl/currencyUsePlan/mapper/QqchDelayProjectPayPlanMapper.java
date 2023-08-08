package com.hhwy.pm.qqch.preparation.costControl.currencyUsePlan.mapper;

import com.hhwy.pm.qqch.preparation.costControl.currencyUsePlan.domain.QqchDelayProjectPayPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:09:45
 * @remark
 */
@Repository
public interface QqchDelayProjectPayPlanMapper {

    QqchDelayProjectPayPlan getQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    List<QqchDelayProjectPayPlan> getQqchDelayProjectPayPlanList(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    int insertQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    int insertQqchDelayProjectPayPlanList(@Param("qqchDelayProjectPayPlanList") List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList);

    int updateQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    int updateQqchDelayProjectPayPlanList(@Param("list") List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList);

    int deleteQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    int deleteQqchDelayProjectPayPlanByPks(@Param("qqchDelayProjectPayPlanPkList") List<Long> qqchDelayProjectPayPlanPkList);
}
