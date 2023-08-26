package com.hhwy.pm.qqch.preparation.sbch.sblease.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 设备租赁供应商详情对象 sbch_equipment_supplier_details
 * 
 * @author hwj
 * @date 2022-11-27
 */
@Data
public class SbchEquipmentSupplierDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 同国别设备主表 sbch_equipment_allot */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    @NotBlank(message = "供应商名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String supplier;

    /** 主要设备 */
    @Excel(name = "主要设备")
    private String mainEquipment;

    /** 国家id */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "国家id不可为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long countryId;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 公司注册地 */
    @Excel(name = "公司注册地点")
    private String companyAddr;

    /** 联系人 */
    @Excel(name = "联系人")
    private String linkman;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String mobile;
    /** 联系电话 */
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

    private List<String> ids;
}
