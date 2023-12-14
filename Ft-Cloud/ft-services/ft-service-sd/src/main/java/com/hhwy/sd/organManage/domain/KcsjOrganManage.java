package com.hhwy.sd.organManage.domain;

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
 * @date 2023-12-14 11:31:44
 * @remark kcsj_organ_manage
 */
@Data
public class KcsjOrganManage extends TreeNode<KcsjOrganManage> {
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
    @Excel(name = "父级id")
    private Long pid;
    /**
     * 字段描述：排序号
     */
    @JsonProperty
    @Excel(name = "排序号")
    private Integer sort;
    /**
     * 字段描述：叶子节点
     */
    @JsonProperty
    @Excel(name = "叶子节点")
    private String leaf;
    /**
     * 字段描述：岗位
     */
    @JsonProperty
    @Excel(name = "岗位")
    private String postName;
    /**
     * 字段描述：专业
     */
    @JsonProperty
    @Excel(name = "专业")
    private String speciality;
    /**
     * 字段描述：岗位职责
     */
    @JsonProperty
    @Excel(name = "岗位职责")
    private String postJob;
    /**
     * 字段描述：编制人数
     */
    @JsonProperty
    @Excel(name = "编制人数")
    private Integer headCount;
    /**
     * 字段描述：用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "用户id")
    private Long userId;
    /**
     * 字段描述：人员姓名
     */
    @JsonProperty
    @Excel(name = "人员姓名")
    private String userName;
    /**
     * 字段描述：工作年限
     */
    @JsonProperty
    @Excel(name = "工作年限")
    private Integer workYears;
    /**
     * 字段描述：联系方式
     */
    @JsonProperty
    @Excel(name = "联系方式")
    private String phone;
    /**
     * 字段描述：实际进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际进场日期", dateFormat = "yyyy-MM-dd")
    private Date actualEnterDate;
    /**
     * 字段描述：是否在场
     */
    @JsonProperty
    @Excel(name = "是否在场")
    private String workOrNot;
    /**
     * 字段描述：实际退场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际退场日期", dateFormat = "yyyy-MM-dd")
    private Date actualExitDate;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
    @Excel(name = "数据来源 0新增1同步")
    private String dataSource;
    /**
     * 字段描述：
     */
    @JsonProperty
    private String path;
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
     * 字段描述：预留字段1   项目编码
     */
    @JsonProperty
    @Excel(name = "预留字段1   项目编码")
    private String ptVar1;
    /**
     * 字段描述：预留字段2  leaf 是否是叶子节点 0否1是
     */
    @JsonProperty
    @Excel(name = "预留字段2  leaf 是否是叶子节点 0否1是")
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

    private String isAdd;

}
