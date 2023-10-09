package com.hhwy.pm.qqch.evaluation.service;

import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;

/**
 * @author zhenglili
 * @date 2023-07-24 16:48:10
 * @remark 前期策划总结评价
 */
public interface IQqchSummaryEvaluationService {

    QqchSummaryEvaluation getQqchSummaryEvaluation(QqchSummaryEvaluation qqchSummaryEvaluation);

    void save(QqchSummaryEvaluation qqchSummaryEvaluation);

    void submit(QqchSummaryEvaluation qqchSummaryEvaluation);

    void updateQqchSummaryEvaluationProcess(Long id);

    void summaryEvaluationWarn(String type);
}
