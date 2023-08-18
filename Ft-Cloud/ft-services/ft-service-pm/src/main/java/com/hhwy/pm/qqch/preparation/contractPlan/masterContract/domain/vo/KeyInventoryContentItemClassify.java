package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-08-03 13:34:18
 * @remark qqch_key_inventory_content
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyInventoryContentItemClassify {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：清单编码
     */
    @JsonProperty
    private String inventoryCode;
    /**
     * 字段描述：清单描述 / 漏项描述
     */
    @JsonProperty
    private String inventoryName;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    private String units;
    /**
     * 字段描述：清单数量
     */
    @JsonProperty
    private Integer inventoryCount;
    /**
     * 字段描述：原合同单价 / 清单单价
     */
    @JsonProperty
    private BigDecimal contractUnivalence;
    /**
     * 字段描述：合同金额
     */
    @JsonProperty
    private BigDecimal contractAmount;
    /**
     * 字段描述：图纸复核数量 / 预估数量
     */
    @JsonProperty
    private Integer blueprintReviewCount;
    /**
     * 字段描述：数量差值（复核数量-清单数量）
     */
    @JsonProperty
    private Integer quantityDifference;
    /**
     * 字段描述：预估单价 / 复核单价
     */
    @JsonProperty
    private BigDecimal forecastUnivalence;
    /**
     * 字段描述：预估金额
     */
    @JsonProperty
    private BigDecimal forecastAmount;
    /**
     * 字段描述：单价差值
     */
    @JsonProperty
    private BigDecimal univalenceDifference;
    /**
     * 字段描述：总价差值
     */
    @JsonProperty
    private BigDecimal totalPriceDifference;
}
