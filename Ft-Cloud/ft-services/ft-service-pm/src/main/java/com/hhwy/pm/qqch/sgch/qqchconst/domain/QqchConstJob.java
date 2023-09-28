package com.hhwy.pm.qqch.sgch.qqchconst.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.utils.JsonUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mls
 * @date 2023-08-03 16:08:17
 * @remark qqch_const_job
 */
public class QqchConstJob extends CompileEntity<QqchConstJob> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父id")
    private Long pid;
    /**
     * 字段描述：主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主表id")
    private Long masterId;
    /**
     * 字段描述：清单编码
     */
    @JsonProperty
    @Excel(name = "清单编码")
    private String itemCode;
    /**
     * 字段描述：清单名称
     */
    @JsonProperty
    @Excel(name = "清单名称")
    private String itemName;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String units;
    /**
     * 字段描述：已复核数量
     */
    @JsonProperty
    @Excel(name = "已复核数量")
    private BigDecimal checkedNum;
    /**
     * 字段描述：WBS编号
     */
    @JsonProperty
    @Excel(name = "WBS编号")
    private String belongWbsCode;
    /**
     * 字段描述：WBS名称
     */
    @JsonProperty
    @Excel(name = "WBS名称")
    private String belongWbs;
    /**
     * 字段描述：WBS单位
     */
    @JsonProperty
    @Excel(name = "WBS单位")
    private String belongWbsUnits;
    /**
     * 字段描述：WBS数量
     */
    @JsonProperty
    @Excel(name = "WBS数量")
    private String belongWbsNum;
    /**
     * 字段描述：施工方案
     */
    @JsonProperty
    @Excel(name = "施工方案")
    private String constructPlan;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：数据来源（1：选择，2：手动新增）
     */
    @JsonProperty
    @Excel(name = "数据来源（1：选择，2：手动新增）")
    private String source;
    /**
     * 字段描述：叶子节点（1：是，0：否）
     */
    @JsonProperty
    @Excel(name = "叶子节点（1：是，0：否）")
    private String leaf;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    @Excel(name = "排序")
    private Integer sort;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    @Excel(name = "是否有效 1-有效 0-失效")
    private String valid;
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
     * 字段描述：多选逗号分隔
     */
    @JsonProperty
    private String belongWbss;
    /**
     * 字段描述：多选逗号分隔
     */
    @JsonProperty
    private String belongWbsCodes;

    public String getBelongWbss() {
        return belongWbss;
    }

    public void setBelongWbss(String belongWbss) {
        this.belongWbss = belongWbss;
    }

    public String getBelongWbsCodes() {
        return belongWbsCodes;
    }

    public void setBelongWbsCodes(String belongWbsCodes) {
        this.belongWbsCodes = belongWbsCodes;
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
    public Long getPid() {
        return pid;
    }

    @JsonIgnore
    public void setPid(Long pid) {
        this.pid = pid;
    }

    @JsonIgnore
    public Long getMasterId() {
        return masterId;
    }

    @JsonIgnore
    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    @JsonIgnore
    public String getItemCode() {
        return itemCode;
    }

    @JsonIgnore
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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
    public String getUnits() {
        return units;
    }

    @JsonIgnore
    public void setUnits(String units) {
        this.units = units;
    }

    @JsonIgnore
    public BigDecimal getCheckedNum() {
        return checkedNum;
    }

    @JsonIgnore
    public void setCheckedNum(BigDecimal checkedNum) {
        this.checkedNum = checkedNum;
    }

    @JsonIgnore
    public String getBelongWbsCode() {
        return belongWbsCode;
    }

    @JsonIgnore
    public void setBelongWbsCode(String belongWbsCode) {
        this.belongWbsCode = belongWbsCode;
    }

    @JsonIgnore
    public String getBelongWbs() {
        return belongWbs;
    }

    @JsonIgnore
    public void setBelongWbs(String belongWbs) {
        this.belongWbs = belongWbs;
    }

    @JsonIgnore
    public String getBelongWbsUnits() {
        return belongWbsUnits;
    }

    @JsonIgnore
    public void setBelongWbsUnits(String belongWbsUnits) {
        this.belongWbsUnits = belongWbsUnits;
    }

    @JsonIgnore
    public String getBelongWbsNum() {
        return belongWbsNum;
    }

    @JsonIgnore
    public void setBelongWbsNum(String belongWbsNum) {
        this.belongWbsNum = belongWbsNum;
    }

    @JsonIgnore
    public String getConstructPlan() {
        return constructPlan;
    }

    @JsonIgnore
    public void setConstructPlan(String constructPlan) {
        this.constructPlan = constructPlan;
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
    public String getSource() {
        return source;
    }

    @JsonIgnore
    public void setSource(String source) {
        this.source = source;
    }

    @JsonIgnore
    public String getLeaf() {
        return leaf;
    }

    @JsonIgnore
    public void setLeaf(String leaf) {
        this.leaf = leaf;
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


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(QqchConstJob.class);
    }
}
