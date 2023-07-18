package com.hhwy.pm.qqch.qqchWorkPlan.domain;

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
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.tree.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hwj
 * @date 2023-07-17 10:58:21
 * @remark qqch_work_plan_detail
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QqchWorkPlanDetail extends TreeNode<QqchWorkPlanDetail> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父类id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父类id")
    private Long pid;
    /**
     * 字段描述：主数据id  （qqch_work_plan）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主数据id  （qqch_work_plan）")
    private Long mainId;
    /**
     * 字段描述：显示顺序
     */
    @JsonProperty
    @Excel(name = "显示顺序")
    private Integer orderNum;
    /**
     * 字段描述：工作说明
     */
    @JsonProperty
    @Excel(name = "工作说明")
    private String workExplain;
    /**
     * 字段描述：是否第一阶段编制内容
     */
    @JsonProperty
    @Excel(name = "是否第一阶段编制内容")
    private String isFirst;
    /**
     * 字段描述：第一阶段编制人
     */
    @JsonProperty
    @Excel(name = "第一阶段编制人")
    private String editorFirst;
    /**
     * 字段描述：第一阶段计划完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "第一阶段计划完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTimeFirst;
    /**
     * 字段描述：是否第二阶段编制内容
     */
    @JsonProperty
    @Excel(name = "是否第二阶段编制内容")
    private String isSecond;
    /**
     * 字段描述：第二阶段编制人
     */
    @JsonProperty
    @Excel(name = "第二阶段编制人")
    private String editorSecond;
    /**
     * 字段描述：第二阶段计划完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "第二阶段计划完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTimeSecond;
    /**
     * 字段描述：是否第三阶段编制内容
     */
    @JsonProperty
    @Excel(name = "是否第三阶段编制内容")
    private String isThird;
    /**
     * 字段描述：第三阶段编制人
     */
    @JsonProperty
    @Excel(name = "第三阶段编制人")
    private String editorThird;
    /**
     * 字段描述：第三阶段计划完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "第三阶段计划完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTimeThird;
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
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
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
     * 字段描述：第一阶段编制人姓名
     */
    @JsonProperty
    @Excel(name = "第一阶段编制人姓名")
    private String editorFirstName;
    /**
     * 字段描述：第二阶段编制人姓名
     */
    @JsonProperty
    @Excel(name = "第二阶段编制人姓名")
    private String editorSecondName;
    /**
     * 字段描述：第三阶段编制人姓名
     */
    @JsonProperty
    @Excel(name = "第三阶段编制人姓名")
    private String editorThirdName;
}
