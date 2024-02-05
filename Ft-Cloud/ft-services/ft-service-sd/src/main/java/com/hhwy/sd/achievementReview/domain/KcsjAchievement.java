package com.hhwy.sd.achievementReview.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2024-02-05 09:04:08
 * @remark kcsj_achievement
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KcsjAchievement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：外键（成果评审id）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "外键（成果评审id）")
    private Long foreignId;
    /**
     * 字段描述：勘察设计成果名称
     */
    @JsonProperty
    @Excel(name = "勘察设计成果名称")
    private String achievementName;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private String version;
    /**
     * 字段描述：评审级别
     */
    @JsonProperty
    @Excel(name = "评审级别")
    private String reviewGrade;
    /**
     * 字段描述：成果状态
     */
    @JsonProperty
    @Excel(name = "成果状态")
    private String achievementStatus;
    /**
     * 字段描述：计划提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划提交日期", dateFormat = "yyyy-MM-dd")
    private Date planSubmitDate;
    /**
     * 字段描述：实际提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际提交日期", dateFormat = "yyyy-MM-dd")
    private Date actualSubmitDate;
    /**
     * 字段描述：形式审查
     */
    @JsonProperty
    @Excel(name = "形式审查")
    private String formExamine;
    /**
     * 字段描述：计划评审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划评审日期", dateFormat = "yyyy-MM-dd")
    private Date planReviewDate;
    /**
     * 字段描述：实际评审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际评审日期", dateFormat = "yyyy-MM-dd")
    private Date actualReviewDate;
    /**
     * 字段描述：负责人
     */
    @JsonProperty
    @Excel(name = "负责人")
    private String principal;
    /**
     * 字段描述：负责人id
     */
    @JsonProperty
    @Excel(name = "负责人id")
    private String principalId;
    /**
     * 字段描述：评审专家
     */
    @JsonProperty
    @Excel(name = "评审专家")
    private String reviewExpert;
    /**
     * 字段描述：评审意见（附件）
     */
    @JsonProperty
    @Excel(name = "评审意见（附件）")
    private String reviewOpinion;
    /**
     * 字段描述：评审结果
     */
    @JsonProperty
    @Excel(name = "评审结果")
    private String reviewResult;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
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
