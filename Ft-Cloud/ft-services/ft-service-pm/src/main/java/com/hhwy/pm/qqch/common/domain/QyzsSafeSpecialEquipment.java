package com.hhwy.pm.qqch.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author fushudong
 * @date 2024-06-05 15:59:05
 * @remark qyzs_safe_special_equipment
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QyzsSafeSpecialEquipment extends TreeNode<QyzsSafeSpecialEquipment> {
    private static final long serialVersionUID = 1L;


    private String innerCode;
    private String parentInnerCode;
    @JsonProperty
    private String isAdd;

    private String[] kind3Arr;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：代码
     */
    @JsonProperty
    @Excel(name = "代码")
    private Integer seriCode;
    /**
     * 字段描述：种类
     */
    @JsonProperty
    @Excel(name = "种类")
    private String kind1;
    /**
     * 字段描述：类别
     */
    @JsonProperty
    @Excel(name = "类别")
    private String kind2;
    /**
     * 字段描述：品种
     */
    @JsonProperty
    @Excel(name = "品种")
    private String kind3;
    /**
     * 字段描述：风险事件
     */
    @JsonProperty
    @Excel(name = "风险事件")
    private String riskEvent;
    /**
     * 字段描述：可能后果
     */
    @JsonProperty
    @Excel(name = "可能后果")
    private String perhapsTrouble;
    /**
     * 字段描述：风险控制措施
     */
    @JsonProperty
    @Excel(name = "风险控制措施")
    private String controlMeasure;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long projectId;
    /**
     * 字段描述：PJ码
     */
    @JsonProperty
    private String projectCode;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    private String ptVar5;

}
