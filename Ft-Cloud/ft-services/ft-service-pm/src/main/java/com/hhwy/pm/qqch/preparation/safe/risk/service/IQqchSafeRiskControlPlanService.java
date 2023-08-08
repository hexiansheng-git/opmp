package com.hhwy.pm.qqch.preparation.safe.risk.service;

import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskControlPlanVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 13:53:33
 * @remark 8.2.3 安全风险过程管控策划
 */
public interface IQqchSafeRiskControlPlanService {

    QqchSafeRiskControlPlanVo getQqchSafeRiskControlPlanList(BigDecimal version);

    void batchSave(QqchSafeRiskControlPlanVo qqchSafeRiskControlPlanVo);
}
