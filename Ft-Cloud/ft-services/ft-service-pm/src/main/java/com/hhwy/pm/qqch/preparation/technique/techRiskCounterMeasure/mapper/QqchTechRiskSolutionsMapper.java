package com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.mapper;

import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.QqchTechRiskSolutions;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:55:16
 * @remark
 */
@Repository
public interface QqchTechRiskSolutionsMapper {

    QqchTechRiskSolutions getQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    List<QqchTechRiskSolutions> getQqchTechRiskSolutionsList(QqchTechRiskSolutions qqchTechRiskSolutions);

    int insertQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    int insertQqchTechRiskSolutionsList(@Param("qqchTechRiskSolutionsList") List<QqchTechRiskSolutions> qqchTechRiskSolutionsList);

    int updateQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    int updateQqchTechRiskSolutionsList(@Param("list") List<QqchTechRiskSolutions> qqchTechRiskSolutionsList);

    int deleteQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    int deleteQqchTechRiskSolutionsByPks(@Param("qqchTechRiskSolutionsPkList") List<Long> qqchTechRiskSolutionsPkList);
}
