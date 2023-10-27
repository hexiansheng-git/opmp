package com.hhwy.pm.qqch.preparation.sbch.plan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 设备总部计划总需用详情对象 sbch_total_demand_plan_detail
 * 
 * @author zq
 * @date 2022-11-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SbchTotalDemandPlanDetailExportVo {
    private static final long serialVersionUID = 1L;

    /**
     * 设备分类
     */
    @FtExcel(name = "设备分类")
    private String materialType;

    /**
     * 设备编码
     */
//    @FtExcel(name = "设备编码")
//    @NotBlank(message = "设备编码不能为空", groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialCode;

    /**
     * 设备名称
     */
    @FtExcel(name = "设备名称")
    private String materialName;

    /**
     * 规格型号
     */
    @FtExcel(name = "规格型号")
    private String materialSpec;

    /**
     * 功率（KW）
     */
    @FtExcel(name = "功率KW")
    private BigDecimal materialPower;

    /**
     * 品牌ids
     */
//    @FtExcel(name = "品牌ids")
    private String brandIds;

    /**
     * 建议品牌
     */
    @FtExcel(name = "建议品牌")
    private String brandNames;

    /**
     * 单位
     */
    @FtExcel(name = "单位")
    private String materialUnit;

    /**
     * 总需求量
     */
    @FtExcel(name = "总需求量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long totalNum;

    /**
     * 内部调拨数量
     */
    @FtExcel(name = "内部调拨数量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long allocateNum;

    /**
     * 国内采购
     */
    @FtExcel(name = "国内采购")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long countryBuyNum;

    /**
     * 当地采购数量
     */
    @FtExcel(name = "当地采购数量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long localBuyNum;

    /**
     * 当地租赁数量
     */
    @FtExcel(name = "当地租赁数量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long localLeaseNum;

    /**
     * 协作单位自带
     */
    @FtExcel(name = "协作单位自带")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long companySelfNum;

    /**
     * 计划进场时间
     */
    @FtExcel(name = "计划进场时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "计划进场时间不能为空", groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Date planEntryTime;

    /**
     * 计划退场时间
     */
    @FtExcel(name = "计划退场时间", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "计划退场时间不能为空", groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planExitTime;

    /**
     * 是否特种设备（0：否，1：是）
     */
    @FtExcel(name = "是否特种设备", readConverterExp = "0=：否，1：是")
    @NotBlank(message = "是否特种设备不能为空", groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String isSpecial;

}


