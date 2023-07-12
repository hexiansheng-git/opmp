package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;

import java.util.List;

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
    List<QqchComparisonScheme> getQqchComparisonSchemeList();

    /**
     * 批量编辑
     * @param qqchComparisonSchemeList
     * @return
     */
    int editQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList);

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
