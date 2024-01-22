package com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain;

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
 * @author fsd
 * @date 2024-01-22 09:56:49
 * @remark sgjs_technical_file_blueprint
 */
@Data
public class SgjsTechnicalFileBlueprint extends TreeNode<SgjsTechnicalFileBlueprint> {
    private static final long serialVersionUID = 1L;

    @JsonProperty
    private String isAdd;

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
    @Excel(name = "父级id")
    private Long pid;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    @Excel(name = "排序")
    private Integer sort;
    /**
     * 字段描述：祖籍
     */
    @JsonProperty
    @Excel(name = "祖籍")
    private String ancestors;
    /**
     * 字段描述：图纸编号
     */
    @JsonProperty
    @Excel(name = "图纸编号")
    private String blueprintNum;
    /**
     * 字段描述：图纸名称
     */
    @JsonProperty
    @Excel(name = "图纸名称")
    private String blueprintName;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private String version;
    /**
     * 字段描述：图纸数量
     */
    @JsonProperty
    @Excel(name = "图纸数量")
    private Integer blueprintCount;
    /**
     * 字段描述：wbs
     */
    @JsonProperty
    @Excel(name = "wbs")
    private String wbsCode;
    /**
     * 字段描述：wbs名称
     */
    @JsonProperty
    @Excel(name = "wbs名称")
    private String wbsName;
    /**
     * 字段描述：计划开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划开工日期", dateFormat = "yyyy-MM-dd")
    private Date startDatePlan;
    /**
     * 字段描述：发放人
     */
    @JsonProperty
    @Excel(name = "发放人")
    private String senderId;
    /**
     * 字段描述：发放人名称
     */
    @JsonProperty
    @Excel(name = "发放人名称")
    private String senderName;
    /**
     * 字段描述：图纸发放日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "图纸发放日期", dateFormat = "yyyy-MM-dd")
    private Date sendDate;
    /**
     * 字段描述：图纸接收人
     */
    @JsonProperty
    @Excel(name = "图纸接收人")
    private Integer receiverId;
    /**
     * 字段描述：图纸接收人名称
     */
    @JsonProperty
    @Excel(name = "图纸接收人名称")
    private String receiverName;
    /**
     * 字段描述：是否变更
     */
    @JsonProperty
    @Excel(name = "是否变更")
    private String changeOr;
    /**
     * 字段描述：图纸有效性
     */
    @JsonProperty
    @Excel(name = "图纸有效性")
    private String blueprintValid;
    /**
     * 字段描述：图纸是否回收
     */
    @JsonProperty
    @Excel(name = "图纸是否回收")
    private String recycleOr;
    /**
     * 字段描述：章有效性
     */
    @JsonProperty
    @Excel(name = "章有效性")
    private String signetValid;
    /**
     * 字段描述：作废日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "作废日期", dateFormat = "yyyy-MM-dd")
    private Date cancelDate;
    /**
     * 字段描述：附件id
     */
    @JsonProperty
    @Excel(name = "附件id")
    private String fileGroupId;
    /**
     * 字段描述：
     */
    @JsonProperty
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
}
