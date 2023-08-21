package com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark qqch_tax_global
 */
public class QqchTaxGlobal extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：父级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父级id")
    private Long pid;
    /**
     * 字段描述：年份
     */
    @JsonProperty
    @Excel(name = "年份")
    private Integer year;
    /**
     * 字段描述：资金项
     */
    @JsonProperty
    @Excel(name = "资金项")
    private String itemName;
    /**
     * 字段描述：预算金额-当地币
     */
    @JsonProperty
    @Excel(name = "预算金额-当地币")
    private BigDecimal budgetLocalAmt;
    /**
     * 字段描述：预算金额-美元
     */
    @JsonProperty
    @Excel(name = "预算金额-美元")
    private BigDecimal budgetUsdAmt;
    /**
     * 字段描述：项目直接收支-当地币
     */
    @JsonProperty
    @Excel(name = "项目直接收支-当地币")
    private BigDecimal prjLocalAmt;
    /**
     * 字段描述：项目直接收支-当地币折美元汇率
     */
    @JsonProperty
    @Excel(name = "项目直接收支-当地币折美元汇率")
    private BigDecimal prjLocalRate;
    /**
     * 字段描述：区域总部/国家办事处/总项目部代收支-当地币
     */
    @JsonProperty
    @Excel(name = "区域总部/国家办事处/总项目部代收支-当地币")
    private BigDecimal regionLocalAmt;
    /**
     * 字段描述：区域总部/国家办事处/总项目部代收支-当地币折美元汇率
     */
    @JsonProperty
    @Excel(name = "区域总部/国家办事处/总项目部代收支-当地币折美元汇率")
    private BigDecimal regionLocalRate;
    /**
     * 字段描述：海外事业部代收支-美元
     */
    @JsonProperty
    @Excel(name = "海外事业部代收支-美元")
    private BigDecimal overseasUsdAmt;
    /**
     * 字段描述：海外事业部代收支-人民币
     */
    @JsonProperty
    @Excel(name = "海外事业部代收支-人民币")
    private BigDecimal overseasCnyAmt;
    /**
     * 字段描述：海外事业部代收支-人民币折美元汇率
     */
    @JsonProperty
    @Excel(name = "海外事业部代收支-人民币折美元汇率")
    private BigDecimal overseasCnyRate;
    /**
     * 字段描述：合计-当地币种
     */
    @JsonProperty
    @Excel(name = "合计-当地币种")
    private BigDecimal sumLocalAmt;
    /**
     * 字段描述：合计-当地币折美元汇率
     */
    @JsonProperty
    @Excel(name = "合计-当地币折美元汇率")
    private BigDecimal sumLocalRate;
    /**
     * 字段描述：合计-美元
     */
    @JsonProperty
    @Excel(name = "合计-美元")
    private BigDecimal sumUsdAmt;
    /**
     * 字段描述：合计-人民币折美元汇率
     */
    @JsonProperty
    @Excel(name = "合计-人民币折美元汇率")
    private BigDecimal sumCnyRate;
    /**
     * 字段描述：附件
     */
    @JsonProperty
    @Excel(name = "附件")
    private String fileGroupId;
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
     * 字段描述：用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "用户id")
    private Long userId;
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
     * 字段描述：版本号
     */
    @JsonProperty
    @Excel(name = "版本号")
    private BigDecimal version;
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
     * 字段描述：序号
     */
    @JsonProperty
    @Excel(name = "序号")
    private Integer sort;

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
    public Integer getYear() {
        return year;
    }

    @JsonIgnore
    public void setYear(Integer year) {
        this.year = year;
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
    public BigDecimal getBudgetLocalAmt() {
        return budgetLocalAmt;
    }

    @JsonIgnore
    public void setBudgetLocalAmt(BigDecimal budgetLocalAmt) {
        this.budgetLocalAmt = budgetLocalAmt;
    }

    @JsonIgnore
    public BigDecimal getBudgetUsdAmt() {
        return budgetUsdAmt;
    }

    @JsonIgnore
    public void setBudgetUsdAmt(BigDecimal budgetUsdAmt) {
        this.budgetUsdAmt = budgetUsdAmt;
    }

    @JsonIgnore
    public BigDecimal getPrjLocalAmt() {
        return prjLocalAmt;
    }

    @JsonIgnore
    public void setPrjLocalAmt(BigDecimal prjLocalAmt) {
        this.prjLocalAmt = prjLocalAmt;
    }

    @JsonIgnore
    public BigDecimal getPrjLocalRate() {
        return prjLocalRate;
    }

    @JsonIgnore
    public void setPrjLocalRate(BigDecimal prjLocalRate) {
        this.prjLocalRate = prjLocalRate;
    }

    @JsonIgnore
    public BigDecimal getRegionLocalAmt() {
        return regionLocalAmt;
    }

    @JsonIgnore
    public void setRegionLocalAmt(BigDecimal regionLocalAmt) {
        this.regionLocalAmt = regionLocalAmt;
    }

    @JsonIgnore
    public BigDecimal getRegionLocalRate() {
        return regionLocalRate;
    }

    @JsonIgnore
    public void setRegionLocalRate(BigDecimal regionLocalRate) {
        this.regionLocalRate = regionLocalRate;
    }

    @JsonIgnore
    public BigDecimal getOverseasUsdAmt() {
        return overseasUsdAmt;
    }

    @JsonIgnore
    public void setOverseasUsdAmt(BigDecimal overseasUsdAmt) {
        this.overseasUsdAmt = overseasUsdAmt;
    }

    @JsonIgnore
    public BigDecimal getOverseasCnyAmt() {
        return overseasCnyAmt;
    }

    @JsonIgnore
    public void setOverseasCnyAmt(BigDecimal overseasCnyAmt) {
        this.overseasCnyAmt = overseasCnyAmt;
    }

    @JsonIgnore
    public BigDecimal getOverseasCnyRate() {
        return overseasCnyRate;
    }

    @JsonIgnore
    public void setOverseasCnyRate(BigDecimal overseasCnyRate) {
        this.overseasCnyRate = overseasCnyRate;
    }

    @JsonIgnore
    public BigDecimal getSumLocalAmt() {
        return sumLocalAmt;
    }

    @JsonIgnore
    public void setSumLocalAmt(BigDecimal sumLocalAmt) {
        this.sumLocalAmt = sumLocalAmt;
    }

    @JsonIgnore
    public BigDecimal getSumLocalRate() {
        return sumLocalRate;
    }

    @JsonIgnore
    public void setSumLocalRate(BigDecimal sumLocalRate) {
        this.sumLocalRate = sumLocalRate;
    }

    @JsonIgnore
    public BigDecimal getSumUsdAmt() {
        return sumUsdAmt;
    }

    @JsonIgnore
    public void setSumUsdAmt(BigDecimal sumUsdAmt) {
        this.sumUsdAmt = sumUsdAmt;
    }

    @JsonIgnore
    public BigDecimal getSumCnyRate() {
        return sumCnyRate;
    }

    @JsonIgnore
    public void setSumCnyRate(BigDecimal sumCnyRate) {
        this.sumCnyRate = sumCnyRate;
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
    public Long getUserId() {
        return userId;
    }

    @JsonIgnore
    public void setUserId(Long userId) {
        this.userId = userId;
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
    public BigDecimal getVersion() {
        return version;
    }

    @JsonIgnore
    public void setVersion(BigDecimal version) {
        this.version = version;
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
    public Integer getSort() {
        return sort;
    }

    @JsonIgnore
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
