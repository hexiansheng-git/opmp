package com.hhwy.pm.qqch.preparation.safe.cost.mapper;

import com.hhwy.pm.qqch.preparation.safe.cost.domain.QqchSafeMeasureCostPlan;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-10 16:38:50
 * @remark 8.11 安全文明措施费策划
 */
public interface QqchSafeMeasureCostPlanMapper {

    QqchSafeMeasureCostPlan getQqchSafeMeasureCostPlan(QqchSafeMeasureCostPlan qqchSafeMeasureCostPlan);

    List<QqchSafeMeasureCostPlan> getQqchSafeMeasureCostPlanList(QqchSafeMeasureCostPlan qqchSafeMeasureCostPlan);

    int insertQqchSafeMeasureCostPlan(QqchSafeMeasureCostPlan qqchSafeMeasureCostPlan);

    int insertQqchSafeMeasureCostPlanList(
        @Param("qqchSafeMeasureCostPlanList") List<QqchSafeMeasureCostPlan> qqchSafeMeasureCostPlanList);

    int updateQqchSafeMeasureCostPlan(QqchSafeMeasureCostPlan qqchSafeMeasureCostPlan);

    int updateQqchSafeMeasureCostPlanList(@Param("list") List<QqchSafeMeasureCostPlan> qqchSafeMeasureCostPlanList);

    int deleteQqchSafeMeasureCostPlan(QqchSafeMeasureCostPlan qqchSafeMeasureCostPlan);

    int deleteQqchSafeMeasureCostPlanByPks(
        @Param("qqchSafeMeasureCostPlanPkList") List<Long> qqchSafeMeasureCostPlanPkList);
}
