package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;

import java.util.Date;
import java.util.List;

/**
 * 测量管理--测试设备进场记录
 *
 * @author lcf
 * @date 2023-12-08 10:49:36
 * @remark   sgjs_equip_entry_record_info
 */
public class SgjsEquipEntryRecordInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 主表id
     */
    private Long recordId;
    /**
     * 字段描述：管理编码
     */
    @JsonProperty
    @Excel(name = "管理编码"    )
    private String manageCode;
    /**
     * 字段描述：类别
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long categoryId;
    /**
     * 字段描述：
     */
    @JsonProperty
    @Excel(name = "类别"    )
    private String categoryCode;
    /**
     * 字段描述：类别名称
     */
    @JsonProperty
    @Excel(name = "类别名称"    )
    private String categoryName;

    /**
     * 字段描述：设备名称
     */
    @JsonProperty
    @Excel(name = "设备名称"    )
    private String materialName;
    /**
     * 字段描述：厂商
     */
    @JsonProperty
    @Excel(name = "厂商"    )
    private String manufacturer;
    /**
     * 字段描述：型号
     */
    @JsonProperty
    @Excel(name = "型号"    )
    private String materialSpec;
    /**
     * 字段描述：主机功率（KW）
     */
    @JsonProperty
    @Excel(name = "主机功率（KW）"    )
    private String power;
    /**
     * 字段描述：主机系列号
     */
    @JsonProperty
    @Excel(name = "主机系列号"    )
    private String serialNum;

    /**
     * 字段描述：底盘系列号
     */
    @JsonProperty
    @Excel(name = "底盘系列号"    )
    private String bottomNo;
    /**
     * 字段描述：实际进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际进场日期"    ,dateFormat = "yyyy-MM-dd"  )
    private Date entryDate;

    private String entryDateStr;

    private Date entryDateBegin;

    private Date entryDateEnd;
    /**
     * 字段描述：实际退场时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际退场时间"    ,dateFormat = "yyyy-MM-dd"  )
    private Date exitDate;
    /**
     * 字段描述：最近自检校验日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "最近自检校验日期"    ,dateFormat = "yyyy-MM-dd"  )
    private Date checkDate;
    /**
     * 字段描述：
     */
    @JsonProperty
    private String source;
    /**
     * 字段描述：当前状态
     */
    @JsonProperty
    @Excel(name = "当前状态"    )
    private String currentState;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注"    )
    private String remark;
    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
    private String dataSource;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    private String delFlag;
    /**
     * 字段描述：预留字段1   项目编码
     */
    @JsonProperty
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
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    private String ptVar5;
    //导出idList
    private List<String> exportIdList;

    public List<String> getExportIdList() {
        return exportIdList;
    }

    public void setExportIdList(List<String> exportIdList) {
        this.exportIdList = exportIdList;
    }

    public String getEntryDateStr() {
        return entryDateStr;
    }

    public void setEntryDateStr(String entryDateStr) {
        this.entryDateStr = entryDateStr;
    }

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

    //子表数据
    private List<SgjsEquipEntryRecordInfoDetail> detailList;

    public List<SgjsEquipEntryRecordInfoDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<SgjsEquipEntryRecordInfoDetail> detailList) {
        this.detailList = detailList;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
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
    public String getManageCode() {
        return manageCode;
    }
    @JsonIgnore
    public void setManageCode(String manageCode) {
        this.manageCode = manageCode;
    }
    @JsonIgnore
    public String getCategoryCode() {
        return categoryCode;
    }
    @JsonIgnore
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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
    public String getManufacturer() {
        return manufacturer;
    }
    @JsonIgnore
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    @JsonIgnore
    public String getSerialNum() {
        return serialNum;
    }
    @JsonIgnore
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
    @JsonIgnore
    public String getPower() {
        return power;
    }
    @JsonIgnore
    public void setPower(String power) {
        this.power = power;
    }
    @JsonIgnore
    public String getBottomNo() {
        return bottomNo;
    }
    @JsonIgnore
    public void setBottomNo(String bottomNo) {
        this.bottomNo = bottomNo;
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
    public Date getExitDate() {
        return exitDate;
    }
    @JsonIgnore
    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }
    @JsonIgnore
    public Date getCheckDate() {
        return checkDate;
    }
    @JsonIgnore
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
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
    public String getCurrentState() {
        return currentState;
    }
    @JsonIgnore
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
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
