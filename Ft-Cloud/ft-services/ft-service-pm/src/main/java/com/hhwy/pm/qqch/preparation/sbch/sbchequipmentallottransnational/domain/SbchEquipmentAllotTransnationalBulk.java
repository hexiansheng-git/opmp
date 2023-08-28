package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨国别设备调拨详情-散货运输方案对象 sbch_equipment_allot_transnational_bulk
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Data
public class SbchEquipmentAllotTransnationalBulk extends CommonBaseEntity {
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

    /** 材料编码 */
    @Excel(name = "材料编码")
//    @NotBlank(message = "设备编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialCode;

    /** 材料名称 */
    @Excel(name = "材料名称")
//    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
//    @NotBlank(message = "规格型号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialSpec;

    /** 设备尺寸 */
    @Excel(name = "设备尺寸")
//    @NotBlank(message = "设备尺寸不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String sbSize;

    /** 设备重量 */
    @Excel(name = "设备重量")
//    @NotNull(message = "设备重量不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private BigDecimal sbWeight;

    /** 散货方量 */
    @Excel(name = "散货方量")
    @NotNull(message = "散货方量不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private BigDecimal bulkAmount;

    /** 起运地 */
    @Excel(name = "起运地")
    @NotBlank(message = "起运地不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String startPlace;

    /** 目的地 */
    @Excel(name = "目的地")
    @NotBlank(message = "目的地不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String endPlace;

    /** 运输时常（h） */
    @Excel(name = "运输时常", readConverterExp = "h=")
    @NotNull(message = "运输时长不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private BigDecimal transportHours;

    /** 运输商 */
    @Excel(name = "运输商")
    @NotBlank(message = "运输商不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String transportAgent;

    /** 注意事项 */
    @Excel(name = "注意事项")
//    @NotBlank(message = "注意事项不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String precautions;

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
