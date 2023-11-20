package com.hhwy.sp.techOrg.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @author lcf
 * @date 2023-11-20 10:41:18
 * @remark   sgjs_measure_position
 */
public class SgjsMeasurePosition extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：岗位
     */
    @JsonProperty
    @Excel(name = "岗位"    )
    private String postName;
    /**
     * 字段描述：具体工作分工
     */
    @JsonProperty
    @Excel(name = "具体工作分工"    )
    private String divideWork;
    /**
     * 字段描述：配置测量工（中方）
     */
    @JsonProperty
    @Excel(name = "配置测量工（中方）"    )
    private String configWork;
    /**
     * 字段描述：配置测量工（属地化）
     */
    @JsonProperty
    @Excel(name = "配置测量工（属地化）"    )
    private String configLocal;
    /**
     * 字段描述：
     */
    @JsonProperty
    private String source;
    /**
     * 字段描述：计划进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date planDate;

    private String planDateStr;

    /**
     * 字段描述：实际进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date actualDate;

    private String actualDateStr;
    /**
     * 字段描述：数据来源
     */
    @JsonProperty
    @Excel(name = "数据来源 "    )
    private String dataSource;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属区域id"    )
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称"    )
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id"    )
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称"    )
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "部门id"    )
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id"    )
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @Excel(name = "数据创建者名称"    )
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @Excel(name = "数据修改者id"    )
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据修改系统时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @Excel(name = "数据删除者"    )
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据删除系统时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除"    )
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    @Excel(name = "预留字段1"    )
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "预留字段2"    )
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3"    )
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @Excel(name = "预留字段4"    )
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @Excel(name = "预留字段5"    )
    private String ptVar5;

    public String getPlanDateStr() {
        return planDateStr;
    }

    public void setPlanDateStr(String planDateStr) {
        this.planDateStr = planDateStr;
    }

    public String getActualDateStr() {
        return actualDateStr;
    }

    public void setActualDateStr(String actualDateStr) {
        this.actualDateStr = actualDateStr;
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
    public String getPostName() {
        return postName;
    }
    @JsonIgnore
    public void setPostName(String postName) {
        this.postName = postName;
    }
    @JsonIgnore
    public String getDivideWork() {
        return divideWork;
    }
    @JsonIgnore
    public void setDivideWork(String divideWork) {
        this.divideWork = divideWork;
    }
    @JsonIgnore
    public String getConfigWork() {
        return configWork;
    }
    @JsonIgnore
    public void setConfigWork(String configWork) {
        this.configWork = configWork;
    }
    @JsonIgnore
    public String getConfigLocal() {
        return configLocal;
    }
    @JsonIgnore
    public void setConfigLocal(String configLocal) {
        this.configLocal = configLocal;
    }
    @JsonIgnore
    public String getSource() {
        return source;
    }
    @JsonIgnore
    public void setSource(String source) {
        this.source = source;
    }
    @JsonIgnore
    public Date getPlanDate() {
        return planDate;
    }
    @JsonIgnore
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }
    @JsonIgnore
    public Date getActualDate() {
        return actualDate;
    }
    @JsonIgnore
    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
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
}
