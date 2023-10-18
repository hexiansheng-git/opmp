package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ldd
 * @date 2023-08-04 14:24:06
 * @remark qqch_weight_engineering_list
 *
 * 9.4.1 重难点工程清单  历史方案
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QqchWeightEngineeringListHistory {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：重难点工程名称
     */
    @JsonProperty
    @Excel(name = "重难点工程名称")
    private String name;
    /**
     * 字段描述：重难点分析
     */
    @JsonProperty
    @Excel(name = "重难点分析")
    private String analysis;
    /**
     * 字段描述：所属WBS的id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属WBS的id")
    private Long wbsId;
    /**
     * 字段描述：所属WBS的id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属WBS的id")
    private String wbsCode;
    /**
     * 字段描述：所属WBS名称
     */
    @JsonProperty
    @Excel(name = "所属WBS名称")
    private String wbsName;
    /**
     * 字段描述：所属WBS祖级id
     */
    @JsonProperty
    @Excel(name = "所属WBS祖级id")
    private String wbsAncestors;
    /**
     * 字段描述：是否关键分部分项
     */
    @JsonProperty
    @Excel(name = "是否关键分部分项")
    private String keySubItems;
    /**
     * 字段描述：关键工序
     */
    @JsonProperty
    @Excel(name = "关键工序")
    private String keyProcesses;

    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;

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
     * 字段描述：质量管控要点
     */
    @JsonProperty
    @Excel(name = "质量管控要点")
    private String controlKeyPoint;
    /**
     * 字段描述：检测次数
     */
    @JsonProperty
    @Excel(name = "检测次数")
    private BigDecimal detectionsNumber;

    /**
     * 字段描述：重点检查项
     */
    @JsonProperty
    @Excel(name = "重点检查项")
    private String keyInspectionItems;

}
