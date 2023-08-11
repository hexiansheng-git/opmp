package com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.service;

import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.domain.QqchSubpackageBidPlan;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.domain.vo.QqchSubpackageBidPlanVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:44
 * @remark
 */
public interface IQqchSubpackageBidPlanService {

    QqchSubpackageBidPlan getQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    List<QqchSubpackageBidPlan> getQqchSubpackageBidPlanList(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    int insertQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    int insertQqchSubpackageBidPlanList(List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList);

    int updateQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    int updateQqchSubpackageBidPlanList(List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList);

    int deleteQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    int deleteQqchSubpackageBidPlanByPks(List<Long> qqchSubpackageBidPlanPkList);

    /**
     * 获取分包招标策划Vo
     * @param qqchSubpackageBidPlan
     * @return
     */
    QqchSubpackageBidPlanVo getQqchSubpackageBidPlanVo(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    /**
     * 保存/确认/提交
     * @param qqchSubpackageBidPlanVo
     * @return
     */
    void save(QqchSubpackageBidPlanVo qqchSubpackageBidPlanVo);
}
