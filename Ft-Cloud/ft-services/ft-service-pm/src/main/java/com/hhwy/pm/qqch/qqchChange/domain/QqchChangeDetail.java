package com.hhwy.pm.qqch.qqchChange.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @author wk
 * @date 2023-11-06 17:41:50
 * @remark qqch_change_detail
 */
public class QqchChangeDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public QqchChangeDetail() {
    }

    public QqchChangeDetail(Long mainId) {
        this.mainId = mainId;
    }

    public QqchChangeDetail(String isFirst, Long editorFirst, String editorFirstName, Date finishTimeFirst) {
        this.isFirst = Integer.parseInt(isFirst);
        this.editorFirst = editorFirst;
        this.editorFirstName = editorFirstName;
        this.finishTimeFirst = finishTimeFirst;
    }

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父类id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父类id")
    private Long pid;
    /**
     * 字段描述：主数据id  （qqch_change）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主数据id  （qqch_change）")
    private Long mainId;
    /**
     * 字段描述：策划项id 菜单地址
     */
    @JsonProperty
    @Excel(name = "策划项id 菜单地址")
    private String itemId;
    /**
     * 字段描述：策划项名称
     */
    @JsonProperty
    @Excel(name = "策划项名称")
    private String itemName;
    /**
     * 字段描述：显示顺序
     */
    @JsonProperty
    @Excel(name = "显示顺序")
    private Integer sort;
    /**
     * 字段描述：工作说明
     */
    @JsonProperty
    @Excel(name = "工作说明")
    private String workExplain;
    /**
     * 字段描述：是否编制 0否 1是
     */
    @JsonProperty
    @Excel(name = "是否编制 0否 1是")
    private Integer isFirst;
    /**
     * 字段描述：编制人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "编制人")
    private Long editorFirst;
    /**
     * 字段描述：编制人姓名
     */
    @JsonProperty
    @Excel(name = "编制人姓名")
    private String editorFirstName;
    /**
     * 字段描述：计划完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "计划完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTimeFirst;
    /**
     * 字段描述：实际完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "实际完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date actFinishTimeFirst;
    /**
     * 字段描述：评审人ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "评审人ID")
    private Long reviewerId;
    /**
     * 字段描述：评审人名称
     */
    @JsonProperty
    @Excel(name = "评审人名称")
    private String reviewerName;
    /**
     * 字段描述：评审完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "评审完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reviewFinishTime;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
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
     * 字段描述：是否是叶子节点 1是
     */
    @JsonProperty
    @Excel(name = "是否是叶子节点 1是")
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
     * 字段描述：流程状态
     */
    @JsonProperty
    @Excel(name = "流程状态")
    private String taskStatus;
    /**
     * 字段描述：是否有效0:否,1:是
     */
    @JsonProperty
    @Excel(name = "是否有效0:否,1:是")
    private Integer valid;

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
    public Long getMainId() {
        return mainId;
    }

    @JsonIgnore
    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    @JsonIgnore
    public String getItemId() {
        return itemId;
    }

    @JsonIgnore
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @JsonIgnore
    public String getItemName() {
        return itemName;
    }

    @JsonIgnore
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @JsonIgnore
    public Integer getSort() {
        return sort;
    }

    @JsonIgnore
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @JsonIgnore
    public String getWorkExplain() {
        return workExplain;
    }

    @JsonIgnore
    public void setWorkExplain(String workExplain) {
        this.workExplain = workExplain;
    }

    @JsonIgnore
    public Integer getIsFirst() {
        return isFirst;
    }

    @JsonIgnore
    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    @JsonIgnore
    public Long getEditorFirst() {
        return editorFirst;
    }

    @JsonIgnore
    public void setEditorFirst(Long editorFirst) {
        this.editorFirst = editorFirst;
    }

    @JsonIgnore
    public String getEditorFirstName() {
        return editorFirstName;
    }

    @JsonIgnore
    public void setEditorFirstName(String editorFirstName) {
        this.editorFirstName = editorFirstName;
    }

    @JsonIgnore
    public Date getFinishTimeFirst() {
        return finishTimeFirst;
    }

    @JsonIgnore
    public void setFinishTimeFirst(Date finishTimeFirst) {
        this.finishTimeFirst = finishTimeFirst;
    }

    @JsonIgnore
    public Date getActFinishTimeFirst() {
        return actFinishTimeFirst;
    }

    @JsonIgnore
    public void setActFinishTimeFirst(Date actFinishTimeFirst) {
        this.actFinishTimeFirst = actFinishTimeFirst;
    }

    @JsonIgnore
    public Long getReviewerId() {
        return reviewerId;
    }

    @JsonIgnore
    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    @JsonIgnore
    public String getReviewerName() {
        return reviewerName;
    }

    @JsonIgnore
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    @JsonIgnore
    public Date getReviewFinishTime() {
        return reviewFinishTime;
    }

    @JsonIgnore
    public void setReviewFinishTime(Date reviewFinishTime) {
        this.reviewFinishTime = reviewFinishTime;
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
    public String getTaskStatus() {
        return taskStatus;
    }

    @JsonIgnore
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @JsonIgnore
    public Integer getValid() {
        return valid;
    }

    @JsonIgnore
    public void setValid(Integer valid) {
        this.valid = valid;
    }
}
