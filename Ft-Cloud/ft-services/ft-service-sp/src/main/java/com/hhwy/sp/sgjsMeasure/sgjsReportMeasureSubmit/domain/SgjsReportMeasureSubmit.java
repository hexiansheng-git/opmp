package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;
import com.hhwy.utils.tree.TreeNode;
import java.util.Date;
import java.math.BigDecimal;
import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zmh
 * @date 2023-12-08 16:19:52
 * @remark
 */
@Data
public class SgjsReportMeasureSubmit extends TreeNode<SgjsReportMeasureSubmit> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "父级id")
    private Long pid;
    /**
     * 字段描述：报告名称
     */
    @JsonProperty
    @Excel(name = "报告名称")
    private String reportName;
    /**
     * 字段描述：所属wbs编号
     */
    @JsonProperty
    //@Excel(name = "所属wbs编号")
    private String wbsCode;
    /**
     * 字段描述：所属wbs名称
     */
    @JsonProperty
    @Excel(name = "所属wbs名称")
    private String wbsName;
    /**
     * 字段描述：所属WBS的id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "所属WBS的id")
    private Long wbsId;
    /**
     * 字段描述：所属wbs祖级ID
     */
    @JsonProperty
    //@Excel(name = "所属wbs祖级ID")
    private String wbsAncestors;
    /**
     * 字段描述：计划提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划提交日期", dateFormat = "yyyy-MM-dd")
    private Date planStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private String planStartDateStr;

    /**
     * 字段描述：实际提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际提交日期", dateFormat = "yyyy-MM-dd")
    private Date realStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private String realStartDateStr;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private String realEndDateStr;


    /**
     * 字段描述：编制人
     */
    @JsonProperty
    @Excel(name = "编制人")
    private String aurhorizedPersonnel;
    /**
     * 字段描述：提交人
     */
    @JsonProperty
    @Excel(name = "提交人")
    private String submitter;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    //@Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
    //@Excel(name = "数据来源 0新增1同步")
    private String dataSource;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    //@Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    //@Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    //@Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    //@Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    //@Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1   项目编码
     */
    @JsonProperty
    //@Excel(name = "预留字段1   项目编码")
    private String ptVar1;
    /**
     * 字段描述：预留字段2  leaf 是否是叶子节点 0否1是
     */
    @JsonProperty
    //@Excel(name = "预留字段2  leaf 是否是叶子节点 0否1是")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    //@Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    //@Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    //@Excel(name = "预留字段5")
    private String ptVar5;

    //导入查询
    private List<Long> ids;

    //新增标识
    private String isAdd;


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
    public String getReportName() {
        return reportName;
    }

    @JsonIgnore
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    @JsonIgnore
    public String getWbsCode() {
        return wbsCode;
    }

    @JsonIgnore
    public void setWbsCode(String wbsCode) {
        this.wbsCode = wbsCode;
    }

    @JsonIgnore
    public String getWbsName() {
        return wbsName;
    }

    @JsonIgnore
    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    @JsonIgnore
    public Long getWbsId() {
        return wbsId;
    }

    @JsonIgnore
    public void setWbsId(Long wbsId) {
        this.wbsId = wbsId;
    }

    @JsonIgnore
    public String getWbsAncestors() {
        return wbsAncestors;
    }

    @JsonIgnore
    public void setWbsAncestors(String wbsAncestors) {
        this.wbsAncestors = wbsAncestors;
    }

    @JsonIgnore
    public Date getPlanStartDate() {
        return planStartDate;
    }

    @JsonIgnore
    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    @JsonIgnore
    public Date getRealStartDate() {
        return realStartDate;
    }

    @JsonIgnore
    public void setRealStartDate(Date realStartDate) {
        this.realStartDate = realStartDate;
    }

    @JsonIgnore
    public String getAurhorizedPersonnel() {
        return aurhorizedPersonnel;
    }

    @JsonIgnore
    public void setAurhorizedPersonnel(String aurhorizedPersonnel) {
        this.aurhorizedPersonnel = aurhorizedPersonnel;
    }

    @JsonIgnore
    public String getSubmitter() {
        return submitter;
    }

    @JsonIgnore
    public void setSubmitter(String submitter) {
        this.submitter = submitter;
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
