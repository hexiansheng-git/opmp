package com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain;

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
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;
import lombok.Data;

/**
 * @author cjh
 * @date 2023-08-24 14:05:37
 * @remark jdgl_day_schedule
 */
@Data
public class JdglDaySchedule extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "日期", dateFormat = "yyyy-MM-dd")
    private Date date;
    /**
     * 字段描述：合同币种
     */
    @JsonProperty
    @Excel(name = "合同币种")
    private String custUnit;
    /**
     * 字段描述：合同币种编码
     */
    @JsonProperty
    @Excel(name = "合同币种编码")
    private String custUnitCode;
    /**
     * 字段描述：本日完成（合同币种）
     */
    @JsonProperty
    @Excel(name = "本日完成（合同币种）")
    private BigDecimal dayValueCu;
    /**
     * 字段描述：开累完成（合同币种）
     */
    @JsonProperty
    @Excel(name = "开累完成（合同币种）")
    private BigDecimal totalValueCu;
    /**
     * 字段描述：汇率
     */
    @JsonProperty
    @Excel(name = "汇率")
    private BigDecimal exchangeRate;
    /**
     * 字段描述：本日完成（万美元）
     */
    @JsonProperty
    @Excel(name = "本日完成（万美元）")
    private BigDecimal dayValueDl;
    /**
     * 字段描述：开累完成（万美元）
     */
    @JsonProperty
    @Excel(name = "开累完成（万美元）")
    private BigDecimal totalValueDl;
    /**
     * 字段描述：日进度说明
     */
    @JsonProperty
    @Excel(name = "日进度说明")
    private String description;
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
     * 字段描述：进度填报wbs集合
     */
    @JsonProperty
    private List<JdglDayScheduleWbs> jdglDayScheduleWbsList;

    /**
     * 需要删除的wbsid集合
     */
    @JsonProperty
    private List<JdglDayScheduleWbs> deleteWbsList;

    /**
     * 需要更新的wbs集合
     */
    @JsonProperty
    private List<JdglDayScheduleWbs> jdglDayScheduleWbsListNeedUpdate;

}
