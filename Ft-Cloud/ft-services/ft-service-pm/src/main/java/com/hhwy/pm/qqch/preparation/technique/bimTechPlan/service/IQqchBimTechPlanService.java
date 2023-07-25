package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service;

import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlan;

import java.util.List;


/**
 * @author han
 * @date 2023-07-25 10:45:57
 * @remark
 */
public interface IQqchBimTechPlanService {

    QqchBimTechPlan getQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    List<QqchBimTechPlan> getQqchBimTechPlanList(QqchBimTechPlan qqchBimTechPlan);

    int insertQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    int insertQqchBimTechPlanList(List<QqchBimTechPlan> qqchBimTechPlanList);

    int updateQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    int updateQqchBimTechPlanList(List<QqchBimTechPlan> qqchBimTechPlanList);

    int deleteQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    int deleteQqchBimTechPlanByPks(List<Long> qqchBimTechPlanPkList);
}
