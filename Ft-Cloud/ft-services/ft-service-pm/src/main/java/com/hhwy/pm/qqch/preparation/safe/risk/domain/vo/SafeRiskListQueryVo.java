package com.hhwy.pm.qqch.preparation.safe.risk.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zq
 * @date 2023-08-11 13:41:25
 * @remark qqch_safe_risk_list
 */
@Data
public class SafeRiskListQueryVo {
    private String type;

    private String wbsId;

    private BigDecimal version;
}
