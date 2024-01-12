package com.hhwy.pm.qqch.preparation.quality.emp.vo;


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
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-22 11:52:26
 * @remark qyzs_quality_special_inspection
 */
@Data
public class QyzsQualitySpecialInspection extends TreeNode<QyzsQualitySpecialInspection> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
//    @Excel(name = "附件组id")
    private String fileGroupId;
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
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "部门id")
    private Long deptId;
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
     * 字段描述：工程类型
     */
    @JsonProperty
//    @Excel(name = "工程类型")
    private String projectType;
    /**
     * 字段描述：父ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "父ID")
    private Long pid;
    /**
     * 字段描述：是否叶子节点
     */
    @JsonProperty
//    @Excel(name = "是否叶子节点")
    private String leaf;
    /**
     * 字段描述：排序号
     */
    @JsonProperty
//    @Excel(name = "排序号")
    private Integer sort;
    /**
     * 字段描述：标准wbs编码
     */
    @JsonProperty
    @Excel(name = "标准wbs编码")
    @FtExcel(name = "标准wbs编码")
    private String wbsCode;
    /**
     * 字段描述：标准wbs名称
     */
    @JsonProperty
    @Excel(name = "标准wbs名称")
    @FtExcel(name = "标准wbs名称")
    private String wbsName;
    /**
     * 字段描述：检查表编号
     */
    @JsonProperty
    @Excel(name = "检查表编号")
    @FtExcel(name = "检查表编号")
    private String inspectionNo;
    /**
     * 字段描述：上级检查表编号
     */
    @JsonProperty
//    @Excel(name = "上级检查表编号")
    @FtExcel(name = "上级检查表编号")
    private String inspectionPno;
    /**
     * 字段描述：检查表名称
     */
    @JsonProperty
    @Excel(name = "检查表名称")
    @FtExcel(name = "检查表名称")
    private String inspectionName;
    /**
     * 字段描述：检查项目
     */
    @JsonProperty
    @Excel(name = "检查项目")
    @FtExcel(name = "检查项目")
    private String inspectionProject;
    /**
     * 字段描述：规定值或允许偏差
     */
    @JsonProperty
    @Excel(name = "规定值或允许偏差")
    @FtExcel(name = "规定值或允许偏差")
    private String specifiedValue;
    /**
     * 字段描述：检查方法和频率
     */
    @JsonProperty
    @Excel(name = "检查方法和频率")
    @FtExcel(name = "检查方法和频率")
    private String inspectionMethodFrequency;
    /**
     * 字段描述：数据来源
     */
    @JsonProperty
    @Excel(name = "数据来源")
    @FtExcel(name = "数据来源")
    private String dataFrom;
    /**
     * 字段描述：编制/推送人
     */
    @JsonProperty
    @Excel(name = "编制/推送人")
    @FtExcel(name = "编制/推送人")
    private String editer;
    /**
     * 字段描述：编制/推送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "编制/推送时间", dateFormat = "yyyy-MM-dd")
    @FtExcel(name = "编制/推送时间", dateFormat = "yyyy-MM-dd")
    private Date editDate;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    @FtExcel(name = "备注")
    private String remark;

    @JsonProperty
    private String isAdd;

}
