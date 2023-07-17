package com.hhwy.pm.qqch.qqchWorkPlan.domain;

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
 * @author hwj
 * @date 2023-07-14 17:15:57
 * @remark qqch_work_plan_detail
 */
public class QqchWorkPlanDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
     * 字段描述：主数据id  （qqch_work_plan）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主数据id  （qqch_work_plan）")
    private Long mainId;
    /**
     * 字段描述：显示顺序
     */
    @JsonProperty
    @Excel(name = "显示顺序")
    private Integer orderNum;
    /**
     * 字段描述：工作说明
     */
    @JsonProperty
    @Excel(name = "工作说明")
    private String workExplain;
    /**
     * 字段描述：是否第一阶段编制内容
     */
    @JsonProperty
    @Excel(name = "是否第一阶段编制内容")
    private String isFirst;
    /**
     * 字段描述：第一阶段编制人
     */
    @JsonProperty
    @Excel(name = "第一阶段编制人")
    private String editorFirst;
    /**
     * 字段描述：第一阶段计划完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "第一阶段计划完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTimeFirst;
    /**
     * 字段描述：是否第二阶段编制内容
     */
    @JsonProperty
    @Excel(name = "是否第二阶段编制内容")
    private String isSecond;
    /**
     * 字段描述：第二阶段编制人
     */
    @JsonProperty
    @Excel(name = "第二阶段编制人")
    private String editorSecond;
    /**
     * 字段描述：第二阶段计划完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "第二阶段计划完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTimeSecond;
    /**
     * 字段描述：是否第三阶段编制内容
     */
    @JsonProperty
    @Excel(name = "是否第三阶段编制内容")
    private String isThird;
    /**
     * 字段描述：第三阶段编制人
     */
    @JsonProperty
    @Excel(name = "第三阶段编制人")
    private String editorThird;
    /**
     * 字段描述：第三阶段计划完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "第三阶段计划完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTimeThird;
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
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    @Excel(name = "流程状态（5已完成）")
    private String taskStatus;

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
    public Integer getOrderNum() {
        return orderNum;
    }

    @JsonIgnore
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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
    public String getIsFirst() {
        return isFirst;
    }

    @JsonIgnore
    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }

    @JsonIgnore
    public String getEditorFirst() {
        return editorFirst;
    }

    @JsonIgnore
    public void setEditorFirst(String editorFirst) {
        this.editorFirst = editorFirst;
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
    public String getIsSecond() {
        return isSecond;
    }

    @JsonIgnore
    public void setIsSecond(String isSecond) {
        this.isSecond = isSecond;
    }

    @JsonIgnore
    public String getEditorSecond() {
        return editorSecond;
    }

    @JsonIgnore
    public void setEditorSecond(String editorSecond) {
        this.editorSecond = editorSecond;
    }

    @JsonIgnore
    public Date getFinishTimeSecond() {
        return finishTimeSecond;
    }

    @JsonIgnore
    public void setFinishTimeSecond(Date finishTimeSecond) {
        this.finishTimeSecond = finishTimeSecond;
    }

    @JsonIgnore
    public String getIsThird() {
        return isThird;
    }

    @JsonIgnore
    public void setIsThird(String isThird) {
        this.isThird = isThird;
    }

    @JsonIgnore
    public String getEditorThird() {
        return editorThird;
    }

    @JsonIgnore
    public void setEditorThird(String editorThird) {
        this.editorThird = editorThird;
    }

    @JsonIgnore
    public Date getFinishTimeThird() {
        return finishTimeThird;
    }

    @JsonIgnore
    public void setFinishTimeThird(Date finishTimeThird) {
        this.finishTimeThird = finishTimeThird;
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
}
