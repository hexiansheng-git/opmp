package com.hhwy.pm.qyzs.speciallistOrg.qyzsSpeciallistLibrary.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author fsd
 * @date 2023-11-30 17:34:54
 * @remark qyzs_speciallist_library
 */
@Data
public class QyzsSpeciallistLibrary extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：编号
     */
    @JsonProperty
    private String speciallistCode;
    /**
     * 字段描述：姓名
     */
    @JsonProperty
    private String speciallistName;
    /**
     * 字段描述：所属单位
     */
    @JsonProperty
    private String department;
    /**
     * 字段描述：业务领域
     */
    @JsonProperty
    private String businessAreas;
    /**
     * 字段描述：产品
     */
    @JsonProperty
    private String products;
    /**
     * 字段描述：专业
     */
    @JsonProperty
    private String specialty;
    /**
     * 字段描述：数据来源
     */
    @JsonProperty
    private String dataSource;
    /**
     * 字段描述：编辑人
     */
    @JsonProperty
    private Integer personId;
    /**
     * 字段描述：编辑人姓名
     */
    @JsonProperty
    private String personName;
    /**
     * 字段描述：编辑时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date editTime;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    private String remark;
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
     * 字段描述：创建时间
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

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getSpeciallistCode() {
        return speciallistCode;
    }

    @JsonIgnore
    public void setSpeciallistCode(String speciallistCode) {
        this.speciallistCode = speciallistCode;
    }

    @JsonIgnore
    public String getSpeciallistName() {
        return speciallistName;
    }

    @JsonIgnore
    public void setSpeciallistName(String speciallistName) {
        this.speciallistName = speciallistName;
    }

    @JsonIgnore
    public String getDepartment() {
        return department;
    }

    @JsonIgnore
    public void setDepartment(String department) {
        this.department = department;
    }

    @JsonIgnore
    public String getBusinessAreas() {
        return businessAreas;
    }

    @JsonIgnore
    public void setBusinessAreas(String businessAreas) {
        this.businessAreas = businessAreas;
    }

    @JsonIgnore
    public String getProducts() {
        return products;
    }

    @JsonIgnore
    public void setProducts(String products) {
        this.products = products;
    }

    @JsonIgnore
    public String getSpecialty() {
        return specialty;
    }

    @JsonIgnore
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
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
    public Integer getPersonId() {
        return personId;
    }

    @JsonIgnore
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @JsonIgnore
    public String getPersonName() {
        return personName;
    }

    @JsonIgnore
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @JsonIgnore
    public Date getEditTime() {
        return editTime;
    }

    @JsonIgnore
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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
}
