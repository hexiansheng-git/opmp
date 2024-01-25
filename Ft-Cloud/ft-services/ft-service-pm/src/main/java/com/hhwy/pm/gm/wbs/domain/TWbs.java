package com.hhwy.pm.gm.wbs.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * wbs
 * @author wk
 * @date 2023-08-01 11:26:43
 * @remark t_wbs
 */
@Data
@ExcelIgnoreUnannotated
public class TWbs extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private String id;
    /**
     * 字段描述：主表id,xmsl_wbs_main.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @NotNull(message = "主id不能空！",groups = ValidationGroups.Select.class)
    private Long mainId;
    /**
     * 字段描述：编号
     */
    @JsonProperty
    @Excel(name = "编号")
    @ExcelProperty("编号")
    private String code;

    //父级编码 不存在与数据库
    private String parentCode;
    //当前编码
    private String selfCode;
    /**
     * 字段描述：父级ID,最顶级为0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private String parentId;
    /**
     * 字段描述：是否包含子级，0:否,1:是
     */
    @JsonProperty
    private Integer haveChildren;
    /**
     * 字段描述：祖级ID
     */
    @JsonProperty
    private String ancestors;
    /**
     * 字段描述：祖级名称
     */
    @JsonProperty
    private String ancestorsName;
    /**
     * 字段描述：名称
     */
    @JsonProperty
    @Excel(name = "名称")
    @ExcelProperty("名称")
    private String name;

    /**
     * 字段描述：节点类型,字典:t_wbs_type
     */
    @JsonProperty
    @Excel(name = "节点类型,字典:t_wbs_type")
    private String nodeType;
    
    /**
     * 字段描述：状态,0:禁用,1:启用
     */
    @JsonProperty
    @Excel(name = "状态",readConverterExp = "0=禁用,1:启用")
    private Integer status;
    
    /**
     * 字段描述：单位,字典:t_wbs_unit
     */
    @JsonProperty
    @Excel(name = "单位,字典:t_wbs_unit")
    private String unit;
    /**
     * 字段描述：层级
     */
    @JsonProperty
    @Excel(name = "层级")
    private Integer level;
    /**
     * 字段描述：专业，字典:t_speciality
     */
    @JsonProperty
    @Excel(name = "专业，字典:t_speciality")
    private String speciality;
    /**
     * 字段描述：划分说明
     */
    @JsonProperty
    @Excel(name = "划分说明")
    private String description;
    
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
     * 字段描述：用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "用户id")
    private Long userId;
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
     * 字段描述：序号
     */
    @JsonProperty
    @Excel(name = "序号")
    private Integer sort;


    private List<TWbs> children;

}
