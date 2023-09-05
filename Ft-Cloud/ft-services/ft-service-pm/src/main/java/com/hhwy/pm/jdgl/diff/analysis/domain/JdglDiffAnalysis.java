package com.hhwy.pm.jdgl.diff.analysis.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 陈锦豪
 * @date 2023-08-28 15:06:24
 * @remark jdgl_diff_analysis
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JdglDiffAnalysis extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：期次
     */
    @JsonFormat(pattern = "yyyy-MM")
    @JsonProperty
    @Excel(name = "期次", dateFormat = "yyyy-MM")
    private Date period;
    /**
     * 字段描述：风险等级
     */
    @JsonProperty
    @Excel(name = "风险等级")
    private String riskLevel;
    /**
     * 字段描述：总得分
     */
    @JsonProperty
    @Excel(name = "总得分")
    private BigDecimal totalGrade;
    /**
     * 字段描述：S曲线差异得分
     */
    @JsonProperty
    @Excel(name = "S曲线差异得分")
    private BigDecimal sDiffGrade;
    /**
     * 字段描述：关键线路形象进度得分
     */
    @JsonProperty
    @Excel(name = "关键线路形象进度得分")
    private BigDecimal keyGrade;
    /**
     * 字段描述：累计计量产值/累计施工产值得分
     */
    @JsonProperty
    @Excel(name = "累计计量产值/累计施工产值得分")
    private BigDecimal valueGrade;
    /**
     * 字段描述：合同超期得分
     */
    @JsonProperty
    @Excel(name = "合同超期得分")
    private BigDecimal contractOverGrade;
    /**
     * 字段描述：重要性得分
     */
    @JsonProperty
    @Excel(name = "重要性得分")
    private BigDecimal importanceGrade;
    /**
     * 字段描述：规模得分
     */
    @JsonProperty
    @Excel(name = "规模得分")
    private BigDecimal scaleGrade;
    /**
     * 字段描述：修正得分
     */
    @JsonProperty
    @Excel(name = "修正得分")
    private BigDecimal correctGrade;
    /**
     * 字段描述：合同金额（万美元）
     */
    @JsonProperty
    @Excel(name = "合同金额（万美元）")
    private BigDecimal contractAmtDl;
    /**
     * 字段描述：合同约定开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "合同约定开工日期", dateFormat = "yyyy-MM-dd")
    private Date contractStartDate;
    /**
     * 字段描述：合同约定竣工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "合同约定竣工日期", dateFormat = "yyyy-MM-dd")
    private Date contractEndDate;
    /**
     * 字段描述：计量产值
     */
    @JsonProperty
    @Excel(name = "计量产值")
    private BigDecimal meterValue;
    /**
     * 字段描述：开累完成产值
     */
    @JsonProperty
    @Excel(name = "开累完成产值")
    private BigDecimal totalCompValue;
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
     * 字段描述：修正得分数据
     */
    @JsonProperty
    @Excel(name = "修正得分数据")
    private Map<String, List<JdglDiffAnalysisCorrect>> jdglDiffAnalysisCorrectList;
}
