package com.hhwy.sd.planProcess.kcsjPlanProcess.domain;

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
 * @author cjh
 * @date 2023-12-18 11:13:27
 * @remark kcsj_plan_process
 */
public class KcsjPlanProcess extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：作业代码
     */
    @JsonProperty
    @Excel(name = "作业代码")
    private String workCode;
    /**
     * 字段描述：作业名称
     */
    @JsonProperty
    @Excel(name = "作业名称")
    private String workName;
    /**
     * 字段描述：工作内容
     */
    @JsonProperty
    @Excel(name = "工作内容")
    private String workContent;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：工作量
     */
    @JsonProperty
    @Excel(name = "工作量")
    private BigDecimal quantity;
    /**
     * 字段描述：计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划开始时间", dateFormat = "yyyy-MM-dd")
    private Date planStartDate;
    /**
     * 字段描述：计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划结束时间", dateFormat = "yyyy-MM-dd")
    private Date planEndDate;
    /**
     * 字段描述：计划工期(天)
     */
    @JsonProperty
    @Excel(name = "计划工期(天)")
    private Integer planDuration;
    /**
     * 字段描述：实际开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际开始时间", dateFormat = "yyyy-MM-dd")
    private Date actStartDate;
    /**
     * 字段描述：实际结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际结束时间", dateFormat = "yyyy-MM-dd")
    private Date actEndDate;
    /**
     * 字段描述：实际工期(天)
     */
    @JsonProperty
    @Excel(name = "实际工期(天)")
    private Integer actDuration;
    /**
     * 字段描述：数据来源
     */
    @JsonProperty
    @Excel(name = "数据来源 ")
    private String dataSource;
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
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父id")
    private Long pid;
    /**
     * 字段描述：是否叶子节点
     */
    @JsonProperty
    @Excel(name = "是否叶子节点")
    private String leaf;
    /**
     * 字段描述：排序号
     */
    @JsonProperty
    @Excel(name = "排序号")
    private Integer sort;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getWorkCode() {
        return workCode;
    }

    @JsonIgnore
    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    @JsonIgnore
    public String getWorkName() {
        return workName;
    }

    @JsonIgnore
    public void setWorkName(String workName) {
        this.workName = workName;
    }

    @JsonIgnore
    public String getWorkContent() {
        return workContent;
    }

    @JsonIgnore
    public void setWorkContent(String workContent) {
        this.workContent = workContent;
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
    public BigDecimal getQuantity() {
        return quantity;
    }

    @JsonIgnore
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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
    public Integer getPlanDuration() {
        return planDuration;
    }

    @JsonIgnore
    public void setPlanDuration(Integer planDuration) {
        this.planDuration = planDuration;
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
    public Integer getActDuration() {
        return actDuration;
    }

    @JsonIgnore
    public void setActDuration(Integer actDuration) {
        this.actDuration = actDuration;
    }

    @JsonIgnore
    public String getDataSource() {
        return dataSource;
    }

    @JsonIgnore
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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
    public Long getPid() {
        return pid;
    }

    @JsonIgnore
    public void setPid(Long pid) {
        this.pid = pid;
    }

    @JsonIgnore
    public String getLeaf() {
        return leaf;
    }

    @JsonIgnore
    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    @JsonIgnore
    public Integer getSort() {
        return sort;
    }

    @JsonIgnore
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
