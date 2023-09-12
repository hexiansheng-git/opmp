package com.hhwy.pm.qqch.wzch.demand.vo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class WzchTotalDemandValidVO {

    private String materialCode;
    private String materialName;
    private String materialSpec;
    /** 技术参数 */
    private String materialTechParam;

    /** 执行标准 */
    private String materialStandard;
    private String unit;
    private BigDecimal totalDemandAmount;
}
