package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain;

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
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;

/**
 * 功能描述: 科技管理 - 一般课题研发管理
 * @author fsd
 * @date 2024-01-25 10:22:49
 * @remark sgjs_technical_normal_topic
 */
public class SgjsTechnicalNormalTopic extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
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
     * 字段描述：高新资质
     */
    @JsonProperty
    @Excel(name = "高新资质")
    private String highCertificate;
    /**
     * 字段描述：课题类别编号
     */
    @JsonProperty
    @Excel(name = "课题类别编号")
    private String topicKind;
    /**
     * 字段描述：课题类别
     */
    @JsonProperty
    @Excel(name = "课题类别")
    private String topicKindName;
    /**
     * 字段描述：项目技术经济目标编号
     */
    @JsonProperty
    @Excel(name = "项目技术经济目标编号")
    private String ecoTarget;
    /**
     * 字段描述：项目技术经济目标
     */
    @JsonProperty
    @Excel(name = "项目技术经济目标")
    private String ecoTargetName;
    /**
     * 字段描述：课题状态
     */
    @JsonProperty
    @Excel(name = "课题状态")
    private String topicState;
    /**
     * 字段描述：项目成果形式
     */
    @JsonProperty
    @Excel(name = "项目成果形式")
    private String achievementKind;
    /**
     * 字段描述：研发起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "研发起始时间", dateFormat = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 字段描述：研发完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "研发完成时间", dateFormat = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 字段描述：研发人员名单
     */
    @JsonProperty
    @Excel(name = "研发人员名单")
    private String personList;
    /**
     * 字段描述：研发人员名单
     */
    @JsonProperty
    @Excel(name = "研发人员名单")
    private String personNameList;
    /**
     * 字段描述：附件id
     */
    @JsonProperty
    @Excel(name = "附件id")
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

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
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
    public String getHighCertificate() {
        return highCertificate;
    }

    @JsonIgnore
    public void setHighCertificate(String highCertificate) {
        this.highCertificate = highCertificate;
    }

    @JsonIgnore
    public String getTopicKind() {
        return topicKind;
    }

    @JsonIgnore
    public void setTopicKind(String topicKind) {
        this.topicKind = topicKind;
    }

    @JsonIgnore
    public String getTopicKindName() {
        return topicKindName;
    }

    @JsonIgnore
    public void setTopicKindName(String topicKindName) {
        this.topicKindName = topicKindName;
    }

    @JsonIgnore
    public String getEcoTarget() {
        return ecoTarget;
    }

    @JsonIgnore
    public void setEcoTarget(String ecoTarget) {
        this.ecoTarget = ecoTarget;
    }

    @JsonIgnore
    public String getEcoTargetName() {
        return ecoTargetName;
    }

    @JsonIgnore
    public void setEcoTargetName(String ecoTargetName) {
        this.ecoTargetName = ecoTargetName;
    }

    @JsonIgnore
    public String getTopicState() {
        return topicState;
    }

    @JsonIgnore
    public void setTopicState(String topicState) {
        this.topicState = topicState;
    }

    @JsonIgnore
    public String getAchievementKind() {
        return achievementKind;
    }

    @JsonIgnore
    public void setAchievementKind(String achievementKind) {
        this.achievementKind = achievementKind;
    }

    @JsonIgnore
    public Date getStartDate() {
        return startDate;
    }

    @JsonIgnore
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonIgnore
    public Date getEndDate() {
        return endDate;
    }

    @JsonIgnore
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @JsonIgnore
    public String getPersonList() {
        return personList;
    }

    @JsonIgnore
    public void setPersonList(String personList) {
        this.personList = personList;
    }

    @JsonIgnore
    public String getPersonNameList() {
        return personNameList;
    }

    @JsonIgnore
    public void setPersonNameList(String personNameList) {
        this.personNameList = personNameList;
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
