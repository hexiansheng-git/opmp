package com.hhwy.pm.qqch.preparation.safe.organ.service;

import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchSafeOrganDutyPlanVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 13:49:43
 * @remark 8.1.1 安全组织职责策划
 */
public interface IQqchSafeOrganDutyPlanService {

    QqchSafeOrganDutyPlanVo getQqchSafeOrganDutyPlanList(BigDecimal version);

    void batchSave(QqchSafeOrganDutyPlanVo qqchSafeOrganDutyPlanVo);
}
