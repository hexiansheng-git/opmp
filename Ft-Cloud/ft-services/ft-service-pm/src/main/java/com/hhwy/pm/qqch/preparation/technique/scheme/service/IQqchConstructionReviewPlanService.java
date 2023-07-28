package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionReviewPlanVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-07-17 15:32:17
 * @remark 3.4.5施工方案编审计划
 */
public interface IQqchConstructionReviewPlanService {

    QqchConstructionReviewPlanVo getQqchConstructionReviewPlanList(BigDecimal version);

    void syncData(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo);

    void batchSave(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo);
}
