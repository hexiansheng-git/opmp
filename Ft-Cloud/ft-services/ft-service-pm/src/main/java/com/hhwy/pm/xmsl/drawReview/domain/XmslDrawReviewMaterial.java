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
import java.util.List;
import java.util.Set;

/**
 * 图纸复核-细目
 * @author wk
 * @date 2023-08-08 18:43:56
 * @remark xmsl_draw_review_material
 */
@Data
public class XmslDrawReviewMaterial extends WarpBaseEntity {
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
    /**
     * 字段描述：工程量清单编码,xmsl_draw_review_list.list_code
     */
    @JsonProperty
    @Excel(name = "工程量清单编码,xmsl_draw_review_list.list_code")
    private String listCode;
    /**
     * 字段描述：工程量清单ID,xmsl_draw_review_list
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "工程量清单ID,xmsl_draw_review_list")
    private Long listId;
    /**
     * 字段描述：材料编码,t_material_info
     */
    @JsonProperty
    @Excel(name = "材料编码,t_material_info")
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
     * 字段描述：材料类型，字典:xmsl_mater_type
     */
    @JsonProperty
    @Excel(name = "材料类型，字典:xmsl_mater_type")
    private String type;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：设计量
     */
    @JsonProperty
    @Excel(name = "设计量")
    private BigDecimal quanlity;
    /**
     * 字段描述：定额损耗系数（%）
     */
    @JsonProperty
    @Excel(name = "定额损耗系数（%）")
    private BigDecimal ratio;
    /**
     * 字段描述：理论用量，设计量*（1+损耗率）
     */
    @JsonProperty
    @Excel(name = "理论用量，设计量*（1+损耗率）")
    private BigDecimal theoreticalDosage;
    /**
     * 字段描述：是否有配合比信息，0:否,1:是
     */
    @JsonProperty
    @Excel(name = "是否有配合比信息，0:否,1:是")
    private Integer mixFlag;
    /**
     * 字段描述：拌合损耗率（%）
     */
    @JsonProperty
    @Excel(name = "拌合损耗率（%）")
    private BigDecimal mixingRatio;
    /**
     * 字段描述：理论需用量，设计量*（1+损耗率）
     */
    @JsonProperty
    @Excel(name = "理论需用量，设计量*（1+损耗率）")
    private BigDecimal theoreticalDosage1;
    /**
     * 字段描述：来源，字典:xmsl_source_flag
     */
    @JsonProperty
    @Excel(name = "来源，字典:xmsl_source_flag")
    private String sourceFlag;
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
    private Set<Long> wbsIds;
    private List<XmslDrawReviewSourceMaterial> sourceMaterialList;

}
