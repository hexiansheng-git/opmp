package com.hhwy.pm.qqch.preparation.safe.risk.domain;

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
import lombok.Data;

/**
 * @author zq
 * @date 2023-08-11 13:41:25
 * @remark qqch_safe_risk_list
 */
@Data
public class QqchSafeRiskList extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：wbs_id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "wbs_id")
    private Long wbsId;
    /**
     * 字段描述：
     */
    @JsonProperty
    private String type;
    /**
     * 字段描述：计划开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划开始日期", dateFormat = "yyyy-MM-dd")
    private Date planStartDate;
    /**
     * 字段描述：计划结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划结束日期", dateFormat = "yyyy-MM-dd")
    private Date planEndDate;
    /**
     * 字段描述：计划交底日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划交底日期", dateFormat = "yyyy-MM-dd")
    private Date planOverDate;
    /**
     * 字段描述：检查频率（check_rate）
     */
    @JsonProperty
    @Excel(name = "检查频率（check_rate）")
    private String checkRate;
    /**
     * 字段描述：检查人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "检查人id")
    private Long checkPersonId;
    /**
     * 字段描述：检查人姓名
     */
    @JsonProperty
    @Excel(name = "检查人姓名")
    private String checkPersonName;
    /**
     * 字段描述：现场负责人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "现场负责人id")
    private Long responsePersonId;
    /**
     * 字段描述：现场负责人姓名
     */
    @JsonProperty
    @Excel(name = "现场负责人姓名")
    private String responsePersonName;
    /**
     * 字段描述：协作单位id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "协作单位id")
    private Long collaboraPersonId;
    /**
     * 字段描述：协作单位名称
     */
    @JsonProperty
    @Excel(name = "协作单位名称")
    private String collaboraPersonName;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 0无效 1有效
     */
    @JsonProperty
    @Excel(name = "是否有效 0无效 1有效")
    private String valid;
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
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long deptId;

    private List<QqchSafeRiskListDetail> detailList;
}
