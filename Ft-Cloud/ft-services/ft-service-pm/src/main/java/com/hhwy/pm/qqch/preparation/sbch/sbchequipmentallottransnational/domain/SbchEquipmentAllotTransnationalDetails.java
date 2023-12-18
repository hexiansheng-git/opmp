package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 跨国别设备调拨详情对象 sbch_equipment_allot_transnational_details
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Data
public class SbchEquipmentAllotTransnationalDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 跨国别设备主表 sbch_equipment_allot_transnational */
    @Excel(name = "跨国别设备主表 sbch_equipment_allot_transnational")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 跨国调拨方案名称 */
    @Excel(name = "跨国调拨方案名称")
    @NotBlank(message = "跨国调拨方案名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String schemeName;

    /** 可调出国id */
    @Excel(name = "可调出国id")
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "可调出国id不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long allotOutCountryId;

    /** 可调出国 */
    @Excel(name = "可调出国")
    @NotBlank(message = "可调出国名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String allotOutCountry;

    /** 预计运输时常 */
    @Excel(name = "预计运输时常")
    @NotBlank(message = "预计运输时常不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String transportTime;

    /** 调出费用（美元） */
    @Excel(name = "调出费用（美元）")
    @NotNull(message = "调出费用（美元）不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private BigDecimal allotOutCost;

    /** 调入费用（美元） */
    @Excel(name = "调入费用（美元）")
    @NotNull(message = "调入费用（美元）不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private BigDecimal allotInCost;

    /** 清关档案情况 */
    @Excel(name = "清关档案情况")
    @NotBlank(message = "清关档案情况不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String clearanceState;

    /** 调运策划 */
    @Excel(name = "调运策划")
    private String allotPlan;

    /** 可调出日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "可调出日期", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "可调出日期不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Date sbExportDate;

    /** 可调出项目 */
    @Excel(name = "可调出项目")
    @NotBlank(message = "可调出项目不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String projectName;

    /** 可调入国id */
    @Excel(name = "可调入国id")
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "可调入国id不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long allotInCountryId;

    /** 可调入出国 */
    @Excel(name = "可调入出国")
    @NotBlank(message = "可调入出国不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String allotInCountry;

    /** 计划进场日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划进场日期", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "计划进场日期不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Date sbPlanEnterDate;

    /** 运输方式 */
    @Excel(name = "运输方式")
    @NotBlank(message = "运输方式不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String transportType;

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

    /** 可调出项目id */
    @Excel(name = "可调出项目id")
    @JsonSerialize(using= ToStringSerializer.class)
//    @NotNull(message = "可调出项目id不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long projectId;

    /** 所属区域id */
    @Excel(name = "所属区域id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String region;
    /*技术评估*/
    private List<SbchEquipmentAllotTransnationalTechnology> detailsListTechnology = new ArrayList<>();
    /*拆卸吊装*/
    private List<SbchEquipmentAllotTransnationalDisassembly> detailsListDisassembly = new ArrayList<>();
    /*清关档案核查*/
    private List<SbchEquipmentAllotTransnationalClearance> detailsListClearance = new ArrayList<>();
    /*散货运输方案*/
    private List<SbchEquipmentAllotTransnationalBulk> detailsListBulk = new ArrayList<>();
    /*集装箱运输方案*/
    private List<SbchEquipmentAllotTransnationalContainer> detailsListContainer = new ArrayList<>();
    /*港口调查*/
    private List<SbchEquipmentAllotTransnationalPort> detailsListPort = new ArrayList<>();
    /*再出口调查*/
    private List<SbchEquipmentAllotTransnationalExit> detailsListExit = new ArrayList<>();
    /*进口调查*/
    private List<SbchEquipmentAllotTransnationalImport> detailsListImport = new ArrayList<>();
    /*费用估算*/
    private List<SbchEquipmentAllotTransnationalCost> detailsListCost = new ArrayList<>();
}
