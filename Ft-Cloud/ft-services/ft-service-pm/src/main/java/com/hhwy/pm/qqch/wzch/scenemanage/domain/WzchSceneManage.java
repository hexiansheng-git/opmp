package com.hhwy.pm.qqch.wzch.scenemanage.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.common.MyPrepareBaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 现场物资管控策划(WzchSceneManage)实体类
 *
 * @author makejava
 * @since 2022-12-18 11:24:38
 */
public class WzchSceneManage extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 892553093604171543L;

    public WzchSceneManage() {
    }
    public WzchSceneManage(BigDecimal version) {
        super.setVersion(version);
    }

    /**
     * id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    /**
     * 单据编号
     */
    private String sceneCode;
    /**
     * 标题
     */
    private String title;
    /**
     * 所属区域id
     */

    private String region;// 搜索用
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;
    /**
     * 地域名
     */
    private String regionName;
    /**
     * 版本号
     */
    private Double versionCode;
    /**
     * 限价方案说明
     */
    private String limitPriceDesc;
    /**
     * 文件id
     */
    private String fileGroupId;
    /**
     * 备注/描述
     */
    private String remark;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
//    private String projectName;
    /**
     * 部门id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;
    /**
     * 数据创建者id
     */
    private String createUser;
    /**
     * 数据创建者名称
     */
    private String createUserName;
    /**
     * 数据创建系统时间
     */
    private Date createTime;
    /**
     * 数据修改者id
     */
    private String updateUser;
    /**
     * 数据修改者名称
     */
    private String updateUserName;
    /**
     * 数据修改系统时间
     */
    private Date updateTime;
    /**
     * 数据删除者
     */
    private String delUser;
    /**
     * 数据删除系统时间
     */
    private Date delTime;
    /**
     * 删除标记: 0-未删除 1-已删除
     */
    private String delFlag;
    /**
     * 预留字段1
     */
    private String ptVar1;
    /**
     * 预留字段2
     */
    private String ptVar2;
    /**
     * 预留字段3
     */
    private String ptVar3;
    /**
     * 预留字段4
     */
    private String ptVar4;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSceneCode() {
        return sceneCode;
    }

    public void setSceneCode(String sceneCode) {
        this.sceneCode = sceneCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Double getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Double versionCode) {
        this.versionCode = versionCode;
    }

    public String getLimitPriceDesc() {
        return limitPriceDesc;
    }

    public void setLimitPriceDesc(String limitPriceDesc) {
        this.limitPriceDesc = limitPriceDesc;
    }

    public String getFileGroupId() {
        return fileGroupId;
    }

    public void setFileGroupId(String fileGroupId) {
        this.fileGroupId = fileGroupId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelUser() {
        return delUser;
    }

    public void setDelUser(String delUser) {
        this.delUser = delUser;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPtVar1() {
        return ptVar1;
    }

    public void setPtVar1(String ptVar1) {
        this.ptVar1 = ptVar1;
    }

    public String getPtVar2() {
        return ptVar2;
    }

    public void setPtVar2(String ptVar2) {
        this.ptVar2 = ptVar2;
    }

    public String getPtVar3() {
        return ptVar3;
    }

    public void setPtVar3(String ptVar3) {
        this.ptVar3 = ptVar3;
    }

    public String getPtVar4() {
        return ptVar4;
    }

    public void setPtVar4(String ptVar4) {
        this.ptVar4 = ptVar4;
    }

}

