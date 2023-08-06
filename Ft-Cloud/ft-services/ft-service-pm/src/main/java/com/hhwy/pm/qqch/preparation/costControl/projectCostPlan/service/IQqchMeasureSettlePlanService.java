package com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.service;

import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.QqchMeasureSettlePlan;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.vo.QqchMeasureSettlePlanVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:45:52
 * @remark
 */
public interface IQqchMeasureSettlePlanService {

    QqchMeasureSettlePlan getQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    List<QqchMeasureSettlePlan> getQqchMeasureSettlePlanList(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    int insertQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    int updateQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    int updateQqchMeasureSettlePlanList(List<QqchMeasureSettlePlan> qqchMeasureSettlePlanList);

    int deleteQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    int deleteQqchMeasureSettlePlanByPks(List<Long> qqchMeasureSettlePlanPkList);

    /**
     * 获取计量结算策划Vo
     * @param qqchMeasureSettlePlan
     * @return
     */
    QqchMeasureSettlePlanVo getQqchMeasureSettlePlanVo(QqchMeasureSettlePlan qqchMeasureSettlePlan);

    /**
     * 保存/确认/提交
     * @param qqchMeasureSettlePlanVo
     * @return
     */
    void save(QqchMeasureSettlePlanVo qqchMeasureSettlePlanVo);
}
