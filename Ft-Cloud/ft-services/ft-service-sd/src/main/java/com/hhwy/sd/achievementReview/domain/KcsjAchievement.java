package com.hhwy.sd.achievementReview.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
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
    private Long id;
    /**
     * 字段描述：外键（成果评审id）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long foreignId;
    /**
     * 字段描述：勘察设计成果名称
     */
    @JsonProperty
    @FtExcel(name = "勘察设计成果名称")
    private String achievementName;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @FtExcel(name = "版本")
    private String version;
    /**
     * 字段描述：评审级别 字典：achievement_review_grade
     */
    @JsonProperty
    @FtExcel(name = "评审级别",dictType = "achievement_review_grade")
    private String reviewGrade;
    /**
     * 字段描述：成果状态  字典：achievement_review_status
     */
    @JsonProperty
    @FtExcel(name = "成果状态",dictType = "achievement_review_status")
    private String achievementStatus;
    /**
     * 字段描述：计划提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划提交日期", dateFormat = "yyyy年MM月dd日")
    private Date planSubmitDate;
    /**
     * 字段描述：实际提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "实际提交日期", dateFormat = "yyyy年MM月dd日")
    private Date actualSubmitDate;
    /**
     * 字段描述：形式审查  字典：form_examine
     */
    @JsonProperty
    @FtExcel(name = "形式审查",dictType = "form_examine")
    private String formExamine;
    /**
     * 字段描述：计划评审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划评审日期", dateFormat = "yyyy年MM月dd日")
    private Date planReviewDate;
    /**
     * 字段描述：实际评审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "实际评审日期", dateFormat = "yyyy年MM月dd日")
    private Date actualReviewDate;
    /**
     * 字段描述：负责人
     */
    @JsonProperty
    @FtExcel(name = "负责人")
    private String principal;
    /**
     * 字段描述：负责人id
     */
    @JsonProperty
    private String principalId;
    /**
     * 字段描述：评审专家
     */
    @JsonProperty
    @FtExcel(name = "评审专家")
    private String reviewExpert;
    /**
     * 字段描述：评审意见（附件）
     */
    @JsonProperty
    private String reviewOpinion;
    /**
     * 字段描述：评审结果
     */
    @JsonProperty
    @FtExcel(name = "评审结果",dictType = "review_result")
    private String reviewResult;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    private String fileGroupId;
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

    /**
     * 是否是新增数据
     */
    private String isAdd;
}
