package com.hhwy.pm.qqch.sgch.sche.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.pm.qqch.common.domain.CompileEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mls
 * @date 2023-07-31 11:22:46
 * @remark qqch_sche_analyse
 */
public class QqchScheAnalyse extends CompileEntity<QqchScheAnalyse> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：得分
     */
    @JsonProperty
    @Excel(name = "得分")
    private BigDecimal score;
    /**
     * 字段描述：差异性(%)【S曲线差异值】最高分( 不包含)
     */
    @JsonProperty
    @Excel(name = "差异性(%)【S曲线差异值】最高分( 不包含)")
    private BigDecimal diffMaxScore;
    /**
     * 字段描述：差异性(%)【S曲线差异值】最低分( 包含)
     */
    @JsonProperty
    @Excel(name = "差异性(%)【S曲线差异值】最低分( 包含)")
    private BigDecimal diffMinScore;
    /**
     * 字段描述：关键形象进度线路（%）最高分( 不包含)
     */
    @JsonProperty
    @Excel(name = "关键形象进度线路（%）最高分( 不包含)")
    private BigDecimal lineMaxScore;
    /**
     * 字段描述：关键形象进度线路（%）最低分( 包含)
     */
    @JsonProperty
    @Excel(name = "关键形象进度线路（%）最低分( 包含)")
    private BigDecimal lineMinScore;
    /**
     * 字段描述：累计计量产值/累计施工产值 最高分( 不包含)
     */
    @JsonProperty
    @Excel(name = "累计计量产值/累计施工产值 最高分( 不包含)")
    private BigDecimal sumMaxScore;
    /**
     * 字段描述：累计计量产值/累计施工产值 最低分( 包含)
     */
    @JsonProperty
    @Excel(name = "累计计量产值/累计施工产值 最低分( 包含)")
    private BigDecimal sumMinScore;
    /**
     * 字段描述：超合同工期
     */
    @JsonProperty
    @Excel(name = "超合同工期")
    private String contFlag;
    /**
     * 字段描述：（公路/铁路）万美元年平均产值 最高分( 不包含)
     */
    @JsonProperty
    @Excel(name = "（公路/铁路）万美元年平均产值 最高分( 不包含)")
    private BigDecimal roadMaxScore;
    /**
     * 字段描述：（公路/铁路）万美元年平均产值 最低分( 包含)
     */
    @JsonProperty
    @Excel(name = "（公路/铁路）万美元年平均产值 最低分( 包含)")
    private BigDecimal roadMinScore;
    /**
     * 字段描述：(机场/房建）万美元年平均产值 最高分( 不包含)
     */
    @JsonProperty
    @Excel(name = "(机场/房建）万美元年平均产值 最高分( 不包含)")
    private BigDecimal buildMaxScore;
    /**
     * 字段描述：(机场/房建）万美元年平均产值 最低分( 包含)
     */
    @JsonProperty
    @Excel(name = "(机场/房建）万美元年平均产值 最低分( 包含)")
    private BigDecimal buildMinScore;
    /**
     * 字段描述：重要性
     */
    @JsonProperty
    @Excel(name = "重要性")
    private String importance;
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
    public BigDecimal getScore() {
        return score;
    }

    @JsonIgnore
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @JsonIgnore
    public BigDecimal getDiffMaxScore() {
        return diffMaxScore;
    }

    @JsonIgnore
    public void setDiffMaxScore(BigDecimal diffMaxScore) {
        this.diffMaxScore = diffMaxScore;
    }

    @JsonIgnore
    public BigDecimal getDiffMinScore() {
        return diffMinScore;
    }

    @JsonIgnore
    public void setDiffMinScore(BigDecimal diffMinScore) {
        this.diffMinScore = diffMinScore;
    }

    @JsonIgnore
    public BigDecimal getLineMaxScore() {
        return lineMaxScore;
    }

    @JsonIgnore
    public void setLineMaxScore(BigDecimal lineMaxScore) {
        this.lineMaxScore = lineMaxScore;
    }

    @JsonIgnore
    public BigDecimal getLineMinScore() {
        return lineMinScore;
    }

    @JsonIgnore
    public void setLineMinScore(BigDecimal lineMinScore) {
        this.lineMinScore = lineMinScore;
    }

    @JsonIgnore
    public BigDecimal getSumMaxScore() {
        return sumMaxScore;
    }

    @JsonIgnore
    public void setSumMaxScore(BigDecimal sumMaxScore) {
        this.sumMaxScore = sumMaxScore;
    }

    @JsonIgnore
    public BigDecimal getSumMinScore() {
        return sumMinScore;
    }

    @JsonIgnore
    public void setSumMinScore(BigDecimal sumMinScore) {
        this.sumMinScore = sumMinScore;
    }

    @JsonIgnore
    public String getContFlag() {
        return contFlag;
    }

    @JsonIgnore
    public void setContFlag(String contFlag) {
        this.contFlag = contFlag;
    }

    @JsonIgnore
    public BigDecimal getRoadMaxScore() {
        return roadMaxScore;
    }

    @JsonIgnore
    public void setRoadMaxScore(BigDecimal roadMaxScore) {
        this.roadMaxScore = roadMaxScore;
    }

    @JsonIgnore
    public BigDecimal getRoadMinScore() {
        return roadMinScore;
    }

    @JsonIgnore
    public void setRoadMinScore(BigDecimal roadMinScore) {
        this.roadMinScore = roadMinScore;
    }

    @JsonIgnore
    public BigDecimal getBuildMaxScore() {
        return buildMaxScore;
    }

    @JsonIgnore
    public void setBuildMaxScore(BigDecimal buildMaxScore) {
        this.buildMaxScore = buildMaxScore;
    }

    @JsonIgnore
    public BigDecimal getBuildMinScore() {
        return buildMinScore;
    }

    @JsonIgnore
    public void setBuildMinScore(BigDecimal buildMinScore) {
        this.buildMinScore = buildMinScore;
    }

    @JsonIgnore
    public String getImportance() {
        return importance;
    }

    @JsonIgnore
    public void setImportance(String importance) {
        this.importance = importance;
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
