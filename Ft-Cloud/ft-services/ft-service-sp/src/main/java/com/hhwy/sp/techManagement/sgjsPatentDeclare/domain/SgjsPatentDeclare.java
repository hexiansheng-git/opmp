package com.hhwy.sp.techManagement.sgjsPatentDeclare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:25
 * @remark sgjs_patent_declare
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgjsPatentDeclare extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 保存类型  1：新增；2：修改
     */
    private String saveType;


    /**
     * 提交标识  0：否  1：是
     */
    private String isSubmit;
    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：专利名称
     */
    @JsonProperty
    @FtExcel(name = "专利名称")
    private String patentName;
    /**
     * 字段描述：单位id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long unitId;
    /**
     * 字段描述：单位名称
     */
    @JsonProperty
    @FtExcel(name = "单位名称")
    private String unitName;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @FtExcel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：专利号
     */
    @JsonProperty
    @FtExcel(name = "专利号")
    private String patentNumber;
    /**
     * 字段描述：专利类型   字典：patent_type
     */
    @JsonProperty
    @FtExcel(name = "专利类型",dictType = "patent_type")
    private String patentType;
    /**
     * 字段描述：专业领域  字典：profession_areas
     */
    @JsonProperty
    @FtExcel(name = "专业领域",dictType = "profession_areas")
    private String professionAreas;
    /**
     * 字段描述：专业板块  字典：profession_plate
     */
    @JsonProperty
    @FtExcel(name = "专业板块",dictType = "profession_plate")
    private String professionPlate;
    /**
     * 字段描述：申请日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @FtExcel(name = "申请日期", dateFormat = "yyyy年MM月dd日")
    private Date applicationDate;
    /**
     * 字段描述：授权日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @FtExcel(name = "授权日期", dateFormat = "yyyy年MM月dd日")
    private Date authorizationDate;
    /**
     * 字段描述：主要完成人
     */
    @JsonProperty
    @FtExcel(name = "主要完成人")
    private String principalConsumator;
    /**
     * 字段描述：专利权人
     */
    @JsonProperty
    @FtExcel(name = "专利权人")
    private String patentee;
    /**
     * 字段描述：主要完成人联系方式
     */
    @JsonProperty
    private String principalConsumatorContactWay;
    /**
     * 字段描述：完成单位
     */
    @JsonProperty
    private String completeUnit;
    /**
     * 字段描述：应用证明
     */
    @JsonProperty
    private String applicationProof;
    /**
     * 字段描述：效益分析
     */
    @JsonProperty
    private String efficiencyAnalyse;
    /**
     * 字段描述：知识产权
     */
    @JsonProperty
    private String intellectualProperty;
    /**
     * 字段描述：登记人
     */
    @JsonProperty
    private String registrant;
    /**
     * 字段描述：登记人联系方式
     */
    @JsonProperty
    private String registrantContactWay;
    /**
     * 字段描述：专利简介
     */
    @JsonProperty
    private String patentIntroduction;
    /**
     * 字段描述：专利交底书
     */
    @JsonProperty
    private String patentDisclosure;
    /**
     * 字段描述：专利-其他附件
     */
    @JsonProperty
    private String patentOtherFile;
    /**
     * 字段描述：状态  字典：patent_state
     */
    @JsonProperty
    @FtExcel(name = "状态",dictType = "patent_state")
    private String patentState;
    /**
     * 字段描述：当前状态
     */
    @JsonProperty
    private String currentState;
    /**
     * 字段描述：成果描述
     */
    @JsonProperty
    private String achievementDescription;
    /**
     * 字段描述：成果附件（PDF格式）
     */
    @JsonProperty
    private String achievementFile;
    /**
     * 字段描述：成果-其他附件
     */
    @JsonProperty
    private String achievementOtherFile;
    /**
     * 字段描述：备注
     */
    @JsonProperty
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
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long projectId;

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
     * 字段描述：完成单位id
     */
    @JsonProperty
    private String ptVar1;
    /**
     * 字段描述：流程状态
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

    /**
     * 字段描述：全部奖项名称
     */
    private String allAwardName;

    @FtExcel(name = "成果奖项")
    private String allAward;

    private List<SgjsAchievementAward> awardList;

    private List<SgjsExpertLibrary> libraryList;
}
