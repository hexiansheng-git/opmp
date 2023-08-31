package com.hhwy.pm.jdgl.diff.analysis.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author han
 * @date 2023-08-28 15:06:24
 * @remark jdgl_diff_analysis
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiffAnalysisQueryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：期次
     */
    @JsonFormat(pattern = "yyyy-MM")
    @JsonProperty
    private Date period;
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
     * 字段描述：项目名称
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：是否修正（Y：是，N：否)
     */
    @JsonProperty
    private String correctFlag;
    /**
     * 字段描述：风险等级
     */
    @JsonProperty
    private String riskLevel;
    /**
     * 字段描述：S曲线差异得分
     */
    @JsonProperty
    private BigDecimal sDiffGrade1;
    /**
     * 字段描述：S曲线差异得分
     */
    @JsonProperty
    private BigDecimal sDiffGrade2;
    /**
     * 字段描述：关键线路形象进度得分
     */
    @JsonProperty
    private BigDecimal keyGrade1;
    /**
     * 字段描述：关键线路形象进度得分
     */
    @JsonProperty
    private BigDecimal keyGrade2;
    /**
     * 字段描述：合同超期得分
     */
    @JsonProperty
    private BigDecimal contractOverGrade1;
    /**
     * 字段描述：合同超期得分
     */
    @JsonProperty
    private BigDecimal contractOverGrade2;
    /**
     * 字段描述：累计计量产值/累计施工产值得分
     */
    @JsonProperty
    @Excel(name = "累计计量产值/累计施工产值得分")
    private BigDecimal valueGrade1;
    /**
     * 字段描述：累计计量产值/累计施工产值得分
     */
    @JsonProperty
    @Excel(name = "累计计量产值/累计施工产值得分")
    private BigDecimal valueGrade2;
    /**
     * 字段描述：重要性得分
     */
    @JsonProperty
    private BigDecimal importanceGrade1;
    /**
     * 字段描述：重要性得分
     */
    @JsonProperty
    private BigDecimal importanceGrade2;
    /**
     * 字段描述：规模得分
     */
    @JsonProperty
    private BigDecimal scaleGrade1;
    /**
     * 字段描述：规模得分
     */
    @JsonProperty
    private BigDecimal scaleGrade2;
    /**
     * 字段描述：修正得分
     */
    @JsonProperty
    private BigDecimal correctGrade1;
    /**
     * 字段描述：修正得分
     */
    @JsonProperty
    private BigDecimal correctGrade2;
}
