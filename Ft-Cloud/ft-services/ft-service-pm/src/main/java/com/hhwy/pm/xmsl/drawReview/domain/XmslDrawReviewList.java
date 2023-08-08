package com.hhwy.pm.xmsl.drawReview.domain;

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
 * 图纸复核-清单
 * @author wk
 * @date 2023-08-07 11:35:12
 * @remark xmsl_draw_review_list
 */
public class XmslDrawReviewList extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：清单编码,xmsl_contract_list.code
     */
    @JsonProperty
    @Excel(name = "清单编码,xmsl_contract_list.code")
    private String listCode;
    /**
     * 字段描述：主表ID,xmsl_draw_review.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主表ID,xmsl_draw_review.id")
    private Long mainId;
    /**
     * 字段描述：清单ID,xmsl_contract_list.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "清单ID,xmsl_contract_list.id")
    private Long listId;
    /**
     * 字段描述：引用主合同清单ID,xmsl_contract_list.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "引用主合同清单ID,xmsl_contract_list.id")
    private Long contractListId;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父id")
    private Long pid;
    /**
     * 字段描述：祖籍id集合
     */
    @JsonProperty
    @Excel(name = "祖籍id集合")
    private String ancestors;
    /**
     * 字段描述：清单中文名称
     */
    @JsonProperty
    @Excel(name = "清单中文名称")
    private String chineseName;
    /**
     * 字段描述：清单外文名称
     */
    @JsonProperty
    @Excel(name = "清单外文名称")
    private String foreignName;
    /**
     * 字段描述：清单类型(字典项（list_type）)
     */
    @JsonProperty
    @Excel(name = "清单类型(字典项（list_type）)")
    private String listType;
    /**
     * 字段描述：单位编码
     */
    @JsonProperty
    @Excel(name = "单位编码")
    private String unitCode;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：合同总数量
     */
    @JsonProperty
    @Excel(name = "合同总数量")
    private BigDecimal winNum;
    /**
     * 字段描述：复核量
     */
    @JsonProperty
    @Excel(name = "复核量")
    private BigDecimal checkNum;
    /**
     * 字段描述：形象进度单元,0:否,1:是
     */
    @JsonProperty
    @Excel(name = "形象进度单元,0:否,1:是")
    private String imageProgress;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
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

    @JsonIgnore
    public String getListCode() {
        return listCode;
    }

    @JsonIgnore
    public void setListCode(String listCode) {
        this.listCode = listCode;
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
    public Long getListId() {
        return listId;
    }

    @JsonIgnore
    public void setListId(Long listId) {
        this.listId = listId;
    }

    @JsonIgnore
    public Long getContractListId() {
        return contractListId;
    }

    @JsonIgnore
    public void setContractListId(Long contractListId) {
        this.contractListId = contractListId;
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
    public String getAncestors() {
        return ancestors;
    }

    @JsonIgnore
    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    @JsonIgnore
    public String getChineseName() {
        return chineseName;
    }

    @JsonIgnore
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @JsonIgnore
    public String getForeignName() {
        return foreignName;
    }

    @JsonIgnore
    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    @JsonIgnore
    public String getListType() {
        return listType;
    }

    @JsonIgnore
    public void setListType(String listType) {
        this.listType = listType;
    }

    @JsonIgnore
    public String getUnitCode() {
        return unitCode;
    }

    @JsonIgnore
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @JsonIgnore
    public String getUnit() {
        return unit;
    }

    @JsonIgnore
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonIgnore
    public BigDecimal getWinNum() {
        return winNum;
    }

    @JsonIgnore
    public void setWinNum(BigDecimal winNum) {
        this.winNum = winNum;
    }

    @JsonIgnore
    public BigDecimal getCheckNum() {
        return checkNum;
    }

    @JsonIgnore
    public void setCheckNum(BigDecimal checkNum) {
        this.checkNum = checkNum;
    }

    @JsonIgnore
    public String getImageProgress() {
        return imageProgress;
    }

    @JsonIgnore
    public void setImageProgress(String imageProgress) {
        this.imageProgress = imageProgress;
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
