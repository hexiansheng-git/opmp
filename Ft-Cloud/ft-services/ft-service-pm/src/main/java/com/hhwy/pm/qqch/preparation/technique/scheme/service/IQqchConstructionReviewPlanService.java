package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionReviewPlanVo;
import java.util.List;

/**
 * @author zhenlili
 * @date 2023-07-17 15:32:17
 * @remark 3.4.5施工方案编审计划
 */
public interface IQqchConstructionReviewPlanService {

    QqchConstructionReviewPlan getQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan);

    QqchConstructionReviewPlanVo getQqchConstructionReviewPlanList();

    void syncData();

    int insertQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan);

    void batchSave(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo);

    int updateQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan);

    int updateQqchConstructionReviewPlanList(List<QqchConstructionReviewPlan> qqchConstructionReviewPlanList);

    int deleteQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan);

    int deleteQqchConstructionReviewPlanByPks(List<Long> qqchConstructionReviewPlanPkList);
}
