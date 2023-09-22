package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchComparisonSchemeVo;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
public interface IQqchComparisonSchemeService {

    /**
     * 获取方案集合
     * @return
     * @param version
     */
    QqchComparisonSchemeVo getQqchComparisonSchemeVo(BigDecimal version);

    /**
     * 初始化表格
     * @return
     */
    QqchComparisonScheme init();

    /**
     * 保存
     * @param qqchComparisonSchemeVo
     * @return
     */
    void save(QqchComparisonSchemeVo qqchComparisonSchemeVo);

    /**
     * 确认
     * @param qqchComparisonSchemeVo
     * @return
     */
    void confirm(QqchComparisonSchemeVo qqchComparisonSchemeVo);
}
