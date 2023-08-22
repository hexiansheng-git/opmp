package com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.service;

import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.QqchSubpackageBidPlan;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.vo.QqchSubpackageBidPlanVo;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;

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

    /**
     * 处理选择的班组数据
     * @param qqchConstList
     * @return
     */
    List<QqchSubpackageBidPlan> disposeSelectedData(List<QqchConst> qqchConstList);
}
