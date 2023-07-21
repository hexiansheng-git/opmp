package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeProcedurePlanVo;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
public interface IQqchOptimizeProcedurePlanService {

    /**
     * 获取优化程序策划集合
     * @return
     * @param version
     */
    QqchOptimizeProcedurePlanVo getQqchOptimizeProcedurePlanVo(BigDecimal version);

    /**
     * 保存
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    void save(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo);

    /**
     * 确认
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    void confirm(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo);
}
