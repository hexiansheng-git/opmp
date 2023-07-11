package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:43
 * @remark 比选方案表头
 */
public interface IQqchComparisonSchemeHeaderService {

    List<QqchComparisonSchemeHeader> getQqchComparisonSchemeHeaderList(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);
}
