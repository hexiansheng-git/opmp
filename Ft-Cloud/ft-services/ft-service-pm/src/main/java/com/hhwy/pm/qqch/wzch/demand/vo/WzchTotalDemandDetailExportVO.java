package com.hhwy.pm.qqch.wzch.demand.vo;

import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 物资总需详情导出对象
 * @author HCT
 */
@Data
public class WzchTotalDemandDetailExportVO implements Serializable {

    /** 物资编码 */
    @Excel(name = "物资编码")
    private String materialCode;

    /** 物资设备名称 */
    @Excel(name = "物资名称")
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String materialSpec;

    /** 技术参数 */
    @Excel(name = "技术参数")
    private String materialTechParam;

    /** 执行标准 */
    @Excel(name = "执行标准")
    private String materialStandard;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 总需用量 */
    @Excel(name = "总需用量")
    private BigDecimal totalDemandAmount;

    /** 自采需用量 */
    @Excel(name = "自采需用量")
    private BigDecimal selfDemandAmount;

    /** 非自采量 */
    @Excel(name = "非自采量")
    private BigDecimal nonSelfAmount;

    /** 类型(t_material_category的category_name) */
    @Excel(name = "类型")
    private String categoryName;

    /** 是否优先进场:0-否;1-是 */
    @Excel(name = "是否优先进场")
    private String firstEnterFlag;

    /**
     * 年季月数据
     */
    private Map<String,Object> data;

    /** 业主合同相关技术标准要求 */
    @Excel(name = "业主合同相关技术标准要求")
    private String contStandard;

    /** 资源调查 */
    @Excel(name = "资源调查")
    private String resourceSurvey;

    private List<String> years;
}
