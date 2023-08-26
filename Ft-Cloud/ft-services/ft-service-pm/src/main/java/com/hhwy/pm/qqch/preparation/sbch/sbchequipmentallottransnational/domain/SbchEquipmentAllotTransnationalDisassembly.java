package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 跨国别设备调拨详情-拆卸吊装方案对象 sbch_equipment_allot_transnational_disassembly
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Data
public class SbchEquipmentAllotTransnationalDisassembly extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 跨国别方案主表 sbch_equipment_allot_transnational_details */
    @Excel(name = "跨国别方案主表 sbch_equipment_allot_transnational_details")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 设备编码 */
    @Excel(name = "设备编码")
//    @NotBlank(message = "设备编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
//    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialName;

    /** 类别编码 */
    @Excel(name = "分类编码")
//    @NotBlank(message = "分类编码不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String categoryCode;

    /** 设备分类 */
    @Excel(name = "设备分类")
//    @NotBlank(message = "设备分类不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String categoryName;

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

    /** 设备照片 */
    @Excel(name = "设备照片")
    private String sbPicture;

    /** 是否特种设备 0否 1是 */
    @Excel(name = "是否特种设备 0否 1是")
//    @NotBlank(message = "是否特种设备不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String isSpecial;

    /** 吊装拆卸方案 */
    @Excel(name = "吊装拆卸方案")
    @NotBlank(message = "吊装拆卸方案不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String disassemblyPlan;

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
