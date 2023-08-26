package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 跨国别设备调拨详情-进口调查对象 sbch_equipment_allot_transnational_import
 * 
 * @author hwj
 * @date 2022-12-22
 */
@Data
public class SbchEquipmentAllotTransnationalImport extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 跨国别方案主表 sbch_equipment_allot_transnational_details */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 进口项目 */
    @Excel(name = "进口项目")
    @NotBlank(message = "进口项目不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String importProject;

    /** 进口方式 */
    @Excel(name = "进口方式")
    @NotBlank(message = "进口方式不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String importType;

    /** 收货人信息 */
    @Excel(name = "收货人信息")
    @NotBlank(message = "收货人信息不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String consigneeInfo;

    /** 港口名称 */
    @Excel(name = "港口名称")
    @NotBlank(message = "港口名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String portName;

    /** 海关名称 */
    @Excel(name = "海关名称")
    @NotBlank(message = "海关名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String customsName;

    /** 海关概述 */
    @Excel(name = "海关概况")
    @NotBlank(message = "海关概述不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String customsIntro;

    /** 关税情况 */
    @Excel(name = "关税情况")
    @NotBlank(message = "关税情况不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String tariffCondition;

    /** 清关政策 */
    @Excel(name = "清关政策")
    @NotBlank(message = "清关政策不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String clearancePolicy;

    /** 清关流程 */
    @Excel(name = "清关流程及要点")
    @NotBlank(message = "清关流程及要点不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String clearanceFlow;

    /** 清关公司概述 */
    @Excel(name = "清关公司概况")
    @NotBlank(message = "清关公司概况不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String companyIntro;

    /** 清关特别注意事项 */
    @Excel(name = "清关特别注意事项")
//    @NotBlank(message = "清关特别注意事项不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String clearancePrecautions;

    /** 附件组id 照片 */
//    @NotBlank(message = "附件不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String fileGroupId;

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
