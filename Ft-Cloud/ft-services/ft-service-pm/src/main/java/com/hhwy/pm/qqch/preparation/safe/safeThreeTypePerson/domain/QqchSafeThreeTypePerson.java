package com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.domain;

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
import lombok.Data;

/**
 * @author zq
 * @date 2023-08-08 17:22:03
 * @remark qqch_safe_three_type_person
 */
@Data
public class QqchSafeThreeTypePerson extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;

    private Long pId;
    /**
     * 字段描述：职务
     */
    @JsonProperty
    @Excel(name = "职务")
    private String duties;
    /**
     * 字段描述：人员姓名
     */
    @JsonProperty
    @Excel(name = "人员姓名")
    private String personName;

    private String personId;
    /**
     * 字段描述：证照名称
     */
    @JsonProperty
    @Excel(name = "证照名称")
    private String cardName;
    /**
     * 字段描述：证照号码
     */
    @JsonProperty
    @Excel(name = "证照号码")
    private String cardNumber;
    /**
     * 字段描述：签发日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "签发日期", dateFormat = "yyyy-MM-dd")
    private Date signDate;
    /**
     * 字段描述：有效期限（年）
     */
    @JsonProperty
    @Excel(name = "有效期限（年）")
    private Integer validPeriod;
    /**
     * 字段描述：消息提醒
     */
    @JsonProperty
    @Excel(name = "消息提醒")
    private String noticeMsg;
    /**
     * 字段描述：证件地址
     */
    @JsonProperty
    @Excel(name = "证件地址")
    private String cardUrl;
    /**
     * 字段描述：管控措施
     */
    @JsonProperty
    @Excel(name = "管控措施")
    private String controMeasures;
    /**
     * 字段描述：注意事项
     */
    @JsonProperty
    @Excel(name = "注意事项")
    private String attenPoint;
    /**
     * 字段描述：主责部门
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主责部门")
    private Long mainResDeptId;
    /**
     * 字段描述：主责部门名称
     */
    @JsonProperty
    @Excel(name = "主责部门名称")
    private String mainResDeptName;
    /**
     * 字段描述：协作部门
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "协作部门")
    private Long otherDeptId;
    /**
     * 字段描述：协作部门名称
     */
    @JsonProperty
    @Excel(name = "协作部门名称")
    private String otherDeptName;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
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

    private List<QqchSafeThreeTypePerson> childrenList;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getDuties() {
        return duties;
    }

    @JsonIgnore
    public void setDuties(String duties) {
        this.duties = duties;
    }

    @JsonIgnore
    public String getPersonName() {
        return personName;
    }

    @JsonIgnore
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @JsonIgnore
    public String getCardName() {
        return cardName;
    }

    @JsonIgnore
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @JsonIgnore
    public String getCardNumber() {
        return cardNumber;
    }

    @JsonIgnore
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @JsonIgnore
    public Date getSignDate() {
        return signDate;
    }

    @JsonIgnore
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    @JsonIgnore
    public Integer getValidPeriod() {
        return validPeriod;
    }

    @JsonIgnore
    public void setValidPeriod(Integer validPeriod) {
        this.validPeriod = validPeriod;
    }

    @JsonIgnore
    public String getNoticeMsg() {
        return noticeMsg;
    }

    @JsonIgnore
    public void setNoticeMsg(String noticeMsg) {
        this.noticeMsg = noticeMsg;
    }

    @JsonIgnore
    public String getCardUrl() {
        return cardUrl;
    }

    @JsonIgnore
    public void setCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
    }

    @JsonIgnore
    public String getControMeasures() {
        return controMeasures;
    }

    @JsonIgnore
    public void setControMeasures(String controMeasures) {
        this.controMeasures = controMeasures;
    }

    @JsonIgnore
    public String getAttenPoint() {
        return attenPoint;
    }

    @JsonIgnore
    public void setAttenPoint(String attenPoint) {
        this.attenPoint = attenPoint;
    }

    @JsonIgnore
    public Long getMainResDeptId() {
        return mainResDeptId;
    }

    @JsonIgnore
    public void setMainResDeptId(Long mainResDeptId) {
        this.mainResDeptId = mainResDeptId;
    }

    @JsonIgnore
    public String getMainResDeptName() {
        return mainResDeptName;
    }

    @JsonIgnore
    public void setMainResDeptName(String mainResDeptName) {
        this.mainResDeptName = mainResDeptName;
    }

    @JsonIgnore
    public Long getOtherDeptId() {
        return otherDeptId;
    }

    @JsonIgnore
    public void setOtherDeptId(Long otherDeptId) {
        this.otherDeptId = otherDeptId;
    }

    @JsonIgnore
    public String getOtherDeptName() {
        return otherDeptName;
    }

    @JsonIgnore
    public void setOtherDeptName(String otherDeptName) {
        this.otherDeptName = otherDeptName;
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
}
