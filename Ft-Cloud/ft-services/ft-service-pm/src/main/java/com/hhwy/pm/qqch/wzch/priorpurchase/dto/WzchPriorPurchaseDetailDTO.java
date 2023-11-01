package com.hhwy.pm.qqch.wzch.priorpurchase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchaseDetail;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mls
 */
@Data
@ToString
public class WzchPriorPurchaseDetailDTO extends WzchPriorPurchaseDetail {

    /**
     * 物资编码
     */
    @Excel(name = "物资编码")
    private String materialCode;

    /**
     * 物资名称
     */
    @Excel(name = "物资名称")
    private String materialName;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String materialSpec;

    /**
     * 技术参数
     */
    @Excel(name = "技术参数")
    private String materialTechParam;

    /**
     * 品牌
     */
    @Excel(name = "品牌")
    private String brand;

    /**
     * 执行标准
     */
    @Excel(name = "执行标准")
    private String materialStandard;
    private String materialStandardName;
    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;


    /**
     * 总需用量
     */
    @Excel(name = "总需用量")
    private BigDecimal totalDemandAmount;

    /**
     * 自采需用量
     */
    @Excel(name = "自采需用量")
    private BigDecimal selfDemandAmount;

    /**
     * 物资类型
     */
    @Excel(name = "物资类型")
    private String categoryName;
    private String categoryNameName;

    /**
     * 优先到场数量
     */
    @Excel(name = "优先到场数量")
    private BigDecimal priorApproachAmount;

    /**
     * 最早需用日期
     */
    @JsonFormat( pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "最早需用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date earliestReqTime;

    /**
     * 要求到场日期
     */
    @Excel(name = "要求到场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date presentTime;

    /**
     * 来源
     */
    private String source;

    @Excel(name = "来源")
    private String sourceName;

    /**
     * 币种
     */
    @Excel(name = "币种")
    private String currency;

    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /**
     * 平均采购价
     */
    @Excel(name = "平均采购价")
    private BigDecimal avgPurchasePrice;

    /**
     * 平均发运价
     */
    @Excel(name = "平均发运价")
    private BigDecimal avgDespatchPrice;

    /**
     * 平均清关价
     */
    @Excel(name = "平均清关价")
    private BigDecimal avgCustClearPrice;

    /**
     * 平均当地运输价
     */
    @Excel(name = "平均当地运输价")
    private BigDecimal avgLocalTransportPrice;

    /**
     * 平均落地价
     */
    @Excel(name = "平均落地价")
    private BigDecimal avgLandingPrice;

    /**
     * 总价
     */
    @Excel(name = "总价")
    private BigDecimal totalPrice;


}
