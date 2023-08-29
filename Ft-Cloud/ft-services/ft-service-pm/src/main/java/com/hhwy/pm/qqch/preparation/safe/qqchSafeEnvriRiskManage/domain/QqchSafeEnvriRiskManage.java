package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.domain;

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
import com.hhwy.utils.validation.ValidationGroups;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zq 8.8.3
 * @date 2023-08-14 14:04:07
 * @remark qqch_safe_envri_risk_manage
 */
public class QqchSafeEnvriRiskManage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：措施项
     */
    @JsonProperty
    @NotBlank(message = "措施项不能为空",groups = {ValidationGroups.Save.class})
    private String measureOption;
    /**
     * 字段描述：执行人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "执行人id")
    @NotNull(message = "执行人id",groups = {ValidationGroups.Save.class})
    private Long executorPersonId;
    /**
     * 字段描述：执行人(姓名-部门)
     */
    @JsonProperty
    @Excel(name = "执行人(姓名-部门)")
    @NotBlank(message = "执行人",groups = {ValidationGroups.Save.class})
    private String executorPersonName;
    /**
     * 字段描述：协作部门ids
     */
    @JsonProperty
    @Excel(name = "协作部门ids")
    @NotBlank(message = "协作部门ids",groups = {ValidationGroups.Save.class})
    private String departmentIds;
    /**
     * 字段描述：协作部门
     */
    @JsonProperty
    @Excel(name = "协作部门")
    @NotBlank(message = "协作部门",groups = {ValidationGroups.Save.class})
    private String departmentNames;
    /**
     * 字段描述：注意事项
     */
    @JsonProperty
    @Excel(name = "注意事项")
    private String attenOption;
    /**
     * 字段描述：使用表格模板id
     */
    @JsonProperty
    @Excel(name = "使用表格模板id")
    private String excelTempleteId;
    /**
     * 字段描述：使用表格模板名称
     */
    @JsonProperty
    @Excel(name = "使用表格模板名称")
    private String excelTempleteName;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 0无效 1有效
     */
    @JsonProperty
    @Excel(name = "是否有效 0无效 1有效")
    private String valid;
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
     * 字段描述：
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
    public String getMeasureOption() {
        return measureOption;
    }

    @JsonIgnore
    public void setMeasureOption(String measureOption) {
        this.measureOption = measureOption;
    }

    @JsonIgnore
    public Long getExecutorPersonId() {
        return executorPersonId;
    }

    @JsonIgnore
    public void setExecutorPersonId(Long executorPersonId) {
        this.executorPersonId = executorPersonId;
    }

    @JsonIgnore
    public String getExecutorPersonName() {
        return executorPersonName;
    }

    @JsonIgnore
    public void setExecutorPersonName(String executorPersonName) {
        this.executorPersonName = executorPersonName;
    }

    @JsonIgnore
    public String getDepartmentIds() {
        return departmentIds;
    }

    @JsonIgnore
    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
    }

    @JsonIgnore
    public String getDepartmentNames() {
        return departmentNames;
    }

    @JsonIgnore
    public void setDepartmentNames(String departmentNames) {
        this.departmentNames = departmentNames;
    }

    @JsonIgnore
    public String getAttenOption() {
        return attenOption;
    }

    @JsonIgnore
    public void setAttenOption(String attenOption) {
        this.attenOption = attenOption;
    }

    @JsonIgnore
    public String getExcelTempleteId() {
        return excelTempleteId;
    }

    @JsonIgnore
    public void setExcelTempleteId(String excelTempleteId) {
        this.excelTempleteId = excelTempleteId;
    }

    @JsonIgnore
    public String getExcelTempleteName() {
        return excelTempleteName;
    }

    @JsonIgnore
    public void setExcelTempleteName(String excelTempleteName) {
        this.excelTempleteName = excelTempleteName;
    }

    @JsonIgnore
    public BigDecimal getVersion() {
        return version;
    }

    @JsonIgnore
    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    @JsonIgnore
    public String getValid() {
        return valid;
    }

    @JsonIgnore
    public void setValid(String valid) {
        this.valid = valid;
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
    public Long getDeptId() {
        return deptId;
    }

    @JsonIgnore
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
