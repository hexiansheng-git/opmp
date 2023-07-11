package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
public interface IQqchChangeProcedurePlanService {

    QqchChangeProcedurePlan getQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan);

    /**
     * 获取变更程序策划集合
     * @return
     */
    List<QqchChangeProcedurePlan> getQqchChangeProcedurePlanList();

    /**
     * 批量编辑（新增和修改）
     * @param qqchChangeProcedurePlanListParam
     * @return
     */
    int editQqchChangeProcedurePlanList(List<QqchChangeProcedurePlan> qqchChangeProcedurePlanListParam);

    int insertQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan);

    int insertQqchChangeProcedurePlanList(List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList);

    int updateQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan);

    int updateQqchChangeProcedurePlanList(List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList);

    int deleteQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan);

    int deleteQqchChangeProcedurePlanByPks(List<Long> qqchChangeProcedurePlanPkList);
}
