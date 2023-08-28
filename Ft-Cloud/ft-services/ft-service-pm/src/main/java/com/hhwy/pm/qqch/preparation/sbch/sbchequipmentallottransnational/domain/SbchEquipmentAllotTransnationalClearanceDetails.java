package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 跨国别设备调拨详情-清关档案核查-清关档案核查明细对象 sbch_equipment_allot_transnational_clearance_details
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Data
public class SbchEquipmentAllotTransnationalClearanceDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 跨国别设备调拨详情-清关档案核查主表 sbch_equipment_allot_transnational_clearance */
    @Excel(name = "跨国别设备调拨详情-清关档案核查主表 sbch_equipment_allot_transnational_clearance")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 档案名称 */
    @Excel(name = "档案名称")
//    @NotBlank(message = "档案名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String archivesName;

    /** 存档方式 */
    @Excel(name = "存档方式")
//    @NotBlank(message = "存档方式不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String archiveType;

    /** 保管部门 */
    @Excel(name = "保管部门")
//    @NotBlank(message = "保管部门不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String custodyDept;

    /** 核查人 */
    @Excel(name = "核查人")
//    @NotBlank(message = "核查人不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String inspector;

    /** 核查结果 */
    @Excel(name = "核查结果")
//    @NotBlank(message = "核查结果不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String inspectResult;

    private String remark;

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
