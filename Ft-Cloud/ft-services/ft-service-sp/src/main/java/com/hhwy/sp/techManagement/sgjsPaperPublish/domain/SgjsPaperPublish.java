package com.hhwy.sp.techManagement.sgjsPaperPublish.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2024-01-25 11:01:37
 * @remark sgjs_paper_publish
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgjsPaperPublish extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：论文编号
     */
    @JsonProperty
    @Excel(name = "论文编号")
    private String paperCode;
    /**
     * 字段描述：论文名称
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "论文名称")
    private Long paperName;
    /**
     * 字段描述：申报等级  字典：declare_grade
     */
    @JsonProperty
    @Excel(name = "申报等级")
    private String declareGrade;
    /**
     * 字段描述：专业类别
     */
    @JsonProperty
    @Excel(name = "专业类别")
    private String professionType;
    /**
     * 字段描述：专业板块
     */
    @JsonProperty
    @Excel(name = "专业板块")
    private String professionPlate;
    /**
     * 字段描述：主要完成人
     */
    @JsonProperty
    @Excel(name = "主要完成人")
    private String principalConsumator;
    /**
     * 字段描述：主要完成人联系方式
     */
    @JsonProperty
    @Excel(name = "主要完成人联系方式")
    private String principalConsumatorContactWay;
    /**
     * 字段描述：参与单位
     */
    @JsonProperty
    @Excel(name = "参与单位")
    private String participationUnit;
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
    private String registrantContactWay;
    /**
     * 字段描述：论文简介
     */
    @JsonProperty
    @Excel(name = "论文简介")
    private String paperIntroduction;
    /**
     * 字段描述：附件
     */
    @JsonProperty
    @Excel(name = "附件")
    private String paperFile;
    /**
     * 字段描述：刊物等级
     */
    @JsonProperty
    @Excel(name = "刊物等级")
    private String periodicalGrade;
    /**
     * 字段描述：刊物名称
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "刊物名称", dateFormat = "yyyy-MM-dd")
    private Date periodicalName;
    /**
     * 字段描述：刊登日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "刊登日期", dateFormat = "yyyy-MM-dd")
    private Date publishDate;
    /**
     * 字段描述：成果描述
     */
    @JsonProperty
    @Excel(name = "成果描述")
    private String achievementDescription;
    /**
     * 字段描述：成果附件（PDF格式）
     */
    @JsonProperty
    @Excel(name = "成果附件（PDF格式）")
    private String achievementFile;
    /**
     * 字段描述：成果-其他附件
     */
    @JsonProperty
    @Excel(name = "成果-其他附件")
    private String achievementOtherFile;
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
}
