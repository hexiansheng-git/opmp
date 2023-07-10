package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:43
 * @remark 比选方案表头
 */
public interface IQqchComparisonSchemeHeaderService {

    QqchComparisonSchemeHeader getQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    List<QqchComparisonSchemeHeader> getQqchComparisonSchemeHeaderList(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    int insertQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    int insertQqchComparisonSchemeHeaderList(List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList);

    int updateQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    int updateQqchComparisonSchemeHeaderList(List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList);

    int deleteQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    int deleteQqchComparisonSchemeHeaderByPks(List<Long> qqchComparisonSchemeHeaderPkList);
}
