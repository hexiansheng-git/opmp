package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 特种设备过程管控策划详情对象 sbch_equipment_special_control_plan_details
 * 
 * @author hwj
 * @date 2022-12-07
 */
@Data
public class SbchEquipmentSpecialControlPlanDetails extends CommonBaseEntity {
    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 设备申购管理主表 sbch_equipment_purchase */
    @Excel(name = "设备申购管理主表 sbch_equipment_purchase")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 管控项 */
    @Excel(name = "管控项")
    private String controlItem;

    /** 执行人 */
    @Excel(name = "执行人")
    private String performName;

    /** 主责部门 */
    @Excel(name = "主责部门")
    private String responsibleDepartment;

    /** 协作部门 */
    @Excel(name = "协作部门")
    private String cooperationDepartment;

    /** 注意事项 */
    @Excel(name = "注意事项")
    private String precautions;

    /** 使用表格模版 */
    @Excel(name = "使用表格模版")
    private String tableTemplate;

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
