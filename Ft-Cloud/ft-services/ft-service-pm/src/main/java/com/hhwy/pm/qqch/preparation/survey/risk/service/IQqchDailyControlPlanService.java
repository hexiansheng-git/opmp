package com.hhwy.pm.qqch.preparation.survey.risk.service;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchDailyControlPlanVo;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-13 11:39:57
 * @remark 日常管控策划
 */
public interface IQqchDailyControlPlanService {

    /**
     * 获取日常管控策划Vo
     * @return
     * @param version
     */
    QqchDailyControlPlanVo getQqchDailyControlPlanVo(BigDecimal version);

    /**
     * 保存
     * @param qqchDailyControlPlanVo
     * @return
     */
    void save(QqchDailyControlPlanVo qqchDailyControlPlanVo);

    /**
     * 确认
     * @param qqchDailyControlPlanVo
     * @return
     */
    void confirm(QqchDailyControlPlanVo qqchDailyControlPlanVo);
}
