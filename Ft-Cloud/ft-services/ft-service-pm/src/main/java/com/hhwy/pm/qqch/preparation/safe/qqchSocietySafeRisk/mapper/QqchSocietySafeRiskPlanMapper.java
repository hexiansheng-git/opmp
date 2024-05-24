package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRiskPlan;
import org.apache.ibatis.annotations.Param;

/**
 * @author hwj
 * @date 2024-05-23 17:36:41
 * @remark
 */
public interface QqchSocietySafeRiskPlanMapper {

    QqchSocietySafeRiskPlan getQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    List<QqchSocietySafeRiskPlan> getQqchSocietySafeRiskPlanList(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    int insertQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    int insertQqchSocietySafeRiskPlanList(@Param("qqchSocietySafeRiskPlanList") List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanList);

    int updateQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    int updateQqchSocietySafeRiskPlanList(@Param("qqchSocietySafeRiskPlanList") List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanList);

    int deleteQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    int deleteQqchSocietySafeRiskPlanByPks(@Param("qqchSocietySafeRiskPlanPkList") List<Long> qqchSocietySafeRiskPlanPkList);
}
