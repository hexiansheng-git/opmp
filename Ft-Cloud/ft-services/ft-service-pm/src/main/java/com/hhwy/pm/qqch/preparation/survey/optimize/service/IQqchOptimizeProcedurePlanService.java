package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeProcedurePlan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
public interface IQqchOptimizeProcedurePlanService {

    QqchOptimizeProcedurePlan getQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan);

    /**
     * 获取优化程序策划集合
     * @return
     */
    List<QqchOptimizeProcedurePlan> getQqchOptimizeProcedurePlanList();

    /**
     * 批量编辑（新增和修改）
     * @param qqchOptimizeProcedurePlanListParam
     * @return
     */
    int editQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanListParam);


    int insertQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan);

    int insertQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList);

    int updateQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan);

    int updateQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList);

    int deleteQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan);

    int deleteQqchOptimizeProcedurePlanByPks(List<Long> qqchOptimizeProcedurePlanPkList);

}
