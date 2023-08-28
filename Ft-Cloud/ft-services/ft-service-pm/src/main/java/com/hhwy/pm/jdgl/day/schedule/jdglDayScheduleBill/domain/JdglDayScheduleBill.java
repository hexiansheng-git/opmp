package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain;

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
 * @author cjh
 * @date 2023-08-24 14:05:49
 * @remark jdgl_day_schedule_bill
 */
@Data
public class JdglDayScheduleBill extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：日进度填报id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "日进度填报id")
    private Long dayScheduleId;
    /**
     * 字段描述：wbsId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long wbsId;
    /**
     * 字段描述：项目WBS编码
     */
    @JsonProperty
    @Excel(name = "项目WBS编码")
    private String wbsCode;
    /**
     * 字段描述：项目WBS名称
     */
    @JsonProperty
    @Excel(name = "项目WBS名称")
    private String wbsName;
    /**
     * 字段描述：清单编码
     */
    @JsonProperty
    @Excel(name = "清单编码")
    private String billCode;
    /**
     * 字段描述：清单名称
     */
    @JsonProperty
    @Excel(name = "清单名称")
    private String billName;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：设计工程量
     */
    @JsonProperty
    @Excel(name = "设计工程量")
    private BigDecimal designQuantity;
    /**
     * 字段描述：剩余工程量
     */
    @JsonProperty
    @Excel(name = "剩余工程量")
    private BigDecimal remainQuantity;
    /**
     * 字段描述：本日完成工程量
     */
    @JsonProperty
    @Excel(name = "本日完成工程量")
    private BigDecimal thisQuantity;
    /**
     * 字段描述：清单单价
     */
    @JsonProperty
    @Excel(name = "清单单价")
    private BigDecimal billPrice;
    /**
     * 字段描述：本日清单产值
     */
    @JsonProperty
    @Excel(name = "本日清单产值")
    private BigDecimal billValue;
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
     * 字段描述：是否是关联形象清单
     */
    @JsonProperty
    @Excel(name = "是否是关联形象清单")
    private String isMain;

}
