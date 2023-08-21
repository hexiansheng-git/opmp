package com.hhwy.pm.qqch.preparation.doc.dwg.domain;

import com.hhwy.common.core.web.domain.BaseEntity;

import java.util.Date;
import java.math.BigDecimal;

import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author mls
 * @date 2023-07-25 18:25:42
 * @remark qqch_doc_dwg
 */
@Data
@ToString
public class QqchDocDwg extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：WBS编码
     */
    @JsonProperty
    @Excel(name = "WBS编码")
    @NotNull(message = "WBS编码不能为空！",groups = ValidationGroups.Save.class)
    private String wbsCode;
    /**
     * 字段描述：WBS名称
     */
    @JsonProperty
    @Excel(name = "WBS名称")
    @NotNull(message = "WBS名称不能为空！",groups = ValidationGroups.Save.class)
    private String wbsName;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：是否关键线路:0-否;1-是
     */
    @JsonProperty
    @Excel(name = "是否关键线路:0-否;1-是")
    private String keyFlag;
    /**
     * 字段描述：计划开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划开工日期", dateFormat = "yyyy-MM-dd")
    private Date planBeginDate;
    /**
     * 字段描述：计划接收图纸日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划接收图纸日期", dateFormat = "yyyy-MM-dd")
    private Date planDwgRecDate;
    /**
     * 字段描述：计划会审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划会审日期", dateFormat = "yyyy-MM-dd")
    private Date planReviewDate;
    /**
     * 字段描述：计划下发图纸日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划下发图纸日期", dateFormat = "yyyy-MM-dd")
    private Date planDwgSendDate;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    @Excel(name = "是否有效 1-有效 0-失效")
    private String valid;
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
    @Excel(name = "排序")
    private Integer sort;
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
