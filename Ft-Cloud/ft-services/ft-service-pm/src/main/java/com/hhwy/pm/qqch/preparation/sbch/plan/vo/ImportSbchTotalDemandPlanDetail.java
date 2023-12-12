package com.hhwy.pm.qqch.preparation.sbch.plan.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 导入设备总部计划总需用详情对象 sbch_total_demand_plan_detail
 * 
 * @author zq
 * @date 2022-11-23
 */
@Data
public class ImportSbchTotalDemandPlanDetail {
    @Excel(name = "设备分类编码")
    @NotBlank(message = "设备分类编码不可为空")
    private String materialType;

    @Excel(name = "设备分类名称")
    private String ptVar1;

    @Excel(name = "设备名称")
    @NotBlank(message = "设备名称不可为空")
    private String materialName;

    @Excel(name = "设备编号")
    @NotBlank(message = "设备编号不可为空")
    private String materialCode;

    @Excel(name = "规格型号")
    private String materialSpec;

    @Excel(name = "功率（KW）")
    @NotNull(message = "'功率'只能输入数字",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @Digits(integer = 5, fraction=2, message = "功率格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @DecimalMin(value = "0.00", message = "功率格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Double materialPower;

    @Excel(name = "建议品牌")
    private String brandNames;

    @Excel(name = "单位")
    @NotBlank(message = "单位不可为空")
    private String materialUnit;

    @Excel(name = "内部调拨")
    @NotNull(message = "'内部调拨'只能输入数字",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @Digits(integer = 11, fraction=0, message = "内部调拨格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @DecimalMin(value = "0", message = "内部调拨格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Integer allocateNum;

    @Excel(name = "国内采购")
    @NotNull(message = "'国内采购'只能输入数字",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @Digits(integer = 11, fraction=0, message = "国内采购格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @DecimalMin(value = "0", message = "国内采购格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Integer countryBuyNum;

    @Excel(name = "当地采购")
    @NotNull(message = "'当地采购'只能输入数字",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @Digits(integer = 11, fraction=0, message = "当地采购格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @DecimalMin(value = "0", message = "当地采购格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Integer localBuyNum;

    @Excel(name = "当地租赁")
    @NotNull(message = "'当地租赁'只能输入数字",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @Digits(integer = 11, fraction=0, message = "当地租赁格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @DecimalMin(value = "0", message = "当地租赁格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Integer localLeaseNum;

    @Excel(name = "协作单位自带")
    @NotNull(message = "'协作单位自带'只能输入数字",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @Digits(integer = 11, fraction=0, message = "协作单位自带格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @DecimalMin(value = "0", message = "协作单位自带格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Integer companySelfNum;

    @Excel(name = "计划进场时间(yyyy-MM-dd)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planExtryTime;

    @Excel(name = "计划退场时间(yyyy-MM-dd)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planExitTime;

    @Excel(name = "是否特种设备")
    private String isSpecial;
}
