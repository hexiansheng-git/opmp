package com.hhwy.pm.qqch.wzch.specialproject.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专项物资策划对象 wzch_special_project
 * 
 * @author mls
 * @date 2022-12-11
 */
public class WzchSpecialProject extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    public WzchSpecialProject() {
    }
    public WzchSpecialProject(BigDecimal version) {
        super.setVersion(version);
    }

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String specialProjectCode;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 所属区域id */
    @Excel(name = "所属区域id")

    private String region;// 搜索用
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String regionName;

    /** 版本号 */
    @Excel(name = "版本号")
    private BigDecimal versionCode;

    /** 限价方案说明 */
    @Excel(name = "限价方案说明")
    private String limitPriceDesc;

    /** 文件id */
    @Excel(name = "文件id")
    private String fileGroupId;

    /** 项目id */
    @Excel(name = "项目id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 部门id */
    @Excel(name = "部门id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 数据创建者id */
    @Excel(name = "数据创建者id")
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "数据创建者名称")
    private String createUserName;

    /** 数据修改者id */
    @Excel(name = "数据修改者id")
    private String updateUser;

    /** 数据修改者名称 */
    @Excel(name = "数据修改者名称")
    private String updateUserName;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标记: 0-未删除 1-已删除 */
    private String delFlag;

    /** 是否有效 1-有效 0-失效 */
    @Excel(name = "是否有效 1-有效 0-失效")
    private String valid;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String ptVar3;

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private String ptVar4;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setSpecialProjectCode(String specialProjectCode){
        this.specialProjectCode = specialProjectCode;
    }

    public String getSpecialProjectCode(){
        return specialProjectCode;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public void setRegionId(Long regionId){
        this.regionId = regionId;
    }

    public Long getRegionId(){
        return regionId;
    }
    public void setRegionName(String regionName){
        this.regionName = regionName;
    }

    public String getRegionName(){
        return regionName;
    }
    public void setVersionCode(BigDecimal versionCode){
        this.versionCode = versionCode;
    }

    public BigDecimal getVersionCode(){
        return versionCode;
    }
    public void setLimitPriceDesc(String limitPriceDesc){
        this.limitPriceDesc = limitPriceDesc;
    }

    public String getLimitPriceDesc(){
        return limitPriceDesc;
    }
    public void setFileGroupId(String fileGroupId){
        this.fileGroupId = fileGroupId;
    }

    public String getFileGroupId(){
        return fileGroupId;
    }
    public void setProjectId(Long projectId){
        this.projectId = projectId;
    }

    public Long getProjectId(){
        return projectId;
    }
    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public String getProjectName(){
        return projectName;
    }
    public void setDeptId(Long deptId){
        this.deptId = deptId;
    }

    public Long getDeptId(){
        return deptId;
    }
    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }

    public String getCreateUser(){
        return createUser;
    }
    public void setCreateUserName(String createUserName){
        this.createUserName = createUserName;
    }

    public String getCreateUserName(){
        return createUserName;
    }
    public void setUpdateUser(String updateUser){
        this.updateUser = updateUser;
    }

    public String getUpdateUser(){
        return updateUser;
    }
    public void setUpdateUserName(String updateUserName){
        this.updateUserName = updateUserName;
    }

    public String getUpdateUserName(){
        return updateUserName;
    }
    public void setDelUser(String delUser){
        this.delUser = delUser;
    }

    public String getDelUser(){
        return delUser;
    }
    public void setDelTime(Date delTime){
        this.delTime = delTime;
    }

    public Date getDelTime(){
        return delTime;
    }
    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }
    public void setValid(String valid){
        this.valid = valid;
    }

    public String getValid(){
        return valid;
    }
    public void setPtVar1(String ptVar1){
        this.ptVar1 = ptVar1;
    }

    public String getPtVar1(){
        return ptVar1;
    }
    public void setPtVar2(String ptVar2){
        this.ptVar2 = ptVar2;
    }

    public String getPtVar2(){
        return ptVar2;
    }
    public void setPtVar3(String ptVar3){
        this.ptVar3 = ptVar3;
    }

    public String getPtVar3(){
        return ptVar3;
    }
    public void setPtVar4(String ptVar4){
        this.ptVar4 = ptVar4;
    }

    public String getPtVar4(){
        return ptVar4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("specialProjectCode", getSpecialProjectCode())
            .append("title", getTitle())
            .append("regionId", getRegionId())
            .append("regionName", getRegionName())
            .append("versionCode", getVersionCode())
            .append("limitPriceDesc", getLimitPriceDesc())
            .append("fileGroupId", getFileGroupId())
            .append("remark", getRemark())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("deptId", getDeptId())
            .append("createUser", getCreateUser())
            .append("createUserName", getCreateUserName())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateUserName", getUpdateUserName())
            .append("updateTime", getUpdateTime())
            .append("delUser", getDelUser())
            .append("delTime", getDelTime())
            .append("delFlag", getDelFlag())
            .append("valid", getValid())
            .append("ptVar1", getPtVar1())
            .append("ptVar2", getPtVar2())
            .append("ptVar3", getPtVar3())
            .append("ptVar4", getPtVar4())
            .toString();
    }
}
