package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain;

import cn.hutool.core.collection.CollUtil;
import com.hhwy.common.core.web.domain.BaseEntity;

import java.util.Date;
import java.math.BigDecimal;

import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCostDTO;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 功能描述: 科技管理 - 一般课题研发管理
 * @author fsd
 * @date 2024-01-25 10:22:49
 * @remark sgjs_technical_normal_topic
 */
public class SgjsTechnicalNormalTopic extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private List<SgjsTechnicalNormalTopicCost> childList;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：课题编号
     */
    @JsonProperty
    @Excel(name = "课题编号", sort = 3)
    @NotBlank(message = "课题编号不能为空！",groups = ValidationGroups.Save.class)
    private String topicCode;
    /**
     * 字段描述：课题名称
     */
    @JsonProperty
    @Excel(name = "课题名称", sort = 4)
    @NotBlank(message = "课题名称不能为空！",groups = ValidationGroups.Save.class)
    private String topicName;
    /**
     * 字段描述：高新资质
     * dict:high_certificate
     */
    @JsonProperty
    private String highCertificate;
    @Excel(name = "高新资质", sort = 5)
    private String highCertificateName;

    public String getHighCertificateName() {
        LinkedHashMap<String, String> dictData = DictUtil.getDictDataName("high_certificate");
        if (CollUtil.isEmpty(dictData)) return "";
        return dictData.get(highCertificate);
    }

    public void setHighCertificateName(String highCertificateName) {
        LinkedHashMap<String, String> dictData = DictUtil.getDictData("high_certificate");
        if (CollUtil.isEmpty(dictData)){
            this.highCertificate = null;
            return;
        }
        this.highCertificate = dictData.get(highCertificateName);
    }

    /**
     * 字段描述：课题类别编号
     * dict:topic_kind
     */
    @JsonProperty
    @NotBlank(message = "课题类别不能为空！",groups = ValidationGroups.Save.class)
    private String topicKind;
    /**
     * 字段描述：课题类别
     */
    @JsonProperty
    @Excel(name = "课题类别", sort = 6)
    private String topicKindName;

    /**
     * 字段描述：项目技术经济目标编号
     * dict:eco_target
     */
    @JsonProperty
    @NotBlank(message = "项目技术经济目标不能为空！",groups = ValidationGroups.Save.class)
    private String ecoTarget;
    /**
     * 字段描述：项目技术经济目标
     */
    @JsonProperty
    @Excel(name = "项目技术经济目标", sort = 7)
    private String ecoTargetName;

    /**
     * 字段描述：课题状态
     * dict:topic_state
     */
    @JsonProperty
    @Excel(name = "课题状态", sort = 8)
    @NotBlank(message = "课题状态不能为空！",groups = ValidationGroups.Save.class)
    private String topicState;
    private String topicStateName;

    public String getTopicStateName() {
        LinkedHashMap<String, String> dictData = DictUtil.getDictDataName("topic_state");
        if (CollUtil.isEmpty(dictData)) return "";
        return dictData.get(topicState);
    }

    public void setTopicStateName(String topicStateName) {
        LinkedHashMap<String, String> dictData = DictUtil.getDictData("topic_state");
        if (CollUtil.isEmpty(dictData)) {
            this.topicStateName = null;
            return;
        }
        this.topicState =dictData.get(topicStateName);
    }

    /**
     * 字段描述：项目成果形式
     * dict:achievement_kind
     */
    @JsonProperty
    @NotBlank(message = "项目成果形式不能为空！",groups = ValidationGroups.Save.class)
    private String achievementKind;

    @Excel(name = "项目成果形式", sort = 9)
    private String achievementKindName;

    public String getAchievementKindName() {
        LinkedHashMap<String, String> dictData = DictUtil.getDictDataName("achievement_kind");
        if (CollUtil.isEmpty(dictData)) return "";
        return dictData.get(achievementKind);
    }

    public void setAchievementKindName(String achievementKindName) {
        LinkedHashMap<String, String> dictData = DictUtil.getDictData("achievement_kind");
        if (CollUtil.isEmpty(dictData)) {
            this.achievementKind = null;
            return;
        }
        this.achievementKind = dictData.get(achievementKindName);
    }

    /**
     * 字段描述：研发起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @Excel(name = "研发起始日期", dateFormat = "yyyy年MM月dd日", sort = 10)
    @NotNull(message = "研发起始日期不能为空！",groups = ValidationGroups.Save.class)
    private Date startDate;
    /**
     * 字段描述：研发完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @Excel(name = "研发完成日期", dateFormat = "yyyy年MM月dd日", sort = 11)
    @NotNull(message = "研发完成日期不能为空！",groups = ValidationGroups.Save.class)
    private Date endDate;
    /**
     * 字段描述：研发人员名单
     */
    @JsonProperty
    private String personList;
    /**
     * 字段描述：研发人员名单
     */
    @JsonProperty
    @Excel(name = "研发人员名单")
    @NotBlank(message = "研发人员名单不能为空！",groups = ValidationGroups.Save.class)
    private String personNameList;
    /**
     * 字段描述：附件id
     */
    @JsonProperty
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
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "单位名称", sort = 1)
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
    @Excel(name = "依托项目名称", sort = 2)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
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

    public List<SgjsTechnicalNormalTopicCost> getChildList() {
        return childList;
    }

    public void setChildList(List<SgjsTechnicalNormalTopicCost> childList) {
        this.childList = childList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getHighCertificate() {
        return highCertificate;
    }

    public void setHighCertificate(String highCertificate) {
        this.highCertificate = highCertificate;
    }

    public String getTopicKind() {
        return topicKind;
    }

    public void setTopicKind(String topicKind) {
        this.topicKind = topicKind;
    }

    public String getTopicKindName() {
        return topicKindName;
    }



    public String getEcoTarget() {
        return ecoTarget;
    }

    public void setEcoTarget(String ecoTarget) {
        this.ecoTarget = ecoTarget;
    }

    public String getEcoTargetName() {
        return ecoTargetName;
    }

    public String getTopicState() {
        return topicState;
    }

    public void setTopicState(String topicState) {
        this.topicState = topicState;
    }

    public String getAchievementKind() {
        return achievementKind;
    }

    public void setAchievementKind(String achievementKind) {
        this.achievementKind = achievementKind;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPersonList() {
        return personList;
    }

    public void setPersonList(String personList) {
        this.personList = personList;
    }

    public String getPersonNameList() {
        return personNameList;
    }

    public void setPersonNameList(String personNameList) {
        this.personNameList = personNameList;
    }

    public String getFileGroupId() {
        return fileGroupId;
    }

    public void setFileGroupId(String fileGroupId) {
        this.fileGroupId = fileGroupId;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public String getCreateUser() {
        return createUser;
    }

    @Override
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getUpdateUser() {
        return updateUser;
    }

    @Override
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelUser() {
        return delUser;
    }

    public void setDelUser(String delUser) {
        this.delUser = delUser;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getPtVar1() {
        return ptVar1;
    }

    @Override
    public void setPtVar1(String ptVar1) {
        this.ptVar1 = ptVar1;
    }

    @Override
    public String getPtVar2() {
        return ptVar2;
    }

    @Override
    public void setPtVar2(String ptVar2) {
        this.ptVar2 = ptVar2;
    }

    @Override
    public String getPtVar3() {
        return ptVar3;
    }

    @Override
    public void setPtVar3(String ptVar3) {
        this.ptVar3 = ptVar3;
    }

    @Override
    public String getPtVar4() {
        return ptVar4;
    }

    @Override
    public void setPtVar4(String ptVar4) {
        this.ptVar4 = ptVar4;
    }

    @Override
    public String getPtVar5() {
        return ptVar5;
    }

    @Override
    public void setPtVar5(String ptVar5) {
        this.ptVar5 = ptVar5;
    }

    public void setTopicKindName(String topicKindName) {
        this.topicKindName = topicKindName;
    }

    public void setEcoTargetName(String ecoTargetName) {
        this.ecoTargetName = ecoTargetName;
    }
}
