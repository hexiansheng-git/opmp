package com.hhwy.pm.qqch.sgch.qqchconst.service;

import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlan;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:24
 * @remark
 */
public interface IQqchConstStaffPlanService {

    QqchConstStaffPlan getQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan);

    List<QqchConstStaffPlan> getQqchConstStaffPlanList(QqchConstStaffPlan qqchConstStaffPlan);

    int insertQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan);

    int insertQqchConstStaffPlanList(List<QqchConstStaffPlan> qqchConstStaffPlanList);

    int updateQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan);

    int updateQqchConstStaffPlanList(List<QqchConstStaffPlan> qqchConstStaffPlanList);

    int deleteQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan);

    int deleteQqchConstStaffPlanByPks(List<Long> qqchConstStaffPlanPkList);

    void saveList(List<QqchConstStaffPlan> iStaffList);

    List<QqchConstStaffPlan>  list(QqchConstStaffPlan dealListDto);
}
