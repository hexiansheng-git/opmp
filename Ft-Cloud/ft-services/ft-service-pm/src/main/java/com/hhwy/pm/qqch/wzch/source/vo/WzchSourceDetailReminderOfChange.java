package com.hhwy.pm.qqch.wzch.source.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 *变更提醒响应对象
 * @author HCT
 */
@Data
public class WzchSourceDetailReminderOfChange {


    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 物资设备名称 */
    @Excel(name = "物资名称")
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String materialSpec;

    /** 物资编码 */
    @Excel(name = "物资编码")
    private String materialCode;

    /** 技术参数 */
    @Excel(name = "技术参数")
    private String materialTechParam;

    /** 执行标准 */
    @Excel(name = "执行标准")
    private String materialStandard;

    /** 总需用量 */
    @Excel(name = "总需用量")
    private BigDecimal totalDemandAmount;
    /** 原总需用量 */
    
    private BigDecimal backTotalDemandAmount;
    /** 自采需用量 */
    @Excel(name = "自采需用量")
    
    private BigDecimal selfDemandAmount;
    /** 原自采需用量 */
    
    private BigDecimal backSelfDemandAmount;

    /** 非自采量 */
    @Excel(name = "非自采量")
    
    private BigDecimal nonSelfAmount;
    /** 原非自采量 */
    
    private BigDecimal backNonSelfAmount;

    /** 类型(t_material_category的category_name) */
    @Excel(name = "类型(t_material_category的category_name)")
    private String categoryName;



}
