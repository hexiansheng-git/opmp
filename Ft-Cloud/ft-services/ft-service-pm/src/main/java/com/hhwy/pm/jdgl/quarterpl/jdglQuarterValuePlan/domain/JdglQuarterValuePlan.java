package com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark jdgl_quarter_value_plan
 */
@Data
public class JdglQuarterValuePlan extends TreeNode<JdglQuarterValuePlan> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：季进度计划id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "季进度计划id")
    private Long planId;
    /**
     * 字段描述：清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "清单id")
    private Long inventoryId;
    /**
     * 字段描述：清单编号
     */
    @JsonProperty
    @Excel(name = "清单编号")
    private String inventoryCode;
    /**
     * 字段描述：清单名称
     */
    @JsonProperty
    @Excel(name = "清单名称")
    private String inventoryName;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：设计工程量
     */
    @JsonProperty
    @Excel(name = "设计工程量")
    private BigDecimal designQuantity;
    /**
     * 字段描述：单价（合同币种）
     */
    @JsonProperty
    @Excel(name = "单价（合同币种）")
    private BigDecimal priceCu;
    /**
     * 字段描述：开累完成设计工程量
     */
    @JsonProperty
    @Excel(name = "开累完成设计工程量")
    private BigDecimal totalCompDesignQuantity;
    /**
     * 字段描述：剩余设计工程量
     */
    @JsonProperty
    @Excel(name = "剩余设计工程量")
    private BigDecimal remainDesignQuantity;
    /**
     * 字段描述：本季计划完成设计工程量
     */
    @JsonProperty
    @Excel(name = "本季计划完成设计工程量")
    private BigDecimal quarterPlanCompDesignQuantity;
    /**
     * 字段描述：本季计划产值（合同币种）
     */
    @JsonProperty
    @Excel(name = "本季计划产值（合同币种）")
    private BigDecimal quarterPlanValueCu;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @Excel(name = "预留字段5")
    private String ptVar5;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    @Excel(name = "流程状态（5已完成）")
    private String taskStatus;

}
