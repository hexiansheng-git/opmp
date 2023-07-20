package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchChangeProcedurePlanVo;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
public interface IQqchChangeProcedurePlanService {

    /**
     * 获取变更程序策划集合
     * @return
     * @param version
     */
    QqchChangeProcedurePlanVo getQqchChangeProcedurePlanVo(BigDecimal version);

    /**
     * 批量编辑（新增和修改）
     * @param qqchChangeProcedurePlanVo
     */
    void save(QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo);

    /**
     * 确认
     * @param qqchChangeProcedurePlanVo
     * @return
     */
    void confirm(QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo);
}
