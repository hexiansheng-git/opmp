package com.hhwy.pm.qqch.preparation.survey.risk.service;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchDailyControlPlan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:39:57
 * @remark 日常管控策划
 */
public interface IQqchDailyControlPlanService {

    QqchDailyControlPlan getQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan);

    List<QqchDailyControlPlan> getQqchDailyControlPlanList(QqchDailyControlPlan qqchDailyControlPlan);

    int insertQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan);

    int insertQqchDailyControlPlanList(List<QqchDailyControlPlan> qqchDailyControlPlanList);

    int updateQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan);

    int updateQqchDailyControlPlanList(List<QqchDailyControlPlan> qqchDailyControlPlanList);

    int deleteQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan);

    int deleteQqchDailyControlPlanByPks(List<Long> qqchDailyControlPlanPkList);
}
