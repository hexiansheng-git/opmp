package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.vo;

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
 * @date 2023-08-04 16:09:42
 * @remark qqch_first_article_engineering_list
 *
 * 9.5.1 首件工程清单  历史项目
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QqchFirstArticleEngineeringListHistory {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：首件名称
     */
    @JsonProperty
    @Excel(name = "首件名称")
    private String name;
    /**
     * 字段描述：首件分级
     */
    @JsonProperty
    @Excel(name = "首件分级")
    private String grade;
    /**
     * 字段描述：wbs的id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "wbs的id")
    private Long wbsId;
    /**
     * 字段描述：wbs的名称
     */
    @JsonProperty
    @Excel(name = "wbs的名称")
    private String wbsName;

    /**
     * 字段描述：是否亮点工程
     */
    @JsonProperty
    @Excel(name = "是否亮点工程")
    private String lightEngineer;
    /**
     * 字段描述：是否难点工程
     */
    @JsonProperty
    @Excel(name = "是否难点工程")
    private String difficultyEngineer;

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
     * 字段描述：紧前工作
     */
    @JsonProperty
    @Excel(name = "紧前工作")
    private String mmediateWork;

}
