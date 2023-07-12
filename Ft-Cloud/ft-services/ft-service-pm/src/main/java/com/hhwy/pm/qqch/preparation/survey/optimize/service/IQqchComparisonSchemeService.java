package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchComparisonSchemeVo;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
public interface IQqchComparisonSchemeService {

    /**
     * 获取方案集合
     * @return
     */
    QqchComparisonSchemeVo getQqchComparisonSchemeVo();

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

    /**
     * 删除方案
     * @param schemeId 方案id
     * @return
     */
    int deleteQqchComparisonSchemeById(Long schemeId);

    /**
     * 删除行
     * @param schemeId
     * @param sorts
     * @return
     */
    int deleteLine(Long schemeId, String[] sorts);

    /**
     * 删除列（多列）
     * @param headerIds
     * @return
     */
    int deleteColumn(Long[] headerIds);
}
