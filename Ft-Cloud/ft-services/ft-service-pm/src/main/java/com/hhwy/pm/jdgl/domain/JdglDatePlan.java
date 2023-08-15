package com.hhwy.pm.jdgl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fushudong
 * @date 2023-08-14 17:55:25
 * @remark jdgl_date_plan
 */
public class JdglDatePlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：计划类型，年、季度、月、周
     */
    @JsonProperty
    @Excel(name = "计划类型，年、季度、月、周")
    private String planType;
    /**
     * 字段描述：编纂版本
     */
    @JsonProperty
    @Excel(name = "编纂版本")
    private String compileVersion;
    /**
     * 字段描述：过期标识：0未过期；1已过期
     */
    @JsonProperty
    @Excel(name = "过期标识：0未过期；1已过期")
    private String expireDateFlag;
    /**
     * 字段描述：编写期次，时间信息：年度计划显示年份、季度显示年+季度
     */
    @JsonProperty
    @Excel(name = "编写期次，时间信息：年度计划显示年份、季度显示年+季度")
    private String compileDateVersion;
    /**
     * 字段描述：编制人
     */
    @JsonProperty
    @Excel(name = "编制人")
    private String compileUser;
    /**
     * 字段描述：编纂日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "编纂日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date compileDate;
    /**
     * 字段描述：计划生产总值（合同币种）
     */
    @JsonProperty
    @Excel(name = "计划生产总值（合同币种）")
    private BigDecimal prodValueCustUnit;
    /**
     * 字段描述：计划生产总值（万美元）
     */
    @JsonProperty
    @Excel(name = "计划生产总值（万美元）")
    private BigDecimal prodValueDollar;
    /**
     * 字段描述：货币类型
     */
    @JsonProperty
    @Excel(name = "货币类型")
    private String currencyUnit;
    /**
     * 字段描述：汇率
     */
    @JsonProperty
    @Excel(name = "汇率")
    private BigDecimal exchangeRate;
    /**
     * 字段描述：批复计划产值（美元）
     */
    @JsonProperty
    @Excel(name = "批复计划产值（美元）")
    private String approveProdValueDollar;
    /**
     * 字段描述：计划说明
     */
    @JsonProperty
    @Excel(name = "计划说明")
    private String planDescription;
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
    public String getPlanType() {
        return planType;
    }

    @JsonIgnore
    public void setPlanType(String planType) {
        this.planType = planType;
    }

    @JsonIgnore
    public String getCompileVersion() {
        return compileVersion;
    }

    @JsonIgnore
    public void setCompileVersion(String compileVersion) {
        this.compileVersion = compileVersion;
    }

    @JsonIgnore
    public String getExpireDateFlag() {
        return expireDateFlag;
    }

    @JsonIgnore
    public void setExpireDateFlag(String expireDateFlag) {
        this.expireDateFlag = expireDateFlag;
    }

    @JsonIgnore
    public String getCompileDateVersion() {
        return compileDateVersion;
    }

    @JsonIgnore
    public void setCompileDateVersion(String compileDateVersion) {
        this.compileDateVersion = compileDateVersion;
    }

    @JsonIgnore
    public String getCompileUser() {
        return compileUser;
    }

    @JsonIgnore
    public void setCompileUser(String compileUser) {
        this.compileUser = compileUser;
    }

    @JsonIgnore
    public Date getCompileDate() {
        return compileDate;
    }

    @JsonIgnore
    public void setCompileDate(Date compileDate) {
        this.compileDate = compileDate;
    }

    @JsonIgnore
    public BigDecimal getProdValueCustUnit() {
        return prodValueCustUnit;
    }

    @JsonIgnore
    public void setProdValueCustUnit(BigDecimal prodValueCustUnit) {
        this.prodValueCustUnit = prodValueCustUnit;
    }

    @JsonIgnore
    public BigDecimal getProdValueDollar() {
        return prodValueDollar;
    }

    @JsonIgnore
    public void setProdValueDollar(BigDecimal prodValueDollar) {
        this.prodValueDollar = prodValueDollar;
    }

    @JsonIgnore
    public String getCurrencyUnit() {
        return currencyUnit;
    }

    @JsonIgnore
    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    @JsonIgnore
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    @JsonIgnore
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @JsonIgnore
    public String getApproveProdValueDollar() {
        return approveProdValueDollar;
    }

    @JsonIgnore
    public void setApproveProdValueDollar(String approveProdValueDollar) {
        this.approveProdValueDollar = approveProdValueDollar;
    }

    @JsonIgnore
    public String getPlanDescription() {
        return planDescription;
    }

    @JsonIgnore
    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
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
    public Long getDeptId() {
        return deptId;
    }

    @JsonIgnore
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
