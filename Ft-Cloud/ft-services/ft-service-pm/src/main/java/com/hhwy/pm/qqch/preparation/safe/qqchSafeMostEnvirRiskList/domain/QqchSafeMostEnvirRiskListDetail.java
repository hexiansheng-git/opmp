package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain;

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

/**
 * @author zq
 * @date 2023-08-14 14:04:04
 * @remark qqch_safe_most_envir_risk_list_detail
 */
public class QqchSafeMostEnvirRiskListDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long infoId;
    /**
     * 字段描述：污染类型
     */
    @JsonProperty
    @Excel(name = "污染类型")
    @NotBlank(message = "污染类型不能为空",groups = {ValidationGroups.Save.class})
    private String pollutionType;
    /**
     * 字段描述：风险描述
     */
    @JsonProperty
    @Excel(name = "风险描述")
    @NotBlank(message = "风险描述不能为空",groups = {ValidationGroups.Save.class})
    private String riskDes;
    /**
     * 字段描述：项目涉及wbs
     */
    @JsonProperty
    @Excel(name = "项目涉及wbs")
    private String projectAboutWbs;
    /**
     * 字段描述：管控措施
     */
    @JsonProperty
    @Excel(name = "管控措施")
    @NotBlank(message = "管控措施不能为空",groups = {ValidationGroups.Save.class})
    private String controlMeasures;
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

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Long getInfoId() {
        return infoId;
    }

    @JsonIgnore
    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    @JsonIgnore
    public String getPollutionType() {
        return pollutionType;
    }

    @JsonIgnore
    public void setPollutionType(String pollutionType) {
        this.pollutionType = pollutionType;
    }

    @JsonIgnore
    public String getRiskDes() {
        return riskDes;
    }

    @JsonIgnore
    public void setRiskDes(String riskDes) {
        this.riskDes = riskDes;
    }

    @JsonIgnore
    public String getProjectAboutWbs() {
        return projectAboutWbs;
    }

    @JsonIgnore
    public void setProjectAboutWbs(String projectAboutWbs) {
        this.projectAboutWbs = projectAboutWbs;
    }

    @JsonIgnore
    public String getControlMeasures() {
        return controlMeasures;
    }

    @JsonIgnore
    public void setControlMeasures(String controlMeasures) {
        this.controlMeasures = controlMeasures;
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
}
