package com.hhwy.pm.qqch.wzch.source.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceApproachYearCount;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class WzchSourceDetailResponse {


    /** 物资编码 */
    @ExcelProperty( "物资编码")
    private String materialCode;


    /** 物资设备名称 */
    @ExcelProperty( "物资名称")
    private String materialName;

    /** 规格型号 */
    @ExcelProperty( "规格型号")
    private String materialSpec;

    /** 技术参数 */
    @ExcelProperty( "技术参数")
    private String materialTechParam;

    /** 执行标准 */
    @ExcelProperty( "执行标准")
    private String materialStandard;

    /** 单位 */
    @ExcelProperty( "单位")
    private String unit;

    /** 总需用量 */
    @ExcelProperty( "总需用量")
    private BigDecimal totalDemandAmount;


    /** 自采需用量 */
    @ExcelProperty( "自采需用量")
    private BigDecimal selfDemandAmount;

    /** 类型(t_material_category的category_name) */
    @Excel(name = "类型(t_material_category的category_name)")
    private String categoryName;

    private String demandVersion;

    private String demandNewVersion;

    @JsonFormat(pattern = "YYYY-MM-dd", timezone = "GMT+8")
    private Date demandValidDate;

    private List<String> yearList;

    private List<WzchSourceApproachYearCount> wzchSourceApproachYearCountList;

}
