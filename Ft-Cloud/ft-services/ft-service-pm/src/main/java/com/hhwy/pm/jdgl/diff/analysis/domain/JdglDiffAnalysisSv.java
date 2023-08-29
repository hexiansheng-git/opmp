package com.hhwy.pm.jdgl.diff.analysis.domain;

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

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:27
 * @remark jdgl_diff_analysis_sv
 */
public class JdglDiffAnalysisSv extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：差异分析主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "差异分析主表id")
    private Long diffAnalysisId;
    /**
     * 字段描述：计划项编号
     */
    @JsonProperty
    @Excel(name = "计划项编号")
    private String planItemCode;
    /**
     * 字段描述：计划项编号
     */
    @JsonProperty
    @Excel(name = "计划项编号")
    private String planItemName;
    /**
     * 字段描述：是否关键路径
     */
    @JsonProperty
    @Excel(name = "是否关键路径")
    private String isCriticalPath;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：工程量
     */
    @JsonProperty
    @Excel(name = "工程量")
    private BigDecimal designNum;
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
     * 字段描述：实际开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际开始日期", dateFormat = "yyyy-MM-dd")
    private Date actStartDate;
    /**
     * 字段描述：实际结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际结束日期", dateFormat = "yyyy-MM-dd")
    private Date actEndDate;
    /**
     * 字段描述：当前偏差量
     */
    @JsonProperty
    @Excel(name = "当前偏差量")
    private BigDecimal thisDeviationNum;
    /**
     * 字段描述：SV值
     */
    @JsonProperty
    @Excel(name = "SV值")
    private BigDecimal svNum;
    /**
     * 字段描述：原因分析
     */
    @JsonProperty
    @Excel(name = "原因分析")
    private BigDecimal causeAnalysis;
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

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Long getDiffAnalysisId() {
        return diffAnalysisId;
    }

    @JsonIgnore
    public void setDiffAnalysisId(Long diffAnalysisId) {
        this.diffAnalysisId = diffAnalysisId;
    }

    @JsonIgnore
    public String getPlanItemCode() {
        return planItemCode;
    }

    @JsonIgnore
    public void setPlanItemCode(String planItemCode) {
        this.planItemCode = planItemCode;
    }

    @JsonIgnore
    public String getPlanItemName() {
        return planItemName;
    }

    @JsonIgnore
    public void setPlanItemName(String planItemName) {
        this.planItemName = planItemName;
    }

    @JsonIgnore
    public String getIsCriticalPath() {
        return isCriticalPath;
    }

    @JsonIgnore
    public void setIsCriticalPath(String isCriticalPath) {
        this.isCriticalPath = isCriticalPath;
    }

    @JsonIgnore
    public String getUnit() {
        return unit;
    }

    @JsonIgnore
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonIgnore
    public BigDecimal getDesignNum() {
        return designNum;
    }

    @JsonIgnore
    public void setDesignNum(BigDecimal designNum) {
        this.designNum = designNum;
    }

    @JsonIgnore
    public Date getPlanStartDate() {
        return planStartDate;
    }

    @JsonIgnore
    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    @JsonIgnore
    public Date getPlanEndDate() {
        return planEndDate;
    }

    @JsonIgnore
    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    @JsonIgnore
    public Date getActStartDate() {
        return actStartDate;
    }

    @JsonIgnore
    public void setActStartDate(Date actStartDate) {
        this.actStartDate = actStartDate;
    }

    @JsonIgnore
    public Date getActEndDate() {
        return actEndDate;
    }

    @JsonIgnore
    public void setActEndDate(Date actEndDate) {
        this.actEndDate = actEndDate;
    }

    @JsonIgnore
    public BigDecimal getThisDeviationNum() {
        return thisDeviationNum;
    }

    @JsonIgnore
    public void setThisDeviationNum(BigDecimal thisDeviationNum) {
        this.thisDeviationNum = thisDeviationNum;
    }

    @JsonIgnore
    public BigDecimal getSvNum() {
        return svNum;
    }

    @JsonIgnore
    public void setSvNum(BigDecimal svNum) {
        this.svNum = svNum;
    }

    @JsonIgnore
    public BigDecimal getCauseAnalysis() {
        return causeAnalysis;
    }

    @JsonIgnore
    public void setCauseAnalysis(BigDecimal causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
    }

    @JsonIgnore
    public String getFileGroupId() {
        return fileGroupId;
    }

    @JsonIgnore
    public void setFileGroupId(String fileGroupId) {
        this.fileGroupId = fileGroupId;
    }

    @JsonIgnore
    public String getRemark() {
        return remark;
    }

    @JsonIgnore
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonIgnore
    public String getCreateUser() {
        return createUser;
    }

    @JsonIgnore
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @JsonIgnore
    public String getCreateUserName() {
        return createUserName;
    }

    @JsonIgnore
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @JsonIgnore
    public Date getCreateTime() {
        return createTime;
    }

    @JsonIgnore
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonIgnore
    public String getUpdateUser() {
        return updateUser;
    }

    @JsonIgnore
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @JsonIgnore
    public Date getUpdateTime() {
        return updateTime;
    }

    @JsonIgnore
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JsonIgnore
    public String getDelUser() {
        return delUser;
    }

    @JsonIgnore
    public void setDelUser(String delUser) {
        this.delUser = delUser;
    }

    @JsonIgnore
    public Date getDelTime() {
        return delTime;
    }

    @JsonIgnore
    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    @JsonIgnore
    public String getDelFlag() {
        return delFlag;
    }

    @JsonIgnore
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @JsonIgnore
    public String getPtVar1() {
        return ptVar1;
    }

    @JsonIgnore
    public void setPtVar1(String ptVar1) {
        this.ptVar1 = ptVar1;
    }

    @JsonIgnore
    public String getPtVar2() {
        return ptVar2;
    }

    @JsonIgnore
    public void setPtVar2(String ptVar2) {
        this.ptVar2 = ptVar2;
    }

    @JsonIgnore
    public String getPtVar3() {
        return ptVar3;
    }

    @JsonIgnore
    public void setPtVar3(String ptVar3) {
        this.ptVar3 = ptVar3;
    }

    @JsonIgnore
    public String getPtVar4() {
        return ptVar4;
    }

    @JsonIgnore
    public void setPtVar4(String ptVar4) {
        this.ptVar4 = ptVar4;
    }

    @JsonIgnore
    public String getPtVar5() {
        return ptVar5;
    }

    @JsonIgnore
    public void setPtVar5(String ptVar5) {
        this.ptVar5 = ptVar5;
    }

    @JsonIgnore
    public String getTaskStatus() {
        return taskStatus;
    }

    @JsonIgnore
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @JsonIgnore
    public Long getRegionId() {
        return regionId;
    }

    @JsonIgnore
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @JsonIgnore
    public String getRegionName() {
        return regionName;
    }

    @JsonIgnore
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @JsonIgnore
    public Long getProjectId() {
        return projectId;
    }

    @JsonIgnore
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @JsonIgnore
    public String getProjectName() {
        return projectName;
    }

    @JsonIgnore
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
