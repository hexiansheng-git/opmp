package com.hhwy.pm.xmsl.drawReview.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.core.base.WarpBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * 图纸复核-细目-原材料配合比
 * @author wk
 * @date 2023-08-08 18:43:48
 * @remark xmsl_draw_review_source_material
 */
@Data
public class XmslDrawReviewSourceMaterial extends WarpBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "Id")
    private Long id;
    /**
     * 字段描述：mainId,xmsl_draw_review.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "mainId,xmsl_draw_review.id")
    private Long mainId;
    /**
     * 字段描述：wbsId,xmsl_draw_review_wbs.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "wbsId,xmsl_draw_review_wbs.id")
    private Long wbsId;
    /**
     * 字段描述：wbs编号,xmsl_draw_review_wbs.code
     */
    @JsonProperty
    @Excel(name = "wbs编号,xmsl_draw_review_wbs.code")
    private String wbsCode;

    private Long listId;
    /**
     * 字段描述：工程量清单编码,xmsl_draw_review_list.list_code
     */
    @JsonProperty
    @Excel(name = "工程量清单编码,xmsl_draw_review_list.list_code")
    private String listCode;
    /**
     * 字段描述：图纸材料细目ID,xmsl_draw_review_material.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "图纸材料细目ID,xmsl_draw_review_material.id")
    private Long materialId;
    /**
     * 字段描述：材料编码
     */
    @JsonProperty
    @Excel(name = "材料编码")
    private String code;
    /**
     * 字段描述：材料名称
     */
    @JsonProperty
    @Excel(name = "材料名称")
    private String name;
    /**
     * 字段描述：规格型号
     */
    @JsonProperty
    @Excel(name = "规格型号")
    private String spec;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：单位配合比
     */
    @JsonProperty
    @Excel(name = "单位配合比")
    private BigDecimal mixProportionRatio;
    /**
     * 字段描述：堆积密度
     */
    @JsonProperty
    @Excel(name = "堆积密度")
    private BigDecimal bulkDensity;
    /**
     * 字段描述：设计量
     */
    @JsonProperty
    @Excel(name = "设计量")
    private BigDecimal quanlity;
    /**
     * 字段描述：wbsId,xmsl_draw_review_wbs.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "wbsId,xmsl_draw_review_wbs.id")
    private Long rawMaterialId;
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
     * 字段描述：数据修改系统时
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据修改系统时", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Set<Long> listIds;
    private Set<Long> materialIds;
}
