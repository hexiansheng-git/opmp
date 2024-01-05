package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchSimilarProjectScheme;

import java.util.List;


/**
 * @author han
 * @date 2024-01-05 11:49:06
 * @remark
 */
public interface IQqchSimilarProjectSchemeService {

    QqchSimilarProjectScheme getQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    List<QqchSimilarProjectScheme> getQqchSimilarProjectSchemeList(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    int insertQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    int insertQqchSimilarProjectSchemeList(List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList);

    int updateQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    int updateQqchSimilarProjectSchemeList(List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList);

    int deleteQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    int deleteQqchSimilarProjectSchemeByPks(List<Long> qqchSimilarProjectSchemePkList);
}
