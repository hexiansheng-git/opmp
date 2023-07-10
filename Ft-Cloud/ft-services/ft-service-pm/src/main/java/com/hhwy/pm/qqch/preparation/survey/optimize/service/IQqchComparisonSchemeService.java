package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
public interface IQqchComparisonSchemeService {

    QqchComparisonScheme getQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme);

    List<QqchComparisonScheme> getQqchComparisonSchemeList(QqchComparisonScheme qqchComparisonScheme);

    int insertQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme);

    int insertQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList);

    int updateQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme);

    int updateQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList);

    int deleteQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme);

    int deleteQqchComparisonSchemeByPks(List<Long> qqchComparisonSchemePkList);
}
