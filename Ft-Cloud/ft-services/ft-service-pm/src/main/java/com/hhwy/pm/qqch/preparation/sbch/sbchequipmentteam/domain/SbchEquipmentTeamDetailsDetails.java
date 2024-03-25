package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain;

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
import java.util.Date;

/**
 * 协作单位设备详情对象 sbch_equipment_team_details_details
 * 
 * @author hwj
 * @date 2022-11-30
 */
@Data
public class SbchEquipmentTeamDetailsDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 协作单位详情主表id sbch_equipment_team_details */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 类别编码 */
    @Excel(name = "设备分类编码")
//    @NotBlank(message = "设备类别分类不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String categoryCode;

    /*设备分类名称*/
    private String categoryName;

    /** 设备名称 */
    @Excel(name = "设备名称")
    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String materialName;

    /** 设备编码 */
    @Excel(name = "设备编码")
    @NotBlank(message = "设备编码不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String materialCode;


    /** 规格型号 */
    @Excel(name = "规格型号")
//    @NotBlank(message = "规格型号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String materialSpec;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    private String productionFactory;

    /** 需用数量 */
    @Excel(name = "需用数量")
//    @NotBlank(message = "需用数量不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String needNum;

    /** 设备要求 */
    @Excel(name = "设备要求")
    private String sbRequest;

    /** 计划进场日期 */
    @Excel(name = "计划进场日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "计划进场日期不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Date sbPlanEnterDate;

    /** 计划退场日期 */
    @Excel(name = "计划退场日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "计划退场日期不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Date sbPlanOutDate;

    /** 是否特种设备（0：否，1：是） */
    @Excel(name = "是否特种设备", readConverterExp = "0=否,1=是")
//    @NotBlank(message = "是否特种设备不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String isSpecial;

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

    /** 所属区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    private String region;
}
