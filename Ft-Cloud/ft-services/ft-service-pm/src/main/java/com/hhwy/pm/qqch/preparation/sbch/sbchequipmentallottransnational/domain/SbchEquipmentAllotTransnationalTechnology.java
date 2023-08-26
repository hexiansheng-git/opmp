package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨国别设备调拨详情-技术评估对象 sbch_equipment_allot_transnational_technology
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Data
public class SbchEquipmentAllotTransnationalTechnology extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 跨国别方案主表 sbch_equipment_allot_transnational_details */
    @Excel(name = "跨国别方案主表 sbch_equipment_allot_transnational_details")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 管理编号 */
    @Excel(name = "管理编号")
//    @NotBlank(message = "管理编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String manageCode;

    /** 设备编码 */
    @Excel(name = "设备编码")
//    @NotBlank(message = "设备编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
//    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
//    @NotBlank(message = "规格型号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialSpec;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
//    @NotBlank(message = "生产厂家不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String sbProductFactory;

    /** 出厂日期 */
    @Excel(name = "出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
//    @NotNull(message = "出厂日期不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date sbLeaveDate;

    /** 原值（美元） */
    @Excel(name = "原值", readConverterExp = "美=元")
//    @NotNull(message = "原值不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private BigDecimal sbOriginalCost;

    /** 净值（美元） */
    @Excel(name = "净值", readConverterExp = "美=元")
//    @NotNull(message = "净值不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private BigDecimal sbOnlyCost;

    /** 设备照片 */
    @Excel(name = "设备照片")
    private String sbPicture;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 可调出日期 */
    @Excel(name = "可调出日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sbExportDate;

    /** 责任部门 */
    @Excel(name = "责任部门")
    private String sbDutyDepartment;

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

    /** 使用年限（年） */
    @Excel(name = "使用年限", readConverterExp = "年=")
//    @NotBlank(message = "使用年限不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String useYears;

    /** 行驶里程KM */
    @Excel(name = "行驶里程KM")
//    @NotNull(message = "行驶里程不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private BigDecimal driveMileage;

    /** 工作小时（h） */
    @Excel(name = "工作小时", readConverterExp = "h=")
//    @NotNull(message = "工作小时不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private BigDecimal workHours;

    /** 发动机状况 */
    @Excel(name = "发动机状况")
//    @NotBlank(message = "发动机状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String engineStatus;

    /** 液压和传动系统状况 */
    @Excel(name = "液压和传动系统状况")
//    @NotBlank(message = "液压和传动系统状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String hydraulicStatus;

    /** 变速箱状况 */
    @Excel(name = "变速箱状况")
//    @NotBlank(message = "变速箱状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String speedStatus;

    /** 工作装置状况 */
    @Excel(name = "工作装置状况")
//    @NotBlank(message = "工作装置状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String workStatus;

    /** 地盘行走系统状况 */
    @Excel(name = "地盘行走系统状况")
//    @NotBlank(message = "地盘行走系统状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String chassisStatus;

    /** 电器仪表状况 */
    @Excel(name = "电器仪表状况")
//    @NotBlank(message = "电器仪表状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String instrumentStatus;

    /** 外观状况 */
    @Excel(name = "外观状况")
//    @NotBlank(message = "外观状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String appearanceStatus;

    /** 整体综合状况 */
    @Excel(name = "整体综合状况")
//    @NotBlank(message = "整体综合状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String wholeStatus;

    /** 是否特种设备 0否 1是 */
    @Excel(name = "是否特种设备 0否 1是")
//    @NotBlank(message = "是否特种设备不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String isSpecial;
}
