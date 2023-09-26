package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain;

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
 * @author 陈锦豪
 * @date 2023-08-29 15:12:27
 * @remark jdgl_main_plan_item
 */
@Data
public class JdglMainPlanItem extends TreeNode<JdglMainPlanItem> {
    private static final long serialVersionUID = 1L;

    /**
     * 数据类型为作业
     */
    public final static String ITEMTYPE_ITEM = "item";
    /**
     * 数据类型为项目
     */
    public final static String ITEMTYPE_PROJ = "proj";
    /**
     * 数据类型为wbs
     */
    public final static String ITEMTYPE_WBS = "wbs";

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：总体计划id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "总体计划id")
    private Long mainPlanId;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父id")
    private Long pid;
    /**
     * 字段描述：排序号
     */
    @JsonProperty
    @Excel(name = "排序号")
    private Integer sort;
    /**
     * 字段描述：作业代码
     */
    @JsonProperty
    @Excel(name = "作业代码")
    private String itemCode;
    /**
     * 字段描述：作业名称
     */
    @JsonProperty
    @Excel(name = "作业名称")
    private String itemName;
    /**
     * 字段描述：原定工期
     */
    @JsonProperty
    @Excel(name = "原定工期")
    private Integer plannedDuration;
    /**
     * 字段描述：总浮时
     */
    @JsonProperty
    @Excel(name = "总浮时")
    private Integer totalFloat;
    /**
     * 字段描述：负责人Id
     */
    @JsonProperty
    @Excel(name = "负责人Id")
    private String executerId;
    /**
     * 字段描述：负责人
     */
    @JsonProperty
    @Excel(name = "负责人")
    private String executer;
    /**
     * 字段描述：开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "开始时间", dateFormat = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 字段描述：结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "结束时间", dateFormat = "yyyy-MM-dd")
    private Date finishDate;
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
     * 字段描述：是否关键线路
     */
    @JsonProperty
    @Excel(name = "是否关键线路")
    private String isCritical;
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
     * 字段描述：自由浮时
     */
    @JsonProperty
    @Excel(name = "自由浮时")
    private Integer freeFloat;

    /**
     * 字段描述：尚需工期
     */
    @JsonProperty
    @Excel(name = "尚需工期")
    private Integer remainingDuration;

    /**
        * 字段描述：实际开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际开始日期"    ,dateFormat = "yyyy-MM-dd"  )
    private Date actualStartDate;
    /**
     * 字段描述：实际结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际结束日期"    ,dateFormat = "yyyy-MM-dd"  )
    private Date actualFinishDate;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位"    )
    private String unit;
    /**
     * 字段描述：工程量
     */
    @JsonProperty
    @Excel(name = "工程量"    )
    private BigDecimal quantity;
    /**
     * 字段描述：项目编码
     */
    @JsonProperty
    @Excel(name = "项目编码"    )
    private String projectCode;
    /**
     * 字段描述：计划完成百分比
     */
    @JsonProperty
    @Excel(name = "计划完成百分比"    )
    private BigDecimal schedulePercentComplete;
    /**
     * 字段描述：期望完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "期望完成日期"    ,dateFormat = "yyyy-MM-dd"  )
    private Date expectedFinishDate;
    /**
     * 字段描述：滞后天数
     */
    @JsonProperty
    @Excel(name = "滞后天数"    )
    private Integer finishDateVariance;
    /**
     * 字段描述：滞后原因
     */
    @JsonProperty
    @Excel(name = "滞后原因"    )
    private String lagReason;
    /**
     * 字段描述：纠偏目标
     */
    @JsonProperty
    @Excel(name = "纠偏目标"    )
    private String correctionTarget;
    /**
     * 字段描述：具体措施
     */
    @JsonProperty
    @Excel(name = "具体措施"    )
    private String concreteMeasure;
    /**
     * 字段描述：纠偏完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "纠偏完成日期"    ,dateFormat = "yyyy-MM-dd"  )
    private Date correctionCompDate;
    /**
     * 字段描述：紧前作业代码
     */
    @JsonProperty
    @Excel(name = "紧前作业代码"    )
    private String predecessorActivityCode;
    /**
     * 字段描述：紧前作业名称
     */
    @JsonProperty
    @Excel(name = "紧前作业名称"    )
    private String predecessorActivityName;

    /**
     * wbs父id
     */
    @JsonProperty
    private String wbsPcode;

    /**
     * 数据类型(proj、wbs、item)
     */
    @JsonProperty
    private String itemType;

    /**
     * 祖籍编码
     */
    @JsonProperty
    private String ancestors;

    /**
     * p6的wbsid
     */
    @JsonProperty
    private String wbsObjectId;


    /**
     * p6的wbs父id
     */
    @JsonProperty
    private String wbsParentObjectId;


    /**
     * 字段描述：任务类型（'Task Dependent', 'Resource Dependent', 'Level of Effort', 'Start Milestone', 'Finish Milestone', or 'WBS Summary'）
     */
    @JsonProperty
    @Excel(name = "任务类型")
    private String taskType;
    /**
     * 字段描述：基线项目开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "基线项目开始", dateFormat = "yyyy-MM-dd")
    private Date baselineStartDate;
    /**
     * 字段描述：基线项目结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "基线项目结束", dateFormat = "yyyy-MM-dd")
    private Date baselineFinishDate;
    /**
     * 字段描述：尚需最早开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "尚需最早开始", dateFormat = "yyyy-MM-dd")
    private Date remainingEarlyStartDate;
    /**
     * 字段描述：尚需最早结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "尚需最早结束", dateFormat = "yyyy-MM-dd")
    private Date remainingEarlyFinishDate;

    /**
     * 字段描述：作业名称(适配甘特图字段)
     */
    @JsonProperty
    private String text;
    /**
     * 字段描述：开始时间(适配甘特图字段)
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "开始时间", dateFormat = "yyyy-MM-dd")
    private Date start_date;
//    /**
//     * 字段描述：结束时间(适配甘特图字段)
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @JsonProperty
//    @Excel(name = "结束时间", dateFormat = "yyyy-MM-dd")
//    private Date end_date;
    /**
     * 字段描述：进度(适配甘特图字段)
     */
    @JsonProperty
    private BigDecimal progress;
    /**
     * 字段描述：工期(适配甘特图字段)
     */
    @JsonProperty
    private BigDecimal duration;

    /**
     * 字段描述：作业标识(适配甘特图字段)
     */
    @JsonProperty
    private String type;

    /**
     * 字段描述：合并格式(适配甘特图字段):split，用于wbs层级
     */
    @JsonProperty
    private String render;
    /**
     * 字段描述：是否展开(适配甘特图字段)
     */
    @JsonProperty
    private boolean open;
    /**
     * 字段描述：是否汇总上级(适配甘特图字段)
     */
    @JsonProperty
    private boolean rollup;
    /**
     * 字段描述：父id(适配甘特图字段)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long parent;

    /**
     * 是否有孩子节点
     */
    @JsonProperty
    private int haveChildren;
}
