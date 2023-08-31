package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 特种设备管理详情对象 sbch_equipment_special_details
 * 
 * @author hwj
 * @date 2022-12-05
 */
@Data
public class SbchEquipmentSpecialDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 设备申购管理主表 sbch_equipment_purchase */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 材料编码 */
    @Excel(name = "设备编码")
    @NotBlank(message = "设备编码不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String materialCode;

    /** 材料名称 */
    @Excel(name = "设备名称")
    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
//    @NotBlank(message = "规格型号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialSpec;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    @NotBlank(message = "生产厂家不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String sbProductFactory;

    /** 生产日期 */
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "生产日期不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sbProductTime;

    /** 新度系数 */
    @Excel(name = "新度系数")
    @NotBlank(message = "新度系数不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String sbNewCoefficient;

    /** 整体综合状况 */
    @Excel(name = "整体综合状况")
    @NotBlank(message = "整体综合状况不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String wholeStatus;

    /** 设备来源 */
    @Excel(name = "设备来源")
    @NotBlank(message = "设备来源不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String sbPurchaseSource;

    /** 计划进场时间 */
    @Excel(name = "计划进场时间", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "计划进场时间不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planEntryTime;

    /** 计划退场时间 */
    @Excel(name = "计划退场时间", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "计划退场时间不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planExitTime;

    /** 操作人员 */
    @Excel(name = "操作人员")
    @NotBlank(message = "操作人员不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String operatorName;

    /** 操作人员证书 */
    @Excel(name = "操作人员证书")
    private String operatorCertificate;

    /** 第三方检测机构 */
    @Excel(name = "第三方检测机构")
    private String thirdOrganization;

    /** 数据创建者id */
    @Excel(name = "数据创建者id")
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "数据创建者名称")
    private String createUserName;

    /** 数据修改者id */
    @Excel(name = "数据修改者id")
    private String updateUser;

    /** 数据修改者名称 */
    @Excel(name = "数据修改者名称")
    private String updateUserName;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String ptVar3;

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private String ptVar4;

    /** 部门id */
    @Excel(name = "部门id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 项目id */
    @Excel(name = "项目id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 所属区域id */
    @Excel(name = "所属区域id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String region;
}
