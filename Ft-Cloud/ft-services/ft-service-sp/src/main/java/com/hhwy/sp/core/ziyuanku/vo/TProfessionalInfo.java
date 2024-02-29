package com.hhwy.sp.core.ziyuanku.vo;

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
 * @author lcf 资源系统专家信息对接
 *
 * @date 2024-02-22 16:45:30
 * @remark t_professional_info
 */
public class TProfessionalInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：专家姓名
     */
    @JsonProperty
    private String name;
    /**
     * 字段描述：专家编号
     */
    @JsonProperty
    @Excel(name = "专家编号")
    private String numNo;
    /**
     * 字段描述：年龄
     */
    @JsonProperty
    @Excel(name = "年龄")
    private Integer age;
    /**
     * 字段描述：工作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "工作时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date workTime;
    /**
     * 字段描述：现工作岗位
     */
    @JsonProperty
    @Excel(name = "现工作岗位")
    private String job;
    /**
     * 字段描述：职务
     */
    @JsonProperty
    @Excel(name = "职务")
    private String duty;
    /**
     * 字段描述：职务序列
     */
    @JsonProperty
    @Excel(name = "职务序列")
    private String dutyNo;
    /**
     * 字段描述：职称级别
     */
    @JsonProperty
    @Excel(name = "职称级别")
    private String dutyLevel;
    /**
     * 字段描述：资格、资质
     */
    @JsonProperty
    @Excel(name = "资格、资质")
    private String credential;
    /**
     * 字段描述：技术领域
     */
    @JsonProperty
    @Excel(name = "技术领域")
    private String technology;
    /**
     * 字段描述：研究方向
     */
    @JsonProperty
    @Excel(name = "研究方向")
    private String researchDirection;
    /**
     * 字段描述：联系电话
     */
    @JsonProperty
    @Excel(name = "联系电话")
    private String phone;
    /**
     * 字段描述：专家类型0内部技术人员1外部技术专家
     */
    @JsonProperty
    @Excel(name = "专家类型0内部技术人员1外部技术专家")
    private Integer type;
    /**
     * 字段描述：现工作单位
     */
    @JsonProperty
    @Excel(name = "现工作单位")
    private String unit;
    /**
     * 字段描述：专家分级
     */
    @JsonProperty
    @Excel(name = "专家分级")
    private String expertClassification;
    /**
     * 字段描述：归属领域
     */
    @JsonProperty
    @Excel(name = "归属领域")
    private String belongTo;
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
     * 字段描述：预留字段1  调整时返回生效版本id
     */
    @JsonProperty
    @Excel(name = "预留字段1  调整时返回生效版本id")
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
     * 字段描述：
     */
    @JsonProperty
    private String ptVar6;
    /**
     * 字段描述：
     */
    @JsonProperty
    private String ptVar7;
    /**
     * 字段描述：
     */
    @JsonProperty
    private String ptVar8;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
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
    public String getNumNo() {
        return numNo;
    }

    @JsonIgnore
    public void setNumNo(String numNo) {
        this.numNo = numNo;
    }

    @JsonIgnore
    public Integer getAge() {
        return age;
    }

    @JsonIgnore
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonIgnore
    public Date getWorkTime() {
        return workTime;
    }

    @JsonIgnore
    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    @JsonIgnore
    public String getJob() {
        return job;
    }

    @JsonIgnore
    public void setJob(String job) {
        this.job = job;
    }

    @JsonIgnore
    public String getDuty() {
        return duty;
    }

    @JsonIgnore
    public void setDuty(String duty) {
        this.duty = duty;
    }

    @JsonIgnore
    public String getDutyNo() {
        return dutyNo;
    }

    @JsonIgnore
    public void setDutyNo(String dutyNo) {
        this.dutyNo = dutyNo;
    }

    @JsonIgnore
    public String getDutyLevel() {
        return dutyLevel;
    }

    @JsonIgnore
    public void setDutyLevel(String dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    @JsonIgnore
    public String getCredential() {
        return credential;
    }

    @JsonIgnore
    public void setCredential(String credential) {
        this.credential = credential;
    }

    @JsonIgnore
    public String getTechnology() {
        return technology;
    }

    @JsonIgnore
    public void setTechnology(String technology) {
        this.technology = technology;
    }

    @JsonIgnore
    public String getResearchDirection() {
        return researchDirection;
    }

    @JsonIgnore
    public void setResearchDirection(String researchDirection) {
        this.researchDirection = researchDirection;
    }

    @JsonIgnore
    public String getPhone() {
        return phone;
    }

    @JsonIgnore
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonIgnore
    public Integer getType() {
        return type;
    }

    @JsonIgnore
    public void setType(Integer type) {
        this.type = type;
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
    public String getExpertClassification() {
        return expertClassification;
    }

    @JsonIgnore
    public void setExpertClassification(String expertClassification) {
        this.expertClassification = expertClassification;
    }

    @JsonIgnore
    public String getBelongTo() {
        return belongTo;
    }

    @JsonIgnore
    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
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
    public String getPtVar6() {
        return ptVar6;
    }

    @JsonIgnore
    public void setPtVar6(String ptVar6) {
        this.ptVar6 = ptVar6;
    }

    @JsonIgnore
    public String getPtVar7() {
        return ptVar7;
    }

    @JsonIgnore
    public void setPtVar7(String ptVar7) {
        this.ptVar7 = ptVar7;
    }

    @JsonIgnore
    public String getPtVar8() {
        return ptVar8;
    }

    @JsonIgnore
    public void setPtVar8(String ptVar8) {
        this.ptVar8 = ptVar8;
    }
}
