package com.hhwy.sd.outlineReview.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.sd.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能描述: 勘察设计 - 勘察设计大纲评审
 * @author fushudong
 * @date 2024-02-04 15:29:15
 * @remark kcsj_outline_review
 */
@Data
public class KcsjOutlineReview extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    //专家库
    List<SgjsExpertLibrary> childList;

    /**
     * 字段描述：
     */
    @JsonProperty
    private Long id;
    /**
     * 字段描述：大纲名称
     */
    @JsonProperty
    @Excel(name = "大纲名称")
    @NotBlank(message = "大纲名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String outlineName;
    /**
     * 字段描述：大纲版本
     */
    @JsonProperty
    @Excel(name = "大纲版本")
    private BigDecimal version;

    private String versionStr;

    /**
     * 字段描述：是否有效 0,1  备用，暂不维护该字段
     */
    @JsonProperty
    @Excel(name = "是否有效")
    private String valid;
    /**
     * 字段描述：当前状态
     */
    @JsonProperty
    @Excel(name = "当前状态")
    private String taskStatus;
    /**
     * 字段描述：计划提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划提交日期", dateFormat = "yyyy年MM月dd日")
    private Date submitPlanDate;
    /**
     * 字段描述：计划评审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划评审日期", dateFormat = "yyyy年MM月dd日")
    private Date reviewPlanDate;
    /**
     * 字段描述：项目总工di
     */
    @JsonProperty
    @Excel(name = "项目总工di")
    private String leadEngineer;
    /**
     * 字段描述：项目总工
     */
    @JsonProperty
    @Excel(name = "项目总工")
    @NotBlank(message = "项目总工不能为空", groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String leadEngineerName;
    /**
     * 字段描述：发起人id
     */
    @JsonProperty
    @Excel(name = "发起人id")
    private String startPerson;
    /**
     * 字段描述：发起人
     */
    @JsonProperty
    @Excel(name = "发起人")
    private String startPersonName;
    /**
     * 字段描述：发起日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "发起日期", dateFormat = "yyyy年MM月dd日")
    private Date startDate;
    /**
     * 字段描述：大纲修回日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "大纲修回日期", dateFormat = "yyyy年MM月dd日")
    private Date remodifyDate;
    /**
     * 字段描述：大纲简述
     */
    @JsonProperty
    @Excel(name = "大纲简述")
    private String outlineSummary;
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
