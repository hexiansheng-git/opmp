package com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.service;

import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.domain.QqchStaffPlan;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:36:01
 * @remark
 */
public interface IQqchStaffPlanService {

    QqchStaffPlan getQqchStaffPlan(QqchStaffPlan qqchStaffPlan);

    List<QqchStaffPlan> getQqchStaffPlanList(QqchStaffPlan qqchStaffPlan);

    int insertQqchStaffPlan(QqchStaffPlan qqchStaffPlan);

    int insertQqchStaffPlanList(List<QqchStaffPlan> qqchStaffPlanList);

    int updateQqchStaffPlan(QqchStaffPlan qqchStaffPlan);

    int updateQqchStaffPlanList(List<QqchStaffPlan> qqchStaffPlanList);

    int deleteQqchStaffPlan(QqchStaffPlan qqchStaffPlan);

    int deleteQqchStaffPlanByPks(List<Long> qqchStaffPlanPkList);
}
