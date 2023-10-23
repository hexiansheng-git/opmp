package com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain;

import com.hhwy.common.core.web.domain.BaseEntity;

import java.util.Date;
import java.math.BigDecimal;

import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark jdgl_year_image_plan
 */
@Data
public class JdglYearImagePlan extends TreeNode<JdglYearImagePlan> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：年进度计划id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "年进度计划id")
    private Long yearPlanId;
    /**
     * 字段描述：总年进度计划形象id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "总年进度计划形象id")
    private Long totalPlanImageId;

    /**
     * 字段描述：总年进度计划形象父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "总年进度计划形象id")
    private Long totalPlanImagePid;

    /**
     * 字段描述：作业id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "作业id")
    private Long workId;
    /**
     * 字段描述：作业代码
     */
    @JsonProperty
    @Excel(name = "作业代码")
    private String workCode;
    /**
     * 字段描述：作业名称
     */
    @JsonProperty
    @Excel(name = "作业名称")
    private String workName;
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
     * 字段描述：开累完成工程量
     */
    @JsonProperty
    @Excel(name = "开累完成工程量")
    private BigDecimal totalCompQuantity;
    /**
     * 字段描述：剩余工程量
     */
    @JsonProperty
    @Excel(name = "剩余工程量")
    private BigDecimal remainQuantity;
    /**
     * 字段描述：计划完成工程量
     */
    @JsonProperty
    @Excel(name = "计划完成工程量")
    private BigDecimal planCompQuantity;

    /**
     * 字段描述：单价
     */
    @JsonProperty
    @Excel(name = "单价")
    private BigDecimal price;

    /**
     * 字段描述：计划完成产值
     */
    @JsonProperty
    @Excel(name = "计划完成产值")
    private BigDecimal planCompValue;
    /**
     * 字段描述：计划开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划开始日期", dateFormat = "yyyy-MM-dd")
    private Date planStartDate;
    /**
     * 字段描述：计划结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划结束日期", dateFormat = "yyyy-MM-dd")
    private Date planEndDate;

    @JsonProperty
    @Excel(name = "责任人")
    private String responsePerson;

    @JsonProperty
    @Excel(name = "责任人Id")
    private String responsePersonId;

    /**
     * 字段描述：wbsid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "wbsId")
    private Long wbsId;
    /**
     * 字段描述：wbs编码
     */
    @JsonProperty
    @Excel(name = "wbs编码")
    private String wbsCode;
    /**
     * 字段描述：wbs名称
     */
    @JsonProperty
    @Excel(name = "wbs名称")
    private String wbsName;

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
     * 字段描述：顺序号
     */
    @JsonProperty
    @Excel(name = "顺序号")
    private Integer sort;
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

    /**
     * 是否有孩子节点
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private int haveChildren;

}
