package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain;

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
 * @author ldd
 * @date 2023-08-04 15:10:09
 * @remark qqch_weight_engineering_control
 * <p>
 * 9.4.2 重难点工程管控
 */
public class QqchWeightEngineeringControl extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "清单id")
    private Long listId;
    /**
     * 字段描述：重难点工程名称
     */
    @JsonProperty
    @Excel(name = "重难点工程名称")
    private String name;
    /**
     * 字段描述：所属WBS的id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属WBS的id")
    private Long wbsId;
    /**
     * 字段描述：所属WBS名称
     */
    @JsonProperty
    @Excel(name = "所属WBS名称")
    private String wbsName;
    /**
     * 字段描述：计划最早开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划最早开工日期", dateFormat = "yyyy-MM-dd")
    private Date plannStartDate;
    /**
     * 字段描述：作业班组
     */
    @JsonProperty
    @Excel(name = "作业班组")
    private String workGroup;
    /**
     * 字段描述：质量管控要点
     */
    @JsonProperty
    @Excel(name = "质量管控要点")
    private String controlKeyPoint;
    /**
     * 字段描述：检测次数
     */
    @JsonProperty
    @Excel(name = "检测次数")
    private BigDecimal detectionsNumber;
    /**
     * 字段描述：施工id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "施工id")
    private Long constructionId;
    /**
     * 字段描述：技术方案计划编制时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "技术方案计划编制时间", dateFormat = "yyyy-MM-dd")
    private Date preparationTime;
    /**
     * 字段描述：计划交底日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划交底日期", dateFormat = "yyyy-MM-dd")
    private Date disclosureDate;
    /**
     * 字段描述：计划培训日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划培训日期", dateFormat = "yyyy-MM-dd")
    private Date trainingDate;
    /**
     * 字段描述：重点检查项
     */
    @JsonProperty
    @Excel(name = "重点检查项")
    private String keyInspectionItems;
    /**
     * 字段描述：现场负责人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "现场负责人id")
    private Long directorId;
    /**
     * 字段描述：现场负责人名称
     */
    @JsonProperty
    @Excel(name = "现场负责人名称")
    private String directorName;
    /**
     * 字段描述：技术负责人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "技术负责人id")
    private Long technicalId;
    /**
     * 字段描述：技术负责人名称
     */
    @JsonProperty
    @Excel(name = "技术负责人名称")
    private String technicalName;
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
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    @Excel(name = "流程状态（5已完成）")
    private String taskStatus;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    @Excel(name = "是否有效 1-有效 0-失效")
    private String valid;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Long getListId() {
        return listId;
    }

    @JsonIgnore
    public void setListId(Long listId) {
        this.listId = listId;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Long getWbsId() {
        return wbsId;
    }

    @JsonIgnore
    public void setWbsId(Long wbsId) {
        this.wbsId = wbsId;
    }

    @JsonIgnore
    public String getWbsName() {
        return wbsName;
    }

    @JsonIgnore
    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    @JsonIgnore
    public Date getPlannStartDate() {
        return plannStartDate;
    }

    @JsonIgnore
    public void setPlannStartDate(Date plannStartDate) {
        this.plannStartDate = plannStartDate;
    }

    @JsonIgnore
    public String getWorkGroup() {
        return workGroup;
    }

    @JsonIgnore
    public void setWorkGroup(String workGroup) {
        this.workGroup = workGroup;
    }

    @JsonIgnore
    public String getControlKeyPoint() {
        return controlKeyPoint;
    }

    @JsonIgnore
    public void setControlKeyPoint(String controlKeyPoint) {
        this.controlKeyPoint = controlKeyPoint;
    }

    @JsonIgnore
    public BigDecimal getDetectionsNumber() {
        return detectionsNumber;
    }

    @JsonIgnore
    public void setDetectionsNumber(BigDecimal detectionsNumber) {
        this.detectionsNumber = detectionsNumber;
    }

    @JsonIgnore
    public Long getConstructionId() {
        return constructionId;
    }

    @JsonIgnore
    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    @JsonIgnore
    public Date getPreparationTime() {
        return preparationTime;
    }

    @JsonIgnore
    public void setPreparationTime(Date preparationTime) {
        this.preparationTime = preparationTime;
    }

    @JsonIgnore
    public Date getDisclosureDate() {
        return disclosureDate;
    }

    @JsonIgnore
    public void setDisclosureDate(Date disclosureDate) {
        this.disclosureDate = disclosureDate;
    }

    @JsonIgnore
    public Date getTrainingDate() {
        return trainingDate;
    }

    @JsonIgnore
    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    @JsonIgnore
    public String getKeyInspectionItems() {
        return keyInspectionItems;
    }

    @JsonIgnore
    public void setKeyInspectionItems(String keyInspectionItems) {
        this.keyInspectionItems = keyInspectionItems;
    }

    @JsonIgnore
    public Long getDirectorId() {
        return directorId;
    }

    @JsonIgnore
    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }

    @JsonIgnore
    public String getDirectorName() {
        return directorName;
    }

    @JsonIgnore
    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    @JsonIgnore
    public Long getTechnicalId() {
        return technicalId;
    }

    @JsonIgnore
    public void setTechnicalId(Long technicalId) {
        this.technicalId = technicalId;
    }

    @JsonIgnore
    public String getTechnicalName() {
        return technicalName;
    }

    @JsonIgnore
    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
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

    @JsonIgnore
    public String getTaskStatus() {
        return taskStatus;
    }

    @JsonIgnore
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
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
}
