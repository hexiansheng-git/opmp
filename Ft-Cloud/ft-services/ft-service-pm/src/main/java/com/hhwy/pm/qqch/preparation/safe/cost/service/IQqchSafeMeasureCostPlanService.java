package com.hhwy.pm.qqch.preparation.safe.cost.service;

import com.hhwy.pm.qqch.preparation.safe.cost.domain.vo.QqchSafeMeasureCostPlanVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-10 16:38:50
 * @remark 8.11 安全文明措施费策划
 */
public interface IQqchSafeMeasureCostPlanService {

    QqchSafeMeasureCostPlanVo getQqchSafeMeasureCostPlanList(BigDecimal version);

    void batchSave(QqchSafeMeasureCostPlanVo qqchSafeMeasureCostPlanVo);
}
