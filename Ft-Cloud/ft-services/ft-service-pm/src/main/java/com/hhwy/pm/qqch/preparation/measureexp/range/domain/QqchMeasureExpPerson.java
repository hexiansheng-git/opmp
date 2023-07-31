package com.hhwy.pm.qqch.preparation.measureexp.range.domain;


import java.util.Date;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.utils.excel.FtExcel;

/**
 * @author mls
 * @date 2023-07-25 18:01:30
 * @remark qqch_measure_exp_person
 */
public class QqchMeasureExpPerson extends CompileEntity<QqchMeasureExpPerson> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @FtExcel(name = "主键id")
    private Long id;
    /**
     * 字段描述：数据类型:1-测量管理计划 2-实验管理计划
     */
    @JsonProperty
    @FtExcel(name = "数据类型:1-测量管理计划 2-实验管理计划")
    private String dataType;
    /**
     * 字段描述：岗位编码
     */
    @JsonProperty
    @FtExcel(name = "岗位编码")
    private String positionCode;
    /**
     * 字段描述：岗位名称
     */
    @JsonProperty
    @FtExcel(name = "岗位名称")
    private String positionName;
    /**
     * 字段描述：工作分工
     */
    @JsonProperty
    @FtExcel(name = "工作分工")
    private String workDesc;
    /**
     * 字段描述：配置测量工（中方）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @FtExcel(name = "配置测量工（中方）")
    private Long cnNum;
    /**
     * 字段描述：配置测量工（属地化）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @FtExcel(name = "配置测量工（属地化）")
    private Long localNum;
    /**
     * 字段描述：来源
     */
    @JsonProperty
    @FtExcel(name = "来源")
    private String source;
    /**
     * 字段描述：计划进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划进场日期", dateFormat = "yyyy-MM-dd")
    private Date planInDate;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @FtExcel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    @FtExcel(name = "是否有效 1-有效 0-失效")
    private String valid;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @FtExcel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @FtExcel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @FtExcel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @FtExcel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @FtExcel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @FtExcel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    @FtExcel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @FtExcel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @FtExcel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @FtExcel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @FtExcel(name = "预留字段5")
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
    public String getDataType() {
        return dataType;
    }

    @JsonIgnore
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @JsonIgnore
    public String getPositionCode() {
        return positionCode;
    }

    @JsonIgnore
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    @JsonIgnore
    public String getPositionName() {
        return positionName;
    }

    @JsonIgnore
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @JsonIgnore
    public String getWorkDesc() {
        return workDesc;
    }

    @JsonIgnore
    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    @JsonIgnore
    public Long getCnNum() {
        return cnNum;
    }

    @JsonIgnore
    public void setCnNum(Long cnNum) {
        this.cnNum = cnNum;
    }

    @JsonIgnore
    public Long getLocalNum() {
        return localNum;
    }

    @JsonIgnore
    public void setLocalNum(Long localNum) {
        this.localNum = localNum;
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
    public Date getPlanInDate() {
        return planInDate;
    }

    @JsonIgnore
    public void setPlanInDate(Date planInDate) {
        this.planInDate = planInDate;
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
