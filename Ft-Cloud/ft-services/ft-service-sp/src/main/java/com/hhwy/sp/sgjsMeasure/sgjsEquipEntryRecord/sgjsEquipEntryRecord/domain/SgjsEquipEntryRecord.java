package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain;

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
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:47:00
 * @remark   sgjs_equip_entry_record
 */
public class SgjsEquipEntryRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：编码
     */
    @JsonProperty
    @Excel(name = "编码"    )
    private String materialCode;
    /**
     * 字段描述：设备名称
     */
    @JsonProperty
    @Excel(name = "设备名称"    )
    private String materialName;
    /**
     * 字段描述：型号
     */
    @JsonProperty
    @Excel(name = "型号"    )
    private String materialSpec;
    /**
     * 字段描述：类别名称
     */
    @JsonProperty
    @Excel(name = "类别名称"    )
    private String categoryName;
    /**
     * 字段描述：类别id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "类别id"    )
    private Long categoryId;
    /**
     * 字段描述：要求进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "要求进场日期"    ,dateFormat = "yyyy-MM-dd"  )
    private Date entryDate;

    /**
     * 字段描述：所需数量
     */
    @JsonProperty
    @Excel(name = "所需数量"    )
    private Integer num;
    /**
     * 字段描述：来源
     */
    @JsonProperty
    @Excel(name = "来源"    )
    private String source;
    /**
     * 字段描述：
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：实际进场数量
     */
    @JsonProperty
    @Excel(name = "实际进场数量"    )
    private Integer actualNum;
    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
    @Excel(name = "数据来源 0新增1同步"    )
    private String dataSource;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属区域id"    )
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称"    )
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id"    )
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称"    )
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "部门id"    )
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id"    )
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @Excel(name = "数据创建者名称"    )
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @Excel(name = "数据修改者id"    )
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据修改系统时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @Excel(name = "数据删除者"    )
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据删除系统时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除"    )
    private String delFlag;
    /**
     * 字段描述：预留字段1   项目编码
     */
    @JsonProperty
    @Excel(name = "预留字段1   项目编码"    )
    private String ptVar1;
    /**
     * 字段描述：预留字段2  leaf 是否是叶子节点 0否1是
     */
    @JsonProperty
    @Excel(name = "预留字段2  leaf 是否是叶子节点 0否1是"    )
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3"    )
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @Excel(name = "预留字段4"    )
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @Excel(name = "预留字段5"    )
    private String ptVar5;

    private String entryDateStr;

    private Date entryDateBegin;

    private Date entryDateEnd;

    public Date getEntryDateBegin() {
        return entryDateBegin;
    }

    public void setEntryDateBegin(Date entryDateBegin) {
        this.entryDateBegin = entryDateBegin;
    }

    public Date getEntryDateEnd() {
        return entryDateEnd;
    }

    public void setEntryDateEnd(Date entryDateEnd) {
        this.entryDateEnd = entryDateEnd;
    }

    public String getEntryDateStr() {
        return entryDateStr;
    }

    public void setEntryDateStr(String entryDateStr) {
        this.entryDateStr = entryDateStr;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }
    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }
    @JsonIgnore
    public String getMaterialCode() {
        return materialCode;
    }
    @JsonIgnore
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
    @JsonIgnore
    public String getMaterialName() {
        return materialName;
    }
    @JsonIgnore
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    @JsonIgnore
    public String getMaterialSpec() {
        return materialSpec;
    }
    @JsonIgnore
    public void setMaterialSpec(String materialSpec) {
        this.materialSpec = materialSpec;
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
    public Date getEntryDate() {
        return entryDate;
    }
    @JsonIgnore
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    @JsonIgnore
    public Integer getNum() {
        return num;
    }
    @JsonIgnore
    public void setNum(Integer num) {
        this.num = num;
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
    public String getRemark() {
        return remark;
    }
    @JsonIgnore
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @JsonIgnore
    public Integer getActualNum() {
        return actualNum;
    }
    @JsonIgnore
    public void setActualNum(Integer actualNum) {
        this.actualNum = actualNum;
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
    public Long getRegionId() {
        return regionId;
    }
    @JsonIgnore
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    @JsonIgnore
    public String getRegionName() {
        return regionName;
    }
    @JsonIgnore
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    @JsonIgnore
    public Long getProjectId() {
        return projectId;
    }
    @JsonIgnore
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    @JsonIgnore
    public String getProjectName() {
        return projectName;
    }
    @JsonIgnore
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
