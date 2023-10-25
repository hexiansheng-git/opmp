package com.hhwy.pm.qqch.preparation.sbch.sblease.domain;

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
 * 设备租赁详情对象 sbch_equipment_lease_details
 * 
 * @author hwj
 * @date 2022-11-28
 */
@Data
public class SbchEquipmentLeaseDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private Long[] ids;

    /** 同国别设备主表 sbch_equipment_allot */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 供应商id */
    @Excel(name = "供应商id")

    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "供应商id不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long suplierId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    @NotBlank(message = "供应商名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String supplier;

    /** 材料名称 */
    @Excel(name = "材料名称")
    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    @NotBlank(message = "规格型号不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String materialSpec;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brandId;

    /** 品牌 */
    @Excel(name = "品牌")
    @NotBlank(message = "品牌不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String brand;

    /** 使用年限 */
    @Excel(name = "使用年限")
    private String userYears;

    /** 行驶里程(km) */
    @Excel(name = "行驶里程(km)")
    private BigDecimal driveMileage;

    /** 工作小时（h） */
    @Excel(name = "工作小时", readConverterExp = "h=")
    private BigDecimal workHours;

    /** 整体综合状况 */
    @Excel(name = "整体综合状况")
    private String wholeStatus;

    /** 附件组id 设备照片 */
    @Excel(name = "附件组id 设备照片")
    private String fileGroupId;

    /** 租赁单价(美元) */
    @Excel(name = "租赁单价(美元)")
    @NotNull(message = "租赁单价不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private BigDecimal leaseUnitprise;

    /** 租赁方式 */
    @Excel(name = "租赁方式")
    private String leaseType;

    /** 燃油承担方 */
    @Excel(name = "燃油承担方")
    @NotBlank(message = "燃油承担方不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String fuelBearer;

    /** 操作手工资(美元) */
    @Excel(name = "操作手工资(美元)")
    private BigDecimal operatorSalary;

    /** 进出场费用 */
    @Excel(name = "进出场费用")
    private BigDecimal entryExitCost;

    /** 是否特种设备 0否 1是 */
    @Excel(name = "是否特种设备 0否 1是")
    @NotBlank(message = "是否特种设备不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String isSpecialEqu;
    /** 备注/描述 */
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

    /** 总价 */
    @Excel(name = "总价")
    private BigDecimal amountPrice;
}
