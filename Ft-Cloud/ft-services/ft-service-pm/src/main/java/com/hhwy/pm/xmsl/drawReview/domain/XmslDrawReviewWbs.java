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

/**
 * 图纸复核-wbs
 * @author wk
 * @date 2023-08-07 11:31:56
 * @remark xmsl_draw_review_wbs
 */
@Data
public class XmslDrawReviewWbs extends WarpBaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 字段描述：wbs_id,
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "wbs_id")
    private Long wbsId;
    /**
     * 字段描述：xmsl_draw_review.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "xmsl_draw_review.id")
    private Long mainId;
    /**
     * 字段描述：编号，单位工程按整百递增，分部分项子分项按三位流水号递增
     */
    @JsonProperty
    @Excel(name = "编号，单位工程按整百递增，分部分项子分项按三位流水号递增")
    private String code;
    /**
     * 字段描述：父级ID,最顶级为0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父级ID,最顶级为0")
    private Long parentId;
    /**
     * 字段描述：是否包含子级，0:否,1:是
     */
    @JsonProperty
    @Excel(name = "是否包含子级，0:否,1:是")
    private Integer haveChildren;

    //版本号
    private Integer version;
    //是否为最新版本
    private Integer versionFlag;
    /**
     * 字段描述：祖级ID
     */
    @JsonProperty
    @Excel(name = "祖级ID")
    private String ancestors;
    /**
     * 字段描述：祖级名称
     */
    @JsonProperty
    @Excel(name = "祖级名称")
    private String ancestorsName;
    /**
     * 字段描述：项目部位
     */
    @JsonProperty
    @Excel(name = "项目部位")
    private String partCode;
    /**
     * 字段描述：名称
     */
    @JsonProperty
    @Excel(name = "名称")
    private String name;
    /**
     * 字段描述：清单编码
     */
    @JsonProperty
    @Excel(name = "清单编码")
    private String listCode;
    /**
     * 字段描述：标准wbsId，预留
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "标准wbsId，预留")
    private Long standardId;
    /**
     * 字段描述：标准wbs编码
     */
    @JsonProperty
    @Excel(name = "标准wbs编码")
    private String standardCode;
    /**
     * 字段描述：标准wbs名称
     */
    @JsonProperty
    @Excel(name = "标准wbs名称")
    private String standardName;
    /**
     * 字段描述：节点类型,字典:xmsl_wbs_type
     */
    @JsonProperty
    @Excel(name = "节点类型,字典:xmsl_wbs_type")
    private String nodeType;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：层级
     */
    @JsonProperty
    @Excel(name = "层级")
    private Integer level;
    /**
     * 字段描述：状态,0:停用,1:启用
     */
    @JsonProperty
    @Excel(name = "状态,0:停用,1:启用")
    private Integer status;
    /**
     * 字段描述：设计量
     */
    @JsonProperty
    @Excel(name = "设计量")
    private BigDecimal designQuanlity;
    /**
     * 字段描述：复核量
     */
    @JsonProperty
    @Excel(name = "复核量")
    private BigDecimal checkQuanlity;
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

    //清单集合
    private List<XmslDrawReviewList> list;
    //物资细目集合
    private List<XmslDrawReviewMaterial> materialList;

}
