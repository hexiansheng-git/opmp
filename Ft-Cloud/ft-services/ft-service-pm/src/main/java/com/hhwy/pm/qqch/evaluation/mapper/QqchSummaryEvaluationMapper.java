package com.hhwy.pm.qqch.evaluation.mapper;

import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-24 16:48:10
 * @remark 前期策划总结评价
 */
public interface QqchSummaryEvaluationMapper {

    QqchSummaryEvaluation getQqchSummaryEvaluation(QqchSummaryEvaluation qqchSummaryEvaluation);

    List<QqchSummaryEvaluation> getQqchSummaryEvaluationList(QqchSummaryEvaluation qqchSummaryEvaluation);

    int insertQqchSummaryEvaluation(QqchSummaryEvaluation qqchSummaryEvaluation);

    int insertQqchSummaryEvaluationList(
        @Param("qqchSummaryEvaluationList") List<QqchSummaryEvaluation> qqchSummaryEvaluationList);

    int updateQqchSummaryEvaluation(QqchSummaryEvaluation qqchSummaryEvaluation);

    int updateQqchSummaryEvaluationList(@Param("list") List<QqchSummaryEvaluation> qqchSummaryEvaluationList);

    int deleteQqchSummaryEvaluation(QqchSummaryEvaluation qqchSummaryEvaluation);

    int deleteQqchSummaryEvaluationByPks(@Param("qqchSummaryEvaluationPkList") List<Long> qqchSummaryEvaluationPkList);
}
