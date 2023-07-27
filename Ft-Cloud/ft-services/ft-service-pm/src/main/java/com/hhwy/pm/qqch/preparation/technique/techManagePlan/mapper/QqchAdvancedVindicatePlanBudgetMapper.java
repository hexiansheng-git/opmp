package com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlanBudget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:51:20
 * @remark
 */
@Repository
public interface QqchAdvancedVindicatePlanBudgetMapper {

    QqchAdvancedVindicatePlanBudget getQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    List<QqchAdvancedVindicatePlanBudget> getQqchAdvancedVindicatePlanBudgetList(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    int insertQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    int insertQqchAdvancedVindicatePlanBudgetList(@Param("qqchAdvancedVindicatePlanBudgetList") List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList);

    int updateQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    int updateQqchAdvancedVindicatePlanBudgetList(@Param("list") List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList);

    int deleteQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget);

    int deleteQqchAdvancedVindicatePlanBudgetByPks(@Param("qqchAdvancedVindicatePlanBudgetPkList") List<Long> qqchAdvancedVindicatePlanBudgetPkList);
}
