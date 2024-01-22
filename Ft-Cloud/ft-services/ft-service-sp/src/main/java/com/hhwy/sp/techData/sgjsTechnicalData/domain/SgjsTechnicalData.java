package com.hhwy.sp.techData.sgjsTechnicalData.domain;

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
 * @author cjh
 * @date 2024-01-22 09:26:47
 * @remark sgjs_technical_data
 */
@Data
public class SgjsTechnicalData extends TreeNode<SgjsTechnicalData> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：技术资料目录id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "技术资料目录id")
    private Long dataCatalogId;
    /**
     * 字段描述：资料名称
     */
    @JsonProperty
    @Excel(name = "资料名称")
    private String dataName;
    /**
     * 字段描述：资料描述
     */
    @JsonProperty
    @Excel(name = "资料描述")
    private String dataDescription;
    /**
     * 字段描述：资料形成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "资料形成日期", dateFormat = "yyyy-MM-dd")
    private Date dataAddDate;
    /**
     * 字段描述：提交单位
     */
    @JsonProperty
    @Excel(name = "提交单位")
    private String submitOrgan;
    /**
     * 字段描述：提交人
     */
    @JsonProperty
    @Excel(name = "提交人")
    private String submitUser;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
//    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
//    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
//    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
//    @Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
//    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
//    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
//    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
//    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
//    @Excel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
//    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
//    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
//    @Excel(name = "预留字段5")
    private String ptVar5;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
//    @Excel(name = "流程状态（5已完成）")
    private String taskStatus;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
//    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
//    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：父ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "父ID")
    private Long pid;
    /**
     * 字段描述：排序号
     */
    @JsonProperty
//    @Excel(name = "排序号")
    private Integer sort;

    private String isAdd;

}
