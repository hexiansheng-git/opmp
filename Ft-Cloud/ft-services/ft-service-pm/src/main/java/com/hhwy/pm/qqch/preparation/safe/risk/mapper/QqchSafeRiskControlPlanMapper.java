package com.hhwy.pm.qqch.preparation.safe.risk.mapper;

import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskControlPlan;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-07 13:53:33
 * @remark 8.2.3 安全风险过程管控策划
 */
public interface QqchSafeRiskControlPlanMapper {

    QqchSafeRiskControlPlan getQqchSafeRiskControlPlan(QqchSafeRiskControlPlan qqchSafeRiskControlPlan);

    List<QqchSafeRiskControlPlan> getQqchSafeRiskControlPlanList(QqchSafeRiskControlPlan qqchSafeRiskControlPlan);

    int insertQqchSafeRiskControlPlan(QqchSafeRiskControlPlan qqchSafeRiskControlPlan);

    int insertQqchSafeRiskControlPlanList(
        @Param("qqchSafeRiskControlPlanList") List<QqchSafeRiskControlPlan> qqchSafeRiskControlPlanList);

    int updateQqchSafeRiskControlPlan(QqchSafeRiskControlPlan qqchSafeRiskControlPlan);

    int updateQqchSafeRiskControlPlanList(
        @Param("list") List<QqchSafeRiskControlPlan> qqchSafeRiskControlPlanList);

    int deleteQqchSafeRiskControlPlan(QqchSafeRiskControlPlan qqchSafeRiskControlPlan);

    int deleteQqchSafeRiskControlPlanByPks(
        @Param("qqchSafeRiskControlPlanPkList") List<Long> qqchSafeRiskControlPlanPkList);
}
