package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.validation.ValidationGroups;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @author wll
 * @date 2024-02-18 14:39:34
 * @remark 勘察设计-设计工程量管理-主材清单
 */
@Valid
public class KcsjMaterialsList extends BaseEntity {
    private static final long serialVersionUID = 1L;
    //删除Id的集合
    private List<Long> delIdList;
    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id ")
    private Long id;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private String version;
    /**
     * 字段描述：清单名称
     */
    @JsonProperty
    @Excel(name = "清单名称")
    @NotBlank(message = "请填写清单名称",groups ={ValidationGroups.Save.class})
    private String listName;
    /**
     * 字段描述：清单附件
     */
    @JsonProperty
    @Excel(name = "清单附件")
    private String fileGroupId;
    /**
     * 字段描述：附件名称 中间用|分隔
     */
    @JsonProperty
    @Excel(name = "附件名称 中间用|分隔")
    private String fileName;
    /**
     * 字段描述：提交日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @Excel(name = "提交日期", dateFormat = "yyyy年MM月dd日")
    private Date submissionDate;

    /**
     * 字段描述：提交人
     */
    @JsonProperty
    @Excel(name = "提交人")
    private String submissionPerson;
    /**
     * 字段描述：是否有效 1有效 0无效
     */
    @JsonProperty
    @Excel(name = "是否有效 1有效 0无效")
    private String valid;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：工程类型 字典项 t_engineering_type
     */
    @JsonProperty
    @Excel(name = "工程类型 字典项 t_engineering_type")
    private String engineeringType;
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
    /**
     * 字段描述：数据来源  0新增1同步
     */
    @JsonProperty
    @Excel(name = "数据来源  0新增1同步")
    private String dataSource;


    //清单明细
    @Valid
    private List<KcsjMaterialsListDetail> detailList;
    //提交日期搜索字符串
    private String submitDateStr;
    //提交日期-开始
    private Date submitDateBegin;
    //提交日期-结束
    private Date submitDateEnd;


    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getVersion() {
        return version;
    }

    @JsonIgnore
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonIgnore
    public String getListName() {
        return listName;
    }

    @JsonIgnore
    public void setListName(String listName) {
        this.listName = listName;
    }

    @JsonIgnore
    public String getFileGroupId() {
        return fileGroupId;
    }

    @JsonIgnore
    public void setFileGroupId(String fileGroupId) {
        this.fileGroupId = fileGroupId;
    }

    @JsonIgnore
    public String getFileName() {
        return fileName;
    }

    @JsonIgnore
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonIgnore
    public Date getSubmissionDate() {
        return submissionDate;
    }

    @JsonIgnore
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    @JsonIgnore
    public String getSubmissionPerson() {
        return submissionPerson;
    }

    @JsonIgnore
    public void setSubmissionPerson(String submissionPerson) {
        this.submissionPerson = submissionPerson;
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
    public String getRemark() {
        return remark;
    }

    @JsonIgnore
    public void setRemark(String remark) {
        this.remark = remark;
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
    public String getEngineeringType() {
        return engineeringType;
    }

    @JsonIgnore
    public void setEngineeringType(String engineeringType) {
        this.engineeringType = engineeringType;
    }

    public List<Long> getDelIdList() {
        return delIdList;
    }

    public void setDelIdList(List<Long> delIdList) {
        this.delIdList = delIdList;
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

    @JsonIgnore
    public String getDataSource() {
        return dataSource;
    }

    @JsonIgnore
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<KcsjMaterialsListDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<KcsjMaterialsListDetail> detailList) {
        this.detailList = detailList;
    }

    public String getSubmitDateStr() {
        return submitDateStr;
    }

    public void setSubmitDateStr(String submitDateStr) {
        this.submitDateStr = submitDateStr;
    }

    public Date getSubmitDateBegin() {
        return submitDateBegin;
    }

    public void setSubmitDateBegin(Date submitDateBegin) {
        this.submitDateBegin = submitDateBegin;
    }

    public Date getSubmitDateEnd() {
        return submitDateEnd;
    }

    public void setSubmitDateEnd(Date submitDateEnd) {
        this.submitDateEnd = submitDateEnd;
    }





}
