package com.hhwy.sp.techTrain.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @author wll-技术培训管理
 * @date 2023-12-07 18:05:53
 * @remark sgjs_technical_training
 */
public class SgjsTechnicalTraining extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：培训组织单位
     */
    @JsonProperty
    @Excel(name = "培训组织单位")
    private String trainingOrganizationUnit;
    /**
     * 字段描述：培训类型
     */
    @JsonProperty
    @Excel(name = "培训类型")
    private String trainingType;
    /**
     * 字段描述：培训名称
     */
    @JsonProperty
    @Excel(name = "培训名称")
    private String trainingName;
    /**
     * 字段描述：培训日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @Excel(name = "培训日期", dateFormat = "yyyy年MM月dd日")
    private Date trainingDate;
    /**
     * 字段描述：培训讲师id
     */
    @JsonProperty
//    @Excel(name = "培训讲师id")
    private String trainingInstructorId;
    /**
     * 字段描述：培训讲师
     */
    @JsonProperty
    @Excel(name = "培训讲师")
    private String trainingInstructor;
    /**
     * 字段描述：编制日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @Excel(name = "编制日期", dateFormat = "yyyy年MM月dd日")
    private Date compileDate;
    /**
     * 字段描述：培训记录
     */
    @JsonProperty
    @Excel(name = "培训记录")
    private String trainingRecords;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
//    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
//    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
//    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
//    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称,编制人用此字段表示
     */
    @JsonProperty
    @Excel(name = "编制人")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
//    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
//    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据删除时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
//    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
//    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
//    @Excel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
//    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
//    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
//    @Excel(name = "预留字段5")
    private String ptVar5;


    /**
     * 字段描述：删除的id集合，或导出数据的集合
     */
    private List<Long> ids;

    public List getIds() {
        return ids;
    }

    public void setIds(List ids) {
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
    public String getTrainingOrganizationUnit() {
        return trainingOrganizationUnit;
    }

    @JsonIgnore
    public void setTrainingOrganizationUnit(String trainingOrganizationUnit) {
        this.trainingOrganizationUnit = trainingOrganizationUnit;
    }

    @JsonIgnore
    public String getTrainingType() {
        return trainingType;
    }

    @JsonIgnore
    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    @JsonIgnore
    public String getTrainingName() {
        return trainingName;
    }

    @JsonIgnore
    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
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
    public String getTrainingInstructorId() {
        return trainingInstructorId;
    }

    @JsonIgnore
    public void setTrainingInstructorId(String trainingInstructorId) {
        this.trainingInstructorId = trainingInstructorId;
    }

    @JsonIgnore
    public String getTrainingInstructor() {
        return trainingInstructor;
    }

    @JsonIgnore
    public void setTrainingInstructor(String trainingInstructor) {
        this.trainingInstructor = trainingInstructor;
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
    public String getTrainingRecords() {
        return trainingRecords;
    }

    @JsonIgnore
    public void setTrainingRecords(String trainingRecords) {
        this.trainingRecords = trainingRecords;
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
