package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 19:02:54
 * @remark 比选方案比选内容
 */
public interface IQqchComparisonSchemeContentService {

    QqchComparisonSchemeContent getQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    List<QqchComparisonSchemeContent> getQqchComparisonSchemeContentList(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    int insertQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    int insertQqchComparisonSchemeContentList(List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList);

    int updateQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    int updateQqchComparisonSchemeContentList(List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList);

    int deleteQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    int deleteQqchComparisonSchemeContentByPks(List<Long> qqchComparisonSchemeContentPkList);
}
