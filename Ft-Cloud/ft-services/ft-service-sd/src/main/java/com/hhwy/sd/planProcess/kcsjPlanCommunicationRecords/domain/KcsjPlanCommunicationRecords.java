package com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @author wll
 * @date 2023-12-15 10:37:09
 * @remark kcsj_plan_communication_records
 */
public class KcsjPlanCommunicationRecords extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：沟通主题
     */
    @JsonProperty
    @Excel(name = "沟通主题")
    private String communicationTheme;
    /**
     * 字段描述：沟通开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "沟通开始日期", dateFormat = "yyyy-MM-dd")
    private Date communicationStartDate;
    /**
     * 字段描述：沟通结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "沟通结束日期", dateFormat = "yyyy-MM-dd")
    private Date communicationEndDate;
    /**
     * 字段描述：附件名称
     */
    @JsonProperty
    @Excel(name = "附件名称")
    private String annex;
    /**
     * 字段描述：所属区域
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属区域")
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
     * 字段描述：数据创建者名称 编制人用此字段显示
     */
    @JsonProperty
    @Excel(name = "数据创建者名称 编制人用此字段显示")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间  编制时间用此字段显示
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd ")
    @JsonProperty
    @Excel(name = "数据创建系统时间  编制时间用此字段显示", dateFormat = "yyyy-MM-dd HH:mm:ss")
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
     * 字段描述：数据删除标识；0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "数据删除标识；0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段 1
     */
    @JsonProperty
    @Excel(name = "预留字段 1")
    private String ptVar1;
    /**
     * 字段描述：预留字段 2
     */
    @JsonProperty
    @Excel(name = "预留字段 2")
    private String ptVar2;
    /**
     * 字段描述：预留字段 3
     */
    @JsonProperty
    @Excel(name = "预留字段 3")
    private String ptVar3;
    /**
     * 字段描述：预留字段 4
     */
    @JsonProperty
    @Excel(name = "预留字段 4")
    private String ptVar4;
    /**
     * 字段描述：预留字段 5
     */
    @JsonProperty
    @Excel(name = "预留字段 5")
    private String ptVar5;



    /**
     * 字段描述：删除的id集合
     */
    private List<Long> ids;


    /**
     * 标识数据被修改还是新增  0新增，1修改
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
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
    public String getCommunicationTheme() {
        return communicationTheme;
    }

    @JsonIgnore
    public void setCommunicationTheme(String communicationTheme) {
        this.communicationTheme = communicationTheme;
    }

    @JsonIgnore
    public Date getCommunicationStartDate() {
        return communicationStartDate;
    }

    @JsonIgnore
    public void setCommunicationStartDate(Date communicationStartDate) {
        this.communicationStartDate = communicationStartDate;
    }

    @JsonIgnore
    public Date getCommunicationEndDate() {
        return communicationEndDate;
    }

    @JsonIgnore
    public void setCommunicationEndDate(Date communicationEndDate) {
        this.communicationEndDate = communicationEndDate;
    }

    @JsonIgnore
    public String getAnnex() {
        return annex;
    }

    @JsonIgnore
    public void setAnnex(String annex) {
        this.annex = annex;
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
