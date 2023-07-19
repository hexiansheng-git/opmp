package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionReviewPlanVo;

/**
 * @author zhenlili
 * @date 2023-07-17 15:32:17
 * @remark 3.4.5施工方案编审计划
 */
public interface IQqchConstructionReviewPlanService {

    QqchConstructionReviewPlanVo getQqchConstructionReviewPlanList();

    void syncData();

    void batchSave(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo);

    void confirm(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo);
}
