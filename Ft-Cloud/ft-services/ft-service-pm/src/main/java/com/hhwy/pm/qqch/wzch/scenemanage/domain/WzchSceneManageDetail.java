package com.hhwy.pm.qqch.wzch.scenemanage.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.Date;

/**
 * 现场物资管控策划详情(WzchSceneManageDetail)实体类
 *
 * @author makejava
 * @since 2022-12-18 11:24:43
 */
public class WzchSceneManageDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 530583458564080688L;
    /**
     * id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    /**
     * 资金策划id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sceneManageId;
    /**
     * 资金策划事项
     */
    @Excel(name = "管控内容")
    private String manageContent;
    /**
     * 要求内容
     */
    @Excel(name = "管控目标")
    private String manageTarget;
    /**
     * 实施措施
     */
    @Excel(name = "执行措施")
    private String implMeasure;
    /**
     * 备注/描述
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 文件id
     */
    private String fileGroupId;
    /**
     * 项目id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSceneManageId() {
        return sceneManageId;
    }

    public void setSceneManageId(Long sceneManageId) {
        this.sceneManageId = sceneManageId;
    }

    public String getManageContent() {
        return manageContent;
    }

    public void setManageContent(String manageContent) {
        this.manageContent = manageContent;
    }

    public String getManageTarget() {
        return manageTarget;
    }

    public void setManageTarget(String manageTarget) {
        this.manageTarget = manageTarget;
    }

    public String getImplMeasure() {
        return implMeasure;
    }

    public void setImplMeasure(String implMeasure) {
        this.implMeasure = implMeasure;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFileGroupId() {
        return fileGroupId;
    }

    public void setFileGroupId(String fileGroupId) {
        this.fileGroupId = fileGroupId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

}

