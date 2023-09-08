package com.hhwy.pm.jdgl.diff.make.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-25 14:33:50
 * @remark jdgl_correction_measures_make
 */
@Data
public class JdglCorrectionMeasuresMake extends CommonBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：预警期次
     */
    @JsonProperty
    @Excel(name = "预警期次")
    private String warnPeriod;
    /**
     * 字段描述：预警时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "预警时间", dateFormat = "yyyy-MM-dd")
    private Date warnTime;
    /**
     * 字段描述：风险等级（字典类别track_risk_level）
     */
    @JsonProperty
    @Excel(name = "风险等级（字典类别track_risk_level）")
    private String riskLevel;
    /**
     * 字段描述：本期总分
     */
    @JsonProperty
    @Excel(name = "本期总分")
    private BigDecimal periodTotalScore;
    /**
     * 字段描述：编制人id
     */
    @JsonProperty
    @Excel(name = "编制人id")
    private String compilerId;
    /**
     * 字段描述：编制人
     */
    @JsonProperty
    @Excel(name = "编制人")
    private String compiler;
    /**
     * 字段描述：纠偏日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "纠偏日期", dateFormat = "yyyy-MM-dd")
    private Date correctionDate;
    /**
     * 字段描述：项目存在的关键问题
     */
    @JsonProperty
    @Excel(name = "项目存在的关键问题")
    private String existKeyIssue;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    @Excel(name = "流程状态（5已完成）")
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

    private List<JdglCorrectionMeasuresMakeDetail> detailList;
}
