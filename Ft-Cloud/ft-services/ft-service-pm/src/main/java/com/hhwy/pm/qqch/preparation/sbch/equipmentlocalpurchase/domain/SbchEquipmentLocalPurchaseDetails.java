package com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备属地化详情对象 sbch_equipment_purchase_details
 *
 * @author hwj
 * @date 2022-11-23
 */
@Data
public class SbchEquipmentLocalPurchaseDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 设备申购管理主表 sbch_equipment_purchase */
//    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 类别编码 */
//    @NotBlank(message = "设备类别分类不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String categoryCode;
    /*设备分类名称*/
    @Excel(name = "设备分类")
    private String categoryName;

    /** 材料编码 */
//    @NotBlank(message = "设备编码不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String materialCode;

    /** 材料名称 */
    @Excel(name = "设备名称")
//    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
//    @NotBlank(message = "规格型号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialSpec;

    /** 建议品牌id */
    private String sbAdviceBrandId;

    /** 建议品牌 */
    @Excel(name = "建议品牌")
    private String sbAdviceBrand;

    /** 设备来源 */
//    @NotBlank(message = "设备来源分类不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String sbPurchaseSource;

    /*设备来源str*/
    @Excel(name = "设备来源")
    private String sbPurchaseSourceStr;

    /** 功率 */
    @Excel(name = "功率")
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "功率不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long sbPower;

    /** 单位编码 */
    private String unitCode;

    /** 单位 */
    @Excel(name = "单位")
//    @NotBlank(message = "单位不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String unit;

    /** 数量 */
    @Excel(name = "数量")
//    @NotNull(message = "数量不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Integer sbNum;

    /** 预估单价 */
    @Excel(name = "预估单价(美元)")
//    @NotNull(message = "预估单价不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private BigDecimal sbForecastPrice;

    /** 预估总价 */
    @Excel(name = "预估总价(美元)")
//    @NotNull(message = "预估总价不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private BigDecimal sbForecastTotalPrice;

    /** 计划进场日期 */
    @Excel(name = "计划进场日期", width = 30, dateFormat = "yyyy-MM-dd")
//    @NotNull(message = "计划进场日期不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sbPlanEnterDate;

    /** 备注/描述 */
    @Excel(name = "备注")
    private String remark;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
    private String createUserName;

    /** 数据修改者id */
    private String updateUser;

    /** 数据修改者名称 */
    private String updateUserName;

    /** 数据删除者 */
    private String delUser;

    /** 数据删除系统时间 */
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** 预留字段3 */
    private String ptVar3;

    /** 预留字段4 */
    private String ptVar4;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 项目id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    private String[] ids;
}