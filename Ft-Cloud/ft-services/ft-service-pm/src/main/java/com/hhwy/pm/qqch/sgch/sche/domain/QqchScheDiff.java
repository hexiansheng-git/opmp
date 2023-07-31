package com.hhwy.pm.qqch.sgch.sche.domain;

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
 * @author mls
 * @date 2023-07-31 11:22:48
 * @remark qqch_sche_diff
 */
public class QqchScheDiff extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：最高分( 不包含)
     */
    @JsonProperty
    @Excel(name = "最高分( 不包含)")
    private BigDecimal maxScore;
    /**
     * 字段描述：最低分( 包含)
     */
    @JsonProperty
    @Excel(name = "最低分( 包含)")
    private BigDecimal minScore;
    /**
     * 字段描述：得分
     */
    @JsonProperty
    @Excel(name = "得分")
    private BigDecimal score;
    /**
     * 字段描述：预警等级 warn_level
     */
    @JsonProperty
    @Excel(name = "预警等级 warn_level")
    private String warnLevel;
    /**
     * 字段描述：风险等级 risk_level
     */
    @JsonProperty
    @Excel(name = "风险等级 risk_level")
    private String riskLevel;
    /**
     * 字段描述：进度评价 sche_eva
     */
    @JsonProperty
    @Excel(name = "进度评价 sche_eva")
    private String scheEva;
    /**
     * 字段描述：响应层级
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "响应层级")
    private Long respOrg;
    /**
     * 字段描述：风险控制策略
     */
    @JsonProperty
    @Excel(name = "风险控制策略")
    private String ctrlPolicy;
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
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
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

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public BigDecimal getMaxScore() {
        return maxScore;
    }

    @JsonIgnore
    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }

    @JsonIgnore
    public BigDecimal getMinScore() {
        return minScore;
    }

    @JsonIgnore
    public void setMinScore(BigDecimal minScore) {
        this.minScore = minScore;
    }

    @JsonIgnore
    public BigDecimal getScore() {
        return score;
    }

    @JsonIgnore
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @JsonIgnore
    public String getWarnLevel() {
        return warnLevel;
    }

    @JsonIgnore
    public void setWarnLevel(String warnLevel) {
        this.warnLevel = warnLevel;
    }

    @JsonIgnore
    public String getRiskLevel() {
        return riskLevel;
    }

    @JsonIgnore
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @JsonIgnore
    public String getScheEva() {
        return scheEva;
    }

    @JsonIgnore
    public void setScheEva(String scheEva) {
        this.scheEva = scheEva;
    }

    @JsonIgnore
    public Long getRespOrg() {
        return respOrg;
    }

    @JsonIgnore
    public void setRespOrg(Long respOrg) {
        this.respOrg = respOrg;
    }

    @JsonIgnore
    public String getCtrlPolicy() {
        return ctrlPolicy;
    }

    @JsonIgnore
    public void setCtrlPolicy(String ctrlPolicy) {
        this.ctrlPolicy = ctrlPolicy;
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
    public String getRemark() {
        return remark;
    }

    @JsonIgnore
    public void setRemark(String remark) {
        this.remark = remark;
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
