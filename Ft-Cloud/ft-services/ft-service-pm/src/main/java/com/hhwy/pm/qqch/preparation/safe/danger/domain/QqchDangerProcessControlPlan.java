package com.hhwy.pm.qqch.preparation.safe.danger.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-07 14:24:24
 * @remark qqch_danger_process_control_plan
 */
@Data
public class QqchDangerProcessControlPlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：危大工程编号
     */
    @JsonProperty
    @Excel(name = "危大工程编号")
    private String schemeCode;
    /**
     * 字段描述：危大工程名称
     */
    @JsonProperty
    @Excel(name = "危大工程名称")
    private String schemeName;
    /**
     * 字段描述：协作单位
     */
    @JsonProperty
    @Excel(name = "协作单位")
    private String assistUnit;
    /**
     * 字段描述：计划开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划开工日期", dateFormat = "yyyy-MM-dd")
    private Date planStartDate;
    /**
     * 字段描述：方案定稿日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "方案定稿日期", dateFormat = "yyyy-MM-dd")
    private Date schemeFinalizeDate;
    /**
     * 字段描述：三级技术交底日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "三级技术交底日期", dateFormat = "yyyy-MM-dd")
    private Date thirdDisclosureDate;
    /**
     * 字段描述：安全培训日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "安全培训日期", dateFormat = "yyyy-MM-dd")
    private Date safeTrainDate;
    /**
     * 字段描述：班前讲话
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "班前讲话", dateFormat = "yyyy-MM-dd")
    private Date preClassSpeechDate;
    /**
     * 字段描述：领导带班检查频率（字典类型leader_examine_frequency）
     */
    @JsonProperty
    @Excel(name = "领导带班检查频率（字典类型leader_examine_frequency）")
    private String leaderExamineFrequency;
    /**
     * 字段描述：领导带班检查表附件组id
     */
    @JsonProperty
    @Excel(name = "领导带班检查表附件组id")
    private String leaderFileGroupId;
    /**
     * 字段描述：专项检查频率（字典类型special_examine_frequency）
     */
    @JsonProperty
    @Excel(name = "专项检查频率（字典类型special_examine_frequency）")
    private String specialExamineFrequency;

    /**
     * 字段描述：专项检查表附件组id
     */
    @JsonProperty
    @Excel(name = "专项检查表附件组id")
    private String specialFileGroupId;
    /**
     * 字段描述：是否首件
     */
    @JsonProperty
    @Excel(name = "首件实施前安全条件确认")
    private String whetherFirst;
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
