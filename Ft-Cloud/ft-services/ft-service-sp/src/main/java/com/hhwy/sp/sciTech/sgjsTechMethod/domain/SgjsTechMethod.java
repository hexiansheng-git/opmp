package com.hhwy.sp.sciTech.sgjsTechMethod.domain;

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
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;
import lombok.Data;

/**
 * @author cjh
 * @date 2024-01-25 10:11:14
 * @remark sgjs_tech_method
 */
@Data
public class SgjsTechMethod extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：工艺工法编号
     */
    @JsonProperty
    @Excel(name = "工艺工法编号")
    private String techMethodCode;
    /**
     * 字段描述：工艺工法名称
     */
    @JsonProperty
    @Excel(name = "工艺工法名称")
    private String techMethodName;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：专业类型
     */
    @JsonProperty
    @Excel(name = "专业类型")
    private String specialityType;
    /**
     * 字段描述：专业板块
     */
    @JsonProperty
    @Excel(name = "专业板块")
    private String specialitySector;
    /**
     * 字段描述：主要完成人
     */
    @JsonProperty
    @Excel(name = "主要完成人")
    private String leader;
    /**
     * 字段描述：主要完成人联系方式
     */
    @JsonProperty
    @Excel(name = "主要完成人联系方式")
    private String leaderContact;
    /**
     * 字段描述：完成单位
     */
    @JsonProperty
    @Excel(name = "完成单位")
    private String compOrgan;
    /**
     * 字段描述：知识产权
     */
    @JsonProperty
    @Excel(name = "知识产权")
    private String intellectualProperty;
    /**
     * 字段描述：应用证明
     */
    @JsonProperty
    @Excel(name = "应用证明")
    private String applicationProof;
    /**
     * 字段描述：效益分析
     */
    @JsonProperty
    @Excel(name = "效益分析")
    private String benefitAnalysis;
    /**
     * 字段描述：等级
     */
    @JsonProperty
    @Excel(name = "等级")
    private String level;
    /**
     * 字段描述：登记人
     */
    @JsonProperty
    @Excel(name = "登记人")
    private String registrant;
    /**
     * 字段描述：登记人联系方式
     */
    @JsonProperty
    @Excel(name = "登记人联系方式")
    private String registrantContact;
    /**
     * 字段描述：工艺工法简介
     */
    @JsonProperty
    @Excel(name = "工艺工法简介")
    private String techMethodIntroduction;
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

    /**
     * 成果描述
     */
    private String achievementDescription;

    /**
     * 成果附件组id
     */
    private String achievementGroupId;

    /**
     * 其他附件组id
     */
    private String otherGroupId;

    /**
     * 是否通过
     */
    private String isPass;

    /**
     * 专家数据集合
     */
    private List<SgjsExpertLibrary> sgjsExpertLibraryList;

    /**
     * 成果奖项数据集合
     */
    private List<SgjsAchievementAward> sgjsAchievementAwardList;

    /**
     * 鉴定或评价数据集合
     */
    private List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList;

}
