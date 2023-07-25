package com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.service;

import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.QqchTechRiskSolutions;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:55:16
 * @remark
 */
public interface IQqchTechRiskSolutionsService {

    QqchTechRiskSolutions getQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    List<QqchTechRiskSolutions> getQqchTechRiskSolutionsList(QqchTechRiskSolutions qqchTechRiskSolutions);

    int insertQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    int insertQqchTechRiskSolutionsList(List<QqchTechRiskSolutions> qqchTechRiskSolutionsList);

    int updateQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    int updateQqchTechRiskSolutionsList(List<QqchTechRiskSolutions> qqchTechRiskSolutionsList);

    int deleteQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    int deleteQqchTechRiskSolutionsByPks(List<Long> qqchTechRiskSolutionsPkList);
}
