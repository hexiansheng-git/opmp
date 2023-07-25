package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchCraftDeclarePlan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:02
 * @remark 工艺工法申报计划
 */
public interface IQqchCraftDeclarePlanService {

    QqchCraftDeclarePlan getQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    List<QqchCraftDeclarePlan> getQqchCraftDeclarePlanList(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int insertQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int insertQqchCraftDeclarePlanList(List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList);

    int updateQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int updateQqchCraftDeclarePlanList(List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList);

    int deleteQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int deleteQqchCraftDeclarePlanByPks(List<Long> qqchCraftDeclarePlanPkList);
}
