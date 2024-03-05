package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.domain;

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
 * @author fushudong
 * @date 2024-03-05 17:02:49
 * @remark sgsj_technical_science_topic_apply
 */
public class SgsjTechnicalScienceTopicApply extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：申请状态
     * 字典: data_current_state
     * 1	未发起
     * 2	审批中
     * 3	申请通过
     * 4	申请不通过
     */
    @JsonProperty
    private String applyState;
    /**
     * 字段描述：当前状态
     */
    @JsonProperty
    @Excel(name = "当前状态")
    private String taskStatus;
    /**
     * 字段描述：当前经办人
     */
    @JsonProperty
    @Excel(name = "当前经办人")
    private String handlePerson;
    /**
     * 字段描述：课题编号
     */
    @JsonProperty
    @Excel(name = "课题编号")
    private String topicCode;
    /**
     * 字段描述：课题名称
     */
    @JsonProperty
    @Excel(name = "课题名称")
    private String topicName;
    /**
     * 字段描述：课题研发日期
     */
    @JsonProperty
    @Excel(name = "课题研发日期")
    private String startEndDate;
    /**
     * 字段描述：课题负责人id
     */
    @JsonProperty
    @Excel(name = "课题负责人id")
    private String dutyPerson;
    /**
     * 字段描述：课题负责人
     */
    @JsonProperty
    @Excel(name = "课题负责人")
    private String dutyPersonName;
    /**
     * 字段描述：协作单位
     */
    @JsonProperty
    @Excel(name = "协作单位")
    private String togetherUnit;
    /**
     * 字段描述：其他协作单位
     */
    @JsonProperty
    @Excel(name = "其他协作单位")
    private String togetherUnitOther;
    /**
     * 字段描述：研发预算（万元）
     */
    @JsonProperty
    @Excel(name = "研发预算（万元）")
    private BigDecimal rdCost;
    /**
     * 字段描述：登记人id
     */
    @JsonProperty
    @Excel(name = "登记人id")
    private String writeInPerson;
    /**
     * 字段描述：登记人
     */
    @JsonProperty
    @Excel(name = "登记人")
    private String writeInPersonName;
    /**
     * 字段描述：登记人联系方式
     */
    @JsonProperty
    @Excel(name = "登记人联系方式")
    private String writeInPersonPhoneNum;
    /**
     * 字段描述：课题简介
     */
    @JsonProperty
    @Excel(name = "课题简介")
    private String topicSummary;
    /**
     * 字段描述：课题附件
     */
    @JsonProperty
    @Excel(name = "课题附件")
    private String topicFileGroupId;
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

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getApplyState() {
        return applyState;
    }

    @JsonIgnore
    public void setApplyState(String applyState) {
        this.applyState = applyState;
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
    public String getHandlePerson() {
        return handlePerson;
    }

    @JsonIgnore
    public void setHandlePerson(String handlePerson) {
        this.handlePerson = handlePerson;
    }

    @JsonIgnore
    public String getTopicCode() {
        return topicCode;
    }

    @JsonIgnore
    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    @JsonIgnore
    public String getTopicName() {
        return topicName;
    }

    @JsonIgnore
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @JsonIgnore
    public String getStartEndDate() {
        return startEndDate;
    }

    @JsonIgnore
    public void setStartEndDate(String startEndDate) {
        this.startEndDate = startEndDate;
    }

    @JsonIgnore
    public String getDutyPerson() {
        return dutyPerson;
    }

    @JsonIgnore
    public void setDutyPerson(String dutyPerson) {
        this.dutyPerson = dutyPerson;
    }

    @JsonIgnore
    public String getDutyPersonName() {
        return dutyPersonName;
    }

    @JsonIgnore
    public void setDutyPersonName(String dutyPersonName) {
        this.dutyPersonName = dutyPersonName;
    }

    @JsonIgnore
    public String getTogetherUnit() {
        return togetherUnit;
    }

    @JsonIgnore
    public void setTogetherUnit(String togetherUnit) {
        this.togetherUnit = togetherUnit;
    }

    @JsonIgnore
    public String getTogetherUnitOther() {
        return togetherUnitOther;
    }

    @JsonIgnore
    public void setTogetherUnitOther(String togetherUnitOther) {
        this.togetherUnitOther = togetherUnitOther;
    }

    @JsonIgnore
    public BigDecimal getRdCost() {
        return rdCost;
    }

    @JsonIgnore
    public void setRdCost(BigDecimal rdCost) {
        this.rdCost = rdCost;
    }

    @JsonIgnore
    public String getWriteInPerson() {
        return writeInPerson;
    }

    @JsonIgnore
    public void setWriteInPerson(String writeInPerson) {
        this.writeInPerson = writeInPerson;
    }

    @JsonIgnore
    public String getWriteInPersonName() {
        return writeInPersonName;
    }

    @JsonIgnore
    public void setWriteInPersonName(String writeInPersonName) {
        this.writeInPersonName = writeInPersonName;
    }

    @JsonIgnore
    public String getWriteInPersonPhoneNum() {
        return writeInPersonPhoneNum;
    }

    @JsonIgnore
    public void setWriteInPersonPhoneNum(String writeInPersonPhoneNum) {
        this.writeInPersonPhoneNum = writeInPersonPhoneNum;
    }

    @JsonIgnore
    public String getTopicSummary() {
        return topicSummary;
    }

    @JsonIgnore
    public void setTopicSummary(String topicSummary) {
        this.topicSummary = topicSummary;
    }

    @JsonIgnore
    public String getTopicFileGroupId() {
        return topicFileGroupId;
    }

    @JsonIgnore
    public void setTopicFileGroupId(String topicFileGroupId) {
        this.topicFileGroupId = topicFileGroupId;
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
}
