package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlanBudget;

import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:51:20
 * @remark
 */
public interface IQqchAdvancedVindicatePlanBudgetService {

    QqchAdvancedVindicatePlanBudget getQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    List<QqchAdvancedVindicatePlanBudget> getQqchAdvancedVindicatePlanBudgetList(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    int insertQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    int insertQqchAdvancedVindicatePlanBudgetList(List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList);

    int updateQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    int updateQqchAdvancedVindicatePlanBudgetList(List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList);

    int deleteQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    int deleteQqchAdvancedVindicatePlanBudgetByPks(List<Long> qqchAdvancedVindicatePlanBudgetPkList);
}
