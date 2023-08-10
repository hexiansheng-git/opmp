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
import lombok.Data;

/**
 * @author zq
 * @date 2023-07-19 11:49:17
 * @remark qqch_work_planing_arrange
 */
@Data
public class QqchWorkPlaningArrange extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：障碍物编号
     */
    @JsonProperty
    @Excel(name = "障碍物编号")
    private String obstacleNumber;
    /**
     * 字段描述：障碍物类型
     */
    @JsonProperty
    @Excel(name = "障碍物类型")
    private String obstacleType;
    /**
     * 字段描述：对应主线桩号
     */
    @JsonProperty
    @Excel(name = "对应主线桩号")
    private String pileCode;
    /**
     * 字段描述：正常宽度
     */
    @JsonProperty
    @Excel(name = "正常宽度")
    private String normalWidth;
    /**
     * 字段描述：正常水位
     */
    @JsonProperty
    @Excel(name = "正常水位")
    private String normalWaterLevel;
    /**
     * 字段描述：汛期宽度
     */
    @JsonProperty
    @Excel(name = "汛期宽度")
    private String floodSeasonWidth;
    /**
     * 字段描述：汛期水位
     */
    @JsonProperty
    @Excel(name = "汛期水位")
    private String floodSeasonWaterLevel;
    /**
     * 字段描述：计划跨越方式（埋管、钢便桥（贝雷片、工字钢）
     */
    @JsonProperty
    @Excel(name = "计划跨越方式（埋管、钢便桥（贝雷片、工字钢）")
    private String planCrossWay;
    /**
     * 字段描述：计划便桥长度
     */
    @JsonProperty
    @Excel(name = "计划便桥长度")
    private String planBridgeLength;
    /**
     * 字段描述：简述材料用量
     */
    @JsonProperty
    @Excel(name = "简述材料用量")
    private String materialUsage;
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
    public String getObstacleNumber() {
        return obstacleNumber;
    }

    @JsonIgnore
    public void setObstacleNumber(String obstacleNumber) {
        this.obstacleNumber = obstacleNumber;
    }

    @JsonIgnore
    public String getObstacleType() {
        return obstacleType;
    }

    @JsonIgnore
    public void setObstacleType(String obstacleType) {
        this.obstacleType = obstacleType;
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
    public String getNormalWidth() {
        return normalWidth;
    }

    @JsonIgnore
    public void setNormalWidth(String normalWidth) {
        this.normalWidth = normalWidth;
    }

    @JsonIgnore
    public String getNormalWaterLevel() {
        return normalWaterLevel;
    }

    @JsonIgnore
    public void setNormalWaterLevel(String normalWaterLevel) {
        this.normalWaterLevel = normalWaterLevel;
    }

    @JsonIgnore
    public String getFloodSeasonWidth() {
        return floodSeasonWidth;
    }

    @JsonIgnore
    public void setFloodSeasonWidth(String floodSeasonWidth) {
        this.floodSeasonWidth = floodSeasonWidth;
    }

    @JsonIgnore
    public String getFloodSeasonWaterLevel() {
        return floodSeasonWaterLevel;
    }

    @JsonIgnore
    public void setFloodSeasonWaterLevel(String floodSeasonWaterLevel) {
        this.floodSeasonWaterLevel = floodSeasonWaterLevel;
    }

    @JsonIgnore
    public String getPlanCrossWay() {
        return planCrossWay;
    }

    @JsonIgnore
    public void setPlanCrossWay(String planCrossWay) {
        this.planCrossWay = planCrossWay;
    }

    @JsonIgnore
    public String getPlanBridgeLength() {
        return planBridgeLength;
    }

    @JsonIgnore
    public void setPlanBridgeLength(String planBridgeLength) {
        this.planBridgeLength = planBridgeLength;
    }

    @JsonIgnore
    public String getMaterialUsage() {
        return materialUsage;
    }

    @JsonIgnore
    public void setMaterialUsage(String materialUsage) {
        this.materialUsage = materialUsage;
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
