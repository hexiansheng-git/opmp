package com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.service;

import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.QqchTechRiskSolutions;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.vo.QqchTechRiskSolutionsVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:55:16
 * @remark 技术风险及应对措施
 */
public interface IQqchTechRiskSolutionsService {

    QqchTechRiskSolutions getQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    List<QqchTechRiskSolutions> getQqchTechRiskSolutionsList(QqchTechRiskSolutions qqchTechRiskSolutions);

    int insertQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    int updateQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    int updateQqchTechRiskSolutionsList(List<QqchTechRiskSolutions> qqchTechRiskSolutionsList);

    int deleteQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions);

    int deleteQqchTechRiskSolutionsByPks(List<Long> qqchTechRiskSolutionsPkList);

    /**
     * 获取技术风险及应对措施Vo
     * @param qqchTechRiskSolutions
     * @return
     */
    QqchTechRiskSolutionsVo getQqchTechRiskSolutionsVo(QqchTechRiskSolutions qqchTechRiskSolutions);

    /**
     * 保存/确认/提交
     * @param qqchTechRiskSolutionsVo
     * @return
     */
    void save(QqchTechRiskSolutionsVo qqchTechRiskSolutionsVo);
}
