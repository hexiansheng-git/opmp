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
import lombok.Data;

import javax.validation.Valid;

/**
 * 功能描述: 科技管理 - 一般课题研发管理
 * @author fsd
 * @date 2024-01-25 10:22:49
 * @remark sgjs_technical_normal_topic
 */
@Data
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
    private String topicCode;
    /**
     * 字段描述：课题名称
     */
    @JsonProperty
    @Excel(name = "课题名称", sort = 4)
    private String topicName;
    /**
     * 字段描述：高新资质
     */
    @JsonProperty
    private String highCertificate;
    @Excel(name = "高新资质", sort = 5)
    private String highCertificateName;

    public String getHighCertificateName() {
        LinkedHashMap<String, String> dictData = DictUtil.getDictData("high_certificate");
        if (CollUtil.isEmpty(dictData)) return "";
        return dictData.get(highCertificate);
    }

    public void setHighCertificateName(String highCertificateName) {
        LinkedHashMap<String, String> dictData = DictUtil.getDictData("high_certificate");
        if (CollUtil.isEmpty(dictData)){
            this.highCertificateName = highCertificateName;
            return;
        }
        this.highCertificateName = dictData.get(highCertificate);
    }

    /**
     * 字段描述：课题类别编号
     */
    @JsonProperty
    private String topicKind;
    /**
     * 字段描述：课题类别
     */
    @JsonProperty
    @Excel(name = "课题类别", sort = 6)
    private String topicKindName;

    /**
     * 字段描述：项目技术经济目标编号
     */
    @JsonProperty
    private String ecoTarget;
    /**
     * 字段描述：项目技术经济目标
     */
    @JsonProperty
    @Excel(name = "项目技术经济目标", sort = 7)
    private String ecoTargetName;

    /**
     * 字段描述：课题状态
     */
    @JsonProperty
    @Excel(name = "课题状态", sort = 8)
    private String topicState;
    private String topicStateName;

    public String getTopicStateName() {
        LinkedHashMap<String, String> dictData = DictUtil.getDictData("topic_state");
        if (CollUtil.isEmpty(dictData)) return "";
        return dictData.get(topicState);
    }

    public void setTopicStateName(String topicStateName) {
        LinkedHashMap<String, String> dictData = DictUtil.getDictData("topic_state");
        if (CollUtil.isEmpty(dictData)) {
            this.topicStateName = topicStateName;
            return;
        }
        this.topicStateName =dictData.get(topicState);
    }

    /**
     * 字段描述：项目成果形式
     */
    @JsonProperty
    private String achievementKind;
    @Excel(name = "项目成果形式", sort = 9)
    private String achievementKindName;

    public String getAchievementKindName() {
        LinkedHashMap<String, String> dictData = DictUtil.getDictData("achievement_kind");
        if (CollUtil.isEmpty(dictData)) return "";
        return dictData.get(achievementKind);
    }

    public void setAchievementKindName(String achievementKindName) {
        LinkedHashMap<String, String> dictData = DictUtil.getDictData("achievement_kind");
        if (CollUtil.isEmpty(dictData)) {
            this.achievementKindName = achievementKindName;
            return;
        }
        this.achievementKindName = dictData.get(achievementKind);
    }

    /**
     * 字段描述：研发起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "研发起始日期", dateFormat = "yyyy年MM月dd日", sort = 10)
    private Date startDate;
    /**
     * 字段描述：研发完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "研发完成日期", dateFormat = "yyyy年MM月dd日", sort = 11)
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
}
