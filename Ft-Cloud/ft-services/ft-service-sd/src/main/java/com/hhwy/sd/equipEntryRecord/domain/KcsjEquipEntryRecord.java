package com.hhwy.sd.equipEntryRecord.domain;

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
 * @author zmh
 * @date 2023-12-14 11:08:08
 * @remark kcsj_equip_entry_record
 */
public class KcsjEquipEntryRecord extends BaseEntity {

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
    private Long pid;
    /**
     * 字段描述：所属班组ID
     */
    @JsonProperty
    @Excel(name = "所属班组ID")
    private String teamId;
    /**
     * 字段描述：所属班组名称
     */
    @JsonProperty
    @Excel(name = "所属班组名称")
    private String teamName;
    /**
     * 字段描述：班组编码
     */
    @JsonProperty
    @Excel(name = "班组编码")
    private String teamNumber;
    /**
     * 字段描述：类别名称
     */
    @JsonProperty
    @Excel(name = "类别名称")
    private String categoryName;
    /**
     * 字段描述：类别id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "类别id")
    private Long categoryId;
    /**
     * 字段描述：设备名称
     */
    @JsonProperty
    @Excel(name = "设备名称")
    private String equipName;
    /**
     * 字段描述：规格型号
     */
    @JsonProperty
    @Excel(name = "规格型号")
    private String equipSpec;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String equipUnit;
    /**
     * 字段描述：计划进场数量
     */
    @JsonProperty
    @Excel(name = "计划进场数量")
    private Integer planNum;
    /**
     * 字段描述：实际进场数量
     */
    @JsonProperty
    @Excel(name = "实际进场数量")
    private Integer practicalNum;
    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
    @Excel(name = "数据来源 0新增1同步")
    private String dataSource;
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
     * 字段描述：预留字段1   项目编码
     */
    @JsonProperty
    @Excel(name = "预留字段1   项目编码")
    private String ptVar1;
    /**
     * 字段描述：预留字段2  leaf 是否是叶子节点 0否1是
     */
    @JsonProperty
    @Excel(name = "预留字段2  leaf 是否是叶子节点 0否1是")
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
    public Long getPid() {
        return pid;
    }

    @JsonIgnore
    public void setPid(Long pid) {
        this.pid = pid;
    }

    @JsonIgnore
    public String getTeamId() {
        return teamId;
    }

    @JsonIgnore
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @JsonIgnore
    public String getTeamName() {
        return teamName;
    }

    @JsonIgnore
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @JsonIgnore
    public String getTeamNumber() {
        return teamNumber;
    }

    @JsonIgnore
    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    @JsonIgnore
    public String getCategoryName() {
        return categoryName;
    }

    @JsonIgnore
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @JsonIgnore
    public Long getCategoryId() {
        return categoryId;
    }

    @JsonIgnore
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @JsonIgnore
    public String getEquipName() {
        return equipName;
    }

    @JsonIgnore
    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    @JsonIgnore
    public String getEquipSpec() {
        return equipSpec;
    }

    @JsonIgnore
    public void setEquipSpec(String equipSpec) {
        this.equipSpec = equipSpec;
    }

    @JsonIgnore
    public String getEquipUnit() {
        return equipUnit;
    }

    @JsonIgnore
    public void setEquipUnit(String equipUnit) {
        this.equipUnit = equipUnit;
    }

    @JsonIgnore
    public Integer getPlanNum() {
        return planNum;
    }

    @JsonIgnore
    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
    }

    @JsonIgnore
    public Integer getPracticalNum() {
        return practicalNum;
    }

    @JsonIgnore
    public void setPracticalNum(Integer practicalNum) {
        this.practicalNum = practicalNum;
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
