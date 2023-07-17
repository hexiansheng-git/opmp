package com.hhwy.pm.qqch.preparation.technique.scheme.mapper;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenlili
 * @date 2023-07-17 15:32:17
 * @remark 3.4.5施工方案编审计划
 */
public interface QqchConstructionReviewPlanMapper {

    QqchConstructionReviewPlan getQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan);

    List<QqchConstructionReviewPlan> getQqchConstructionReviewPlanList(
        QqchConstructionReviewPlan qqchConstructionReviewPlan);

    int insertQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan);

    int insertQqchConstructionReviewPlanList(
        @Param("qqchConstructionReviewPlanList") List<QqchConstructionReviewPlan> qqchConstructionReviewPlanList);

    int updateQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan);

    int updateQqchConstructionReviewPlanList(@Param("list") List<QqchConstructionReviewPlan> qqchConstructionReviewPlanList);

    int deleteQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan);

    int deleteQqchConstructionReviewPlanByPks(
        @Param("qqchConstructionReviewPlanPkList") List<Long> qqchConstructionReviewPlanPkList);
}
