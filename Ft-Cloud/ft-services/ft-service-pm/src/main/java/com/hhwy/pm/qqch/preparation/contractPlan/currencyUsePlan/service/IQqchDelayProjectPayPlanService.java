package com.hhwy.pm.qqch.preparation.contractPlan.currencyUsePlan.service;

import com.hhwy.pm.qqch.preparation.contractPlan.currencyUsePlan.domain.QqchDelayProjectPayPlan;
import com.hhwy.pm.qqch.preparation.contractPlan.currencyUsePlan.domain.vo.QqchDelayProjectPayPlanVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:09:45
 * @remark
 */
public interface IQqchDelayProjectPayPlanService {

    QqchDelayProjectPayPlan getQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    List<QqchDelayProjectPayPlan> getQqchDelayProjectPayPlanList(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    int insertQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    int updateQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    int updateQqchDelayProjectPayPlanList(List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList);

    int deleteQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    int deleteQqchDelayProjectPayPlanByPks(List<Long> qqchDelayProjectPayPlanPkList);

    /**
     * 获取Vo
     * @param qqchDelayProjectPayPlan
     * @return
     */
    QqchDelayProjectPayPlanVo getQqchDelayProjectPayPlanVo(QqchDelayProjectPayPlan qqchDelayProjectPayPlan);

    /**
     * 保存/确认/提交
     * @param qqchDelayProjectPayPlanVo
     * @return
     */
    void save(QqchDelayProjectPayPlanVo qqchDelayProjectPayPlanVo);
}
