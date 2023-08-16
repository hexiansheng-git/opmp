package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
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
    @Excel(name = "清单编码")
    private String inventoryCode;
    /**
     * 字段描述：清单名称
     */
    @JsonProperty
    @Excel(name = "清单名称")
    private String inventoryName;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String units;
    /**
     * 字段描述：清单数量
     */
    @JsonProperty
    @Excel(name = "清单数量")
    private Integer inventoryCount;
    /**
     * 字段描述：合同单价
     */
    @JsonProperty
    @Excel(name = "合同单价")
    private BigDecimal contractUnivalence;
    /**
     * 字段描述：合同金额
     */
    @JsonProperty
    @Excel(name = "合同金额")
    private BigDecimal contractAmount;
    /**
     * 字段描述：图纸复核数量
     */
    @JsonProperty
    @Excel(name = "图纸复核数量")
    private Integer blueprintReviewCount;
    /**
     * 字段描述：数量差值（复核数量-清单数量）
     */
    @JsonProperty
    @Excel(name = "数量差值")
    private Integer quantityDifferentials;
    /**
     * 字段描述：预估单价
     */
    @JsonProperty
    @Excel(name = "预估单价")
    private BigDecimal forecastUnivalence;
    /**
     * 字段描述：预估金额
     */
    @JsonProperty
    @Excel(name = "预估金额")
    private BigDecimal forecastAmount;
    /**
     * 字段描述：总价差值
     */
    @JsonProperty
    @Excel(name = "总价差值")
    private BigDecimal totalPriceDifference;
}
