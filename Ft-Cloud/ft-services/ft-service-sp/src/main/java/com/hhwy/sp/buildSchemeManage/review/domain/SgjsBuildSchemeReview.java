package com.hhwy.sp.buildSchemeManage.review.domain;

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
 * @date 2024-03-20 09:39:35
 * @remark sgjs_build_scheme_review
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgjsBuildSchemeReview extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：关联id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "关联id")
    private Long relevancyId;
    /**
     * 字段描述：方案编号
     */
    @JsonProperty
    @Excel(name = "方案编号")
    private String schemeNum;
    /**
     * 字段描述：方案名称
     */
    @JsonProperty
    @Excel(name = "方案名称")
    private String schemeName;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
    @Excel(name = "关联WBS")
    private String relationWbsId;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
    @Excel(name = "关联WBS")
    private String relationWbsName;
    /**
     * 字段描述：方案类型
     */
    @JsonProperty
    @Excel(name = "方案类型")
    private String schemeType;
    /**
     * 字段描述：方案分级 1Ⅰ、2Ⅱ、3Ⅲ、4Ⅳ
     */
    @JsonProperty
    @Excel(name = "方案分级 1Ⅰ、2Ⅱ、3Ⅲ、4Ⅳ")
    private String schemeLevel;
    /**
     * 字段描述：危大等级 1危大、2超危大、3一般
     */
    @JsonProperty
    @Excel(name = "危大等级 1危大、2超危大、3一般")
    private String dangerLevel;
    /**
     * 字段描述：计划编制完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划编制完成时间", dateFormat = "yyyy-MM-dd")
    private Date planComplationTime;
    /**
     * 字段描述：计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划实施时间", dateFormat = "yyyy-MM-dd")
    private Date planStartTime;
    /**
     * 字段描述：本方案编制负责人
     */
    @JsonProperty
    @Excel(name = "本方案编制负责人")
    private String schemeCompilePrincipal;
    /**
     * 字段描述：本方案编制负责人联系方式
     */
    @JsonProperty
    @Excel(name = "本方案编制负责人联系方式")
    private String principalContactWay;
    /**
     * 字段描述：方案发起人
     */
    @JsonProperty
    @Excel(name = "方案发起人")
    private String schemeInitiatorName;
    /**
     * 字段描述：方案发起人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "方案发起人id")
    private Long schemeInitiatorId;
    /**
     * 字段描述：提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "提交日期", dateFormat = "yyyy-MM-dd")
    private Date submitDate;
    /**
     * 字段描述：方案附件组id
     */
    @JsonProperty
    @Excel(name = "方案附件组id")
    private String schemeGroupId;
    /**
     * 字段描述：方案清单项目内部审核记录表附件组id
     */
    @JsonProperty
    @Excel(name = "方案清单项目内部审核记录表附件组id")
    private String auditRecordGroupId;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：流程状态
     */
    @JsonProperty
    @Excel(name = "流程状态")
    private String taskStatus;
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
     * 字段描述：项目编码
     */
    @JsonProperty
    @Excel(name = "项目编码")
    private String projectCode;
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
