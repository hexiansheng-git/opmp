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
 * 跨国别设备调拨详情-港口调查对象 sbch_equipment_allot_transnational_port
 * 
 * @author hwj
 * @date 2022-12-22
 */
@Data
public class SbchEquipmentAllotTransnationalPort extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 跨国别设备调拨详情主表 sbch_equipment_allot_transnational_details */
    @Excel(name = "跨国别设备调拨详情主表 sbch_equipment_allot_transnational_details")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 港口类型 0发运港 1目的港 */
    @Excel(name = "港口类型 0发运港 1目的港")
    @NotBlank(message = "港口类型不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String portType;

    /** 港口名称 */
    @Excel(name = "港口名称")
    @NotBlank(message = "港口名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String portName;

    /** 吞吐量（万TEU） */
    @Excel(name = "吞吐量", readConverterExp = "万=TEU")
    @NotNull(message = "吞吐量不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private BigDecimal throughput;

    /** 装卸能力 */
    @Excel(name = "装卸能力")
    @NotBlank(message = "装卸能力不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String handlingCapacity;

    /** 仓储条件 */
    @Excel(name = "仓储条件")
    @NotBlank(message = "仓储条件不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String storageConditions;

    /** 附件组id 附件 */
    @Excel(name = "附件组id 附件")
    private String fileGroupId;

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
