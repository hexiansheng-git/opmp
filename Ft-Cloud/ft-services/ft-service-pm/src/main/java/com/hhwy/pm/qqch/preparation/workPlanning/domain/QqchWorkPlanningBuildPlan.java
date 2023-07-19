package com.hhwy.pm.qqch.preparation.workPlanning.domain;

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
 * @author zq
 * @date 2023-07-19 11:30:27
 * @remark qqch_work_planning_build_plan
 */
public class QqchWorkPlanningBuildPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：名称
     */
    @JsonProperty
    @Excel(name = "名称")
    private String name;
    /**
     * 字段描述：主线桩号
     */
    @JsonProperty
    @Excel(name = "主线桩号")
    private String pileCode;
    /**
     * 字段描述：左/右侧
     */
    @JsonProperty
    @Excel(name = "左/右侧")
    private String leftOrRight;
    /**
     * 字段描述：到主线距离（m）
     */
    @JsonProperty
    @Excel(name = "到主线距离（m）")
    private Double distanceToMain;
    /**
     * 字段描述：占地面积（㎡）
     */
    @JsonProperty
    @Excel(name = "占地面积（㎡）")
    private Double coverAnArea;
    /**
     * 字段描述：建筑面积
     */
    @JsonProperty
    @Excel(name = "建筑面积（㎡）")
    private Double floorSpace;
    /**
     * 字段描述：距离不良地质、爆破区（m）
     */
    @JsonProperty
    @Excel(name = "距离不良地质、爆破区（m）")
    private Double distanceToBoom;
    /**
     * 字段描述：电力供应
     */
    @JsonProperty
    @Excel(name = "电力供应")
    private String powerSupply;
    /**
     * 字段描述：通讯
     */
    @JsonProperty
    @Excel(name = "通讯")
    private String communicate;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
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
    public String getPileCode() {
        return pileCode;
    }

    @JsonIgnore
    public void setPileCode(String pileCode) {
        this.pileCode = pileCode;
    }

    @JsonIgnore
    public String getLeftOrRight() {
        return leftOrRight;
    }

    @JsonIgnore
    public void setLeftOrRight(String leftOrRight) {
        this.leftOrRight = leftOrRight;
    }

    @JsonIgnore
    public Double getDistanceToMain() {
        return distanceToMain;
    }

    @JsonIgnore
    public void setDistanceToMain(Double distanceToMain) {
        this.distanceToMain = distanceToMain;
    }

    @JsonIgnore
    public Double getCoverAnArea() {
        return coverAnArea;
    }

    @JsonIgnore
    public void setCoverAnArea(Double coverAnArea) {
        this.coverAnArea = coverAnArea;
    }

    @JsonIgnore
    public Double getFloorSpace() {
        return floorSpace;
    }

    @JsonIgnore
    public void setFloorSpace(Double floorSpace) {
        this.floorSpace = floorSpace;
    }

    @JsonIgnore
    public Double getDistanceToBoom() {
        return distanceToBoom;
    }

    @JsonIgnore
    public void setDistanceToBoom(Double distanceToBoom) {
        this.distanceToBoom = distanceToBoom;
    }

    @JsonIgnore
    public String getPowerSupply() {
        return powerSupply;
    }

    @JsonIgnore
    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }

    @JsonIgnore
    public String getCommunicate() {
        return communicate;
    }

    @JsonIgnore
    public void setCommunicate(String communicate) {
        this.communicate = communicate;
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
}
