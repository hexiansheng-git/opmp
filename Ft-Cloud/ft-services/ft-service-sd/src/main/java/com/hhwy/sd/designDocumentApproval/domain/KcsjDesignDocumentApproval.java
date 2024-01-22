package com.hhwy.sd.designDocumentApproval.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.validation.ValidationGroups;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author wll
 * @date 2024-01-19 17:39:21
 * @remark kcsj_design_document_approval
 */
public class KcsjDesignDocumentApproval extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：设计文件名称
     */
    @JsonProperty
    @NotBlank(message = "请填写设计文件名称",groups ={ValidationGroups.Save.class})
    @Excel(name = "设计文件名称")
    private String designDocumentName;
    /**
     * 字段描述：设计部分范围简述
     */
    @JsonProperty
    @Excel(name = "设计部分范围简述")
    private String designScopeDescription;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：负责人id
     */
    @JsonProperty
    @Excel(name = "负责人id")
    private Long manageId;
    /**
     * 字段描述：负责人
     */
    @JsonProperty
    @NotBlank(message = "请填写负责人",groups ={ValidationGroups.Save.class})
    @Excel(name = "负责人")
    private String manager;
    /**
     * 字段描述：报监理业主日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @NotNull(message = "请填写报监理业主日期",groups ={ValidationGroups.Save.class})
    @Excel(name = "报监理业主日期", dateFormat = "yyyy年MM月dd日")
    private Date reportSupervisingOwnerDate;
    /**
     * 字段描述：监理业主联系人
     */
    @JsonProperty
    @Excel(name = "监理业主联系人")
    private String supervisingOwnerContacts;
    /**
     * 字段描述：联系方式
     */
    @JsonProperty
    @Excel(name = "联系方式")
    private String contactsInformation;
    /**
     * 字段描述：下次跟进日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @Excel(name = "下次跟进日期", dateFormat = "yyyy年MM月dd日")
    private Date nextFollowupDate;
    /**
     * 字段描述：实际反馈日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @Excel(name = "实际反馈日期", dateFormat = "yyyy年MM月dd日")
    private Date actualFeedbackDate;
    /**
     * 字段描述：反馈情况
     */
    @JsonProperty
    @Excel(name = "反馈情况")
    private String feedbackSituation;
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
     * 字段描述：数据删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据删除时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
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
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;

    /**
     * 报监理业主日期搜索字符串
     */
    @JsonProperty
    private String reportSupervisingOwnerDateStr;

    /**
     * 报监理业主日期开始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    private Date reportSupervisingOwnerDateBegin;

    /**
     * 报监理业主日期结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    private Date reportSupervisingOwnerDateEnd;

    /**
     * 下次跟进日期搜索字符串
     */
    @JsonProperty
    private String nextFollowupDateStr;

    /**
     * 下次跟进日期开始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    private Date  nextFollowupDateBegin;


    /**
     * 下次跟进日期结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    private Date  nextFollowupDateEnd;


    /**
     * 实际反馈日期搜索字符串
     */
    @JsonProperty
    private String actualFeedbackDateStr;

    /**
     * 实际反馈日期开始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    private Date  actualFeedbackDateBegin;


    /**
     * 实际反馈日期结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    private Date  actualFeedbackDateEnd;

    /**
     * 新增标识 1为新增
     */
    @JsonProperty
    private String isAdd;

    public String getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(String isAdd) {
        this.isAdd = isAdd;
    }

    public String getReportSupervisingOwnerDateStr() {
        return reportSupervisingOwnerDateStr;
    }

    public void setReportSupervisingOwnerDateStr(String reportSupervisingOwnerDateStr) {
        this.reportSupervisingOwnerDateStr = reportSupervisingOwnerDateStr;
    }

    public String getNextFollowupDateStr() {
        return nextFollowupDateStr;
    }

    public void setNextFollowupDateStr(String nextFollowupDateStr) {
        this.nextFollowupDateStr = nextFollowupDateStr;
    }

    public String getActualFeedbackDateStr() {
        return actualFeedbackDateStr;
    }

    public void setActualFeedbackDateStr(String actualFeedbackDateStr) {
        this.actualFeedbackDateStr = actualFeedbackDateStr;
    }

    public Date getReportSupervisingOwnerDateBegin() {
        return reportSupervisingOwnerDateBegin;
    }

    public void setReportSupervisingOwnerDateBegin(Date reportSupervisingOwnerDateBegin) {
        this.reportSupervisingOwnerDateBegin = reportSupervisingOwnerDateBegin;
    }

    public Date getReportSupervisingOwnerDateEnd() {
        return reportSupervisingOwnerDateEnd;
    }

    public void setReportSupervisingOwnerDateEnd(Date reportSupervisingOwnerDateEnd) {
        this.reportSupervisingOwnerDateEnd = reportSupervisingOwnerDateEnd;
    }

    public Date getNextFollowupDateBegin() {
        return nextFollowupDateBegin;
    }

    public void setNextFollowupDateBegin(Date nextFollowupDateBegin) {
        this.nextFollowupDateBegin = nextFollowupDateBegin;
    }

    public Date getNextFollowupDateEnd() {
        return nextFollowupDateEnd;
    }

    public void setNextFollowupDateEnd(Date nextFollowupDateEnd) {
        this.nextFollowupDateEnd = nextFollowupDateEnd;
    }

    public Date getActualFeedbackDateBegin() {
        return actualFeedbackDateBegin;
    }

    public void setActualFeedbackDateBegin(Date actualFeedbackDateBegin) {
        this.actualFeedbackDateBegin = actualFeedbackDateBegin;
    }

    public Date getActualFeedbackDateEnd() {
        return actualFeedbackDateEnd;
    }

    public void setActualFeedbackDateEnd(Date actualFeedbackDateEnd) {
        this.actualFeedbackDateEnd = actualFeedbackDateEnd;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getDesignDocumentName() {
        return designDocumentName;
    }

    @JsonIgnore
    public void setDesignDocumentName(String designDocumentName) {
        this.designDocumentName = designDocumentName;
    }

    @JsonIgnore
    public String getDesignScopeDescription() {
        return designScopeDescription;
    }

    @JsonIgnore
    public void setDesignScopeDescription(String designScopeDescription) {
        this.designScopeDescription = designScopeDescription;
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
    public Long getManageId() {
        return manageId;
    }

    @JsonIgnore
    public void setManageId(Long manageId) {
        this.manageId = manageId;
    }

    @JsonIgnore
    public String getManager() {
        return manager;
    }

    @JsonIgnore
    public void setManager(String manager) {
        this.manager = manager;
    }

    @JsonIgnore
    public Date getReportSupervisingOwnerDate() {
        return reportSupervisingOwnerDate;
    }

    @JsonIgnore
    public void setReportSupervisingOwnerDate(Date reportSupervisingOwnerDate) {
        this.reportSupervisingOwnerDate = reportSupervisingOwnerDate;
    }

    @JsonIgnore
    public String getSupervisingOwnerContacts() {
        return supervisingOwnerContacts;
    }

    @JsonIgnore
    public void setSupervisingOwnerContacts(String supervisingOwnerContacts) {
        this.supervisingOwnerContacts = supervisingOwnerContacts;
    }

    @JsonIgnore
    public String getContactsInformation() {
        return contactsInformation;
    }

    @JsonIgnore
    public void setContactsInformation(String contactsInformation) {
        this.contactsInformation = contactsInformation;
    }

    @JsonIgnore
    public Date getNextFollowupDate() {
        return nextFollowupDate;
    }

    @JsonIgnore
    public void setNextFollowupDate(Date nextFollowupDate) {
        this.nextFollowupDate = nextFollowupDate;
    }

    @JsonIgnore
    public Date getActualFeedbackDate() {
        return actualFeedbackDate;
    }

    @JsonIgnore
    public void setActualFeedbackDate(Date actualFeedbackDate) {
        this.actualFeedbackDate = actualFeedbackDate;
    }

    @JsonIgnore
    public String getFeedbackSituation() {
        return feedbackSituation;
    }

    @JsonIgnore
    public void setFeedbackSituation(String feedbackSituation) {
        this.feedbackSituation = feedbackSituation;
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

    @JsonIgnore
    public String getRemark() {
        return remark;
    }

    @JsonIgnore
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
