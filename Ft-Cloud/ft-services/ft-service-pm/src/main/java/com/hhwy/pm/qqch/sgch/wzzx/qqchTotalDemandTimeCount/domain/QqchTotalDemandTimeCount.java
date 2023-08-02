package com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ldd
 * @date 2023-08-02 10:55:17
 * @remark qqch_total_demand_time_count
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QqchTotalDemandTimeCount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：物资总需id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "物资总需id")
    private Long demandId;
    /**
     * 字段描述：物资编码
     */
    @JsonProperty
    @Excel(name = "物资编码")
    private String materialCode;
    /**
     * 字段描述：年份
     */
    @JsonProperty
    @Excel(name = "年份")
    private String year;
    /**
     * 字段描述：一月数量
     */
    @JsonProperty
    @Excel(name = "一月数量")
    private BigDecimal janNum;
    /**
     * 字段描述：二月数量
     */
    @JsonProperty
    @Excel(name = "二月数量")
    private BigDecimal febNum;
    /**
     * 字段描述：三月数量
     */
    @JsonProperty
    @Excel(name = "三月数量")
    private BigDecimal marNum;
    /**
     * 字段描述：四月数量
     */
    @JsonProperty
    @Excel(name = "四月数量")
    private BigDecimal aprNum;
    /**
     * 字段描述：五月数量
     */
    @JsonProperty
    @Excel(name = "五月数量")
    private BigDecimal mayNum;
    /**
     * 字段描述：六月数量
     */
    @JsonProperty
    @Excel(name = "六月数量")
    private BigDecimal junNum;
    /**
     * 字段描述：七月数量
     */
    @JsonProperty
    @Excel(name = "七月数量")
    private BigDecimal julNum;
    /**
     * 字段描述：八月数量
     */
    @JsonProperty
    @Excel(name = "八月数量")
    private BigDecimal augNum;
    /**
     * 字段描述：九月数量
     */
    @JsonProperty
    @Excel(name = "九月数量")
    private BigDecimal septNum;
    /**
     * 字段描述：十月数量
     */
    @JsonProperty
    @Excel(name = "十月数量")
    private BigDecimal octNum;
    /**
     * 字段描述：十一月数量
     */
    @JsonProperty
    @Excel(name = "十一月数量")
    private BigDecimal novNum;
    /**
     * 字段描述：十二月数量
     */
    @JsonProperty
    @Excel(name = "十二月数量")
    private BigDecimal decNum;
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
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    @Excel(name = "流程状态（5已完成）")
    private String taskStatus;
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
}
