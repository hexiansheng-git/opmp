package com.hhwy.sp.sciTech.sgjsFourNewsAchievement.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.domain.SgjsAuthenticateEvaluate;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;

/**
 * @author cjh
 * @date 2024-01-25 10:10:50
 * @remark sgjs_four_news_achievement
 */
@Data
public class SgjsFourNewsAchievement extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    // @FtExcel(name = "主键id")
    private Long id;
    /**
     * 字段描述：四新名称
     */
    @JsonProperty
    @FtExcel(name = "四新名称")
    private String fourNewsName;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @FtExcel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：四新类型
     */
    @JsonProperty
    @FtExcel(name = "四新类型", dictType = "four_news_type")
    private String fourNewsType;
    /**
     * 字段描述：获奖类型
     */
    @JsonProperty
    @FtExcel(name = "获奖类型", dictType = "awards_type")
    private String awardsType;
    /**
     * 字段描述：专业类型
     */
    @JsonProperty
    @FtExcel(name = "专业类型", dictType = "speciality_type")
    private String specialityType;
    /**
     * 字段描述：专业板块
     */
    @JsonProperty
    @FtExcel(name = "专业板块", dictType = "speciality_sector")
    private String specialitySector;
    /**
     * 字段描述：主要完成人
     */
    @JsonProperty
    @FtExcel(name = "主要完成人")
    private String leader;
    /**
     * 字段描述：主要完成人联系方式
     */
    @JsonProperty
//    @FtExcel(name = "主要完成人联系方式")
    private String leaderContact;
    /**
     * 字段描述：完成单位
     */
    @JsonProperty
    @FtExcel(name = "完成单位")
    private String compOrgan;
    /**
     * 字段描述：协作单位
     */
    @JsonProperty
    @FtExcel(name = "协作单位")
    private String collaborateOrgan;
    /**
     * 字段描述：登记人
     */
    @JsonProperty
    @FtExcel(name = "登记人")
    private String registrant;
    /**
     * 字段描述：登记人联系方式
     */
    @JsonProperty
    @FtExcel(name = "登记人联系方式")
    private String registrantContact;
    /**
     * 字段描述：技术简介
     */
    @JsonProperty
    @FtExcel(name = "技术简介")
    private String technicalIntroduction;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    // @FtExcel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    // @FtExcel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    // @FtExcel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    // @FtExcel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    // @FtExcel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    // @FtExcel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    // @FtExcel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    // @FtExcel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty
    // @FtExcel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    // @FtExcel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty
    // @FtExcel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    // @FtExcel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty
    // @FtExcel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    // @FtExcel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    // @FtExcel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    // @FtExcel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    // @FtExcel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    // @FtExcel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    // @FtExcel(name = "预留字段5")
    private String ptVar5;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    // @FtExcel(name = "流程状态（5已完成）")
    private String taskStatus;
    /**
     * 字段描述：当前状态
     */
    @JsonProperty
    // @FtExcel(name = "当前状态")
    private String dataCurrentState;

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
     * 四新应用
     */
    private String fourNewApplication;

    /**
     * 是否通过
     */
    private String isPass;

    /**
     * 专家建议
     */
    private String expertAdvice;

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
    private List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList;

    /**
     * 字段描述：鉴定单位
     */
    @JsonProperty
    @FtExcel(name = "鉴定单位")
    private String authenticateUnit;
    /**
     * 字段描述：鉴定日期
     */
    @JsonProperty
    @FtExcel(name = "鉴定日期")
    private String authenticateDate;
    /**
     * 字段描述：评价结论
     */
    @JsonProperty
    @FtExcel(name = "评价结论")
    private String evaluateConclusion;

    /**
     * 字段描述：申报奖项
     */
    @JsonProperty
    @FtExcel(name = "申报奖项")
    private String applyAward;
    /**
     * 字段描述：奖项等级
     */
    @JsonProperty
    @FtExcel(name = "奖项等级")
    private String awardGrade;
    /**
     * 字段描述：奖项类别
     */
    @JsonProperty
    @FtExcel(name = "奖项类别")
    private String awardType;
    /**
     * 字段描述：授予单位
     */
    @JsonProperty
    @FtExcel(name = "授予单位")
    private String grantUnit;
    /**
     * 字段描述：奖项时间
     */
    @JsonProperty
    @FtExcel(name = "奖项时间")
    private String awardTime;

    @JsonProperty
    private String ids;

}
