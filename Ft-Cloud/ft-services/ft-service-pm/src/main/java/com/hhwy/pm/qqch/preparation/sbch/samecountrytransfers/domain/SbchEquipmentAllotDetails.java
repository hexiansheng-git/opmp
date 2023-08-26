package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 同国别设备详情对象 sbch_equipment_allot_details
 *
 * @author hwj
 * @date 2022-11-25
 */
@Data
public class SbchEquipmentAllotDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 同国别设备主表 sbch_equipment_allot */
    @Excel(name = "同国别设备主表 sbch_equipment_allot")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 管理编号 */
    @Excel(name = "管理编号")
//    @NotBlank(message = "管理编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String manageCode;

    /** 材料编码 */
    @Excel(name = "设备编码")
//    @NotBlank(message = "设备编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialCode;

    /** 材料名称 */
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

    /** 发动机号 */
    @Excel(name = "发动机号")
//    @NotBlank(message = "发动机号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String sbEngineNum;

    /** 底盘号 */
    @Excel(name = "底盘号")
//    @NotBlank(message = "底盘号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String sbChassisNum;

    /** 原值 */
    @Excel(name = "原值")
//    @NotNull(message = "原值不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private BigDecimal sbOriginalCost;

    /** 净值 */
    @Excel(name = "净值")
//    @NotNull(message = "净值不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private BigDecimal sbOnlyCost;

    /** 状态 1：闲置 2：在用 3：待修 */
    @Excel(name = "状态 1：闲置 2：在用 3：待修")
    @NotNull(message = "状态不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Integer sbStatus;

    /** 附件组id 照片 */
    @Excel(name = "附件组id 照片")
    private String fileGroupId;

    /** 项目名称 */
    @Excel(name = "可调出项目名称")
//    @NotBlank(message = "可调出项目名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String projectName;

    /** 可调出日期 */
    @Excel(name = "可调出日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
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
    @NotNull(message = "可调出项目id为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long projectId;

    /** 所属区域id */
    @Excel(name = "所属区域id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String region;

    /** 使用年限 */
//    @Excel(name = "使用年限")
//    @NotBlank(message = "使用年限不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String useYears;

    /** 行驶里程 */
//    @Excel(name = "行驶里程")
//    @NotNull(message = "行驶里程不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private BigDecimal driveMileage;

    /** 工作小时 */
//    @Excel(name = "工作小时")
//    @NotNull(message = "工作小时不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private BigDecimal workHours;

    /** 发动机状况 */
//    @Excel(name = "发动机状况")
//    @NotBlank(message = "发动机状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String engineStatus;

    /** 液压和传动系统状况 */
//    @Excel(name = "液压和传动系统状况")
//    @NotBlank(message = "液压和传动系统状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String hydraulicStatus;

    /** 变速箱状况 */
//    @Excel(name = "变速箱状况")
//    @NotBlank(message = "变速箱状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String speedStatus;

    /** 工作装置状况 */
//    @Excel(name = "工作装置状况")
//    @NotBlank(message = "工作装置状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String workStatus;

    /** 地盘行走系统状况 */
//    @Excel(name = "地盘行走系统状况")
//    @NotBlank(message = "地盘行走系统状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String chassisStatus;

    /** 电器仪表状况 */
//    @Excel(name = "电器仪表状况")
//    @NotBlank(message = "电器仪表状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String instrumentStatus;

    /** 外观状况 */
//    @Excel(name = "外观状况")
//    @NotBlank(message = "外观状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String appearanceStatus;

    /** 整体综合状况 */
//    @Excel(name = "整体综合状况")
//    @NotBlank(message = "整体综合状况不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String wholeStatus;

}
