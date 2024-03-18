package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zmh
 * @date 2023-12-08 16:19:52
 * @remark
 */
@Data
public class SgjsReportMeasureSubmit extends TreeNode<SgjsReportMeasureSubmit> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "父级id")
    private Long pid;
    /**
     * 字段描述：报告名称
     */
    @JsonProperty
    @Excel(name = "报告名称")
    private String reportName;
    /**
     * 字段描述：所属wbs编号
     */
    @JsonProperty
    //@Excel(name = "所属wbs编号")
    private String wbsCode;
    /**
     * 字段描述：所属wbs名称
     */
    @JsonProperty
    @Excel(name = "所属wbs名称")
    private String wbsName;
    /**
     * 字段描述：所属WBS的id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "所属WBS的id")
    private Long wbsId;
    /**
     * 字段描述：所属wbs祖级ID
     */
    @JsonProperty
    //@Excel(name = "所属wbs祖级ID")
    private String wbsAncestors;
    /**
     * 字段描述：计划提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty

    private Date planStartDate;

    @JsonProperty
    @Excel(name = "计划提交日期")
    private String planStartDateStr;

    /**
     * 字段描述：实际提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date realStartDate;

    @JsonProperty
    @Excel(name = "实际提交日期")
    private String realStartDateStr;

    @JsonProperty
    private String realEndDateStr;

    /*
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目编码
     */
    @JsonProperty
    @Excel(name = "项目编码")
    private String projectCode;
    /**
     * 字段描述：项目名称（中文）
     */
    @JsonProperty
    @Excel(name = "项目名称（中文）")
    private String projectName;
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
     * 字段描述：编制人
     */
    @JsonProperty
    @Excel(name = "编制人")
    private String aurhorizedPersonnel;
    /**
     * 字段描述：提交人
     */
    @JsonProperty
    @Excel(name = "提交人")
    private String submitter;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    //@Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    //@Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
    //@Excel(name = "数据来源 0新增1同步")
    private String dataSource;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    //@Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    //@Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    //@Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    //@Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    //@Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1   项目编码
     */
    @JsonProperty
    //@Excel(name = "预留字段1   项目编码")
    private String ptVar1;
    /**
     * 字段描述：预留字段2  leaf 是否是叶子节点 0否1是
     */
    @JsonProperty
    //@Excel(name = "预留字段2  leaf 是否是叶子节点 0否1是")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    //@Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    //@Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    //@Excel(name = "预留字段5")
    private String ptVar5;

    //导入查询
    private List<Long> ids;

    //新增标识
    private String isAdd;

    private List<Long> idList;

}
