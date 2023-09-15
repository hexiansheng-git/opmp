package com.hhwy.pm.qqch.wzch.localpuchasesupply.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupplyDetail;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购供应策划材料视角物资详情对象 wzch_purchase_supply_detail
 *
 * @author mls
 * @date 2022-11-17
 */
@ToString
@Data
public class WzchLocalPurchaseSupplyDetailDTO extends WzchLocalPurchaseSupplyDetail {
    private static final long serialVersionUID = 1L;

    private String planPurchaseDateGroup;

    /**
     * 序号
     */
    @FtExcel(name = "序号")
    private String serialNum;

    /**
     * 批次信息
     */
    private List<WzchLocalPurchaseSupplyDetailDTO> children;

    /**
     * 物资编码
     */
    @FtExcel(name = "物资编码")
    private String materialCode;
    /**
     * 物资名称
     */
    @FtExcel(name = "物资名称")
    private String materialName;

    /**
     * 规格型号
     */
    @FtExcel(name = "规格型号")
    private String materialSpec;

    /**
     * 物资分类
     */
    @NotBlank(message = "物资分类不能为空", groups = {ValidationGroups.Other.class})
    @FtExcel(name = "物资分类",dictType = "total_demand_category_name")
    private String categoryName;
    private String categoryNameName;


    /**
     * 技术参数
     */
    @FtExcel(name = "技术参数")
    private String materialTechParam;

    /**
     * 品牌
     */
    @FtExcel(name = "品牌")
    private String brand;

    /**
     * 执行标准
     */
    @FtExcel(name = "执行标准",dictType = "material_standard")
    private String materialStandard;
    private String materialStandardName;

    /**
     * 单位
     */
    @FtExcel(name = "单位")
    private String unit;

    /**
     * 总需用量
     */
    @FtExcel(name = "总需用量")
    private BigDecimal totalDemandAmount;

    /**
     * 自采需用量
     */
    @FtExcel(name = "自采需用量")
    private BigDecimal selfDemandAmount;

    /**
     * 非优先进场数量
     */
    @FtExcel(name = "非优先进场数量")
    private BigDecimal nonPriorApproachAmount;

    /**
     * 批次数量
     */
    @FtExcel(name = "批次")
    @JsonSerialize(using = ToStringSerializer.class)
    private String batch;

    /**
     * 拟采购数量
     */
    @FtExcel(name = "拟采购数量")
    private BigDecimal planPurchaseNum;

    /**
     * 拟采购日期
     */
    @FtExcel(name = "拟采购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planPurchaseDate;

    /**
     * 来源
     */
    @FtExcel(name = "来源",dictType = "wzch_purchase_source")
    private String source;

    private String sourceName;

    private List<Map<String,String>> sourceMap;

    /**
     * 现场进度计划使用时间
     */
    @FtExcel(name = "现场进度计划使用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planTime;

    /**
     * 币种
     */
    private String currency;
    @FtExcel(name = "币种")
    private String currencyName;

    /**
     * 单价
     */
    @FtExcel(name = "单价")
    private BigDecimal unitPrice;

    /**
     * 平均采购价
     */
    @FtExcel(name = "平均采购价")
    private BigDecimal avgPurchasePrice;

    /**
     * 平均发运价
     */
    @FtExcel(name = "平均发运价")
    private BigDecimal avgDespatchPrice;

    /**
     * 平均清关价
     */
    @FtExcel(name = "平均清关价")
    private BigDecimal avgCustClearPrice;

    /**
     * 平均当地运输价
     */
    @FtExcel(name = "平均当地运输价")
    private BigDecimal avgLocalTransportPrice;

    /**
     * 平均落地价
     */
    @FtExcel(name = "平均落地价")
    private BigDecimal avgLandingPrice;

    /**
     * 总价
     */
    @FtExcel(name = "总价")
    private BigDecimal totalPrice;
    /**
     * 国内采购数量
     */
    private BigDecimal internalNum;
    /**
     * 第三国采购数量
     */
    private BigDecimal otherStateNum;
    /**
     * 当地采购数量
     */
    private BigDecimal localNum;
    /**
     * 采购数量
     */
    private BigDecimal purchaseAmount;


    /**
     * 拟采购开始日期
     */
    @NotNull(message = "拟采购开始日期不能为空", groups = {ValidationGroups.Other.class})
    private Date planPurchaseDateStart;

    /**
     * 拟采购结束日期
     */
    @NotNull(message = "拟采购结束日期不能为空", groups = {ValidationGroups.Other.class})
    private Date planPurchaseDateEnd;

}
