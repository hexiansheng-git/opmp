package com.hhwy.pm.qqch.tax.qqchTaxCost.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:26
 * @remark qqch_tax_cost_detail
 */
@Data
@ToString
public class QqchTaxCostDetail extends TreeNode<QqchTaxCostDetail> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：qqch_tax_cost.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "qqch_tax_cost.id")
    private Long masterId;
    private List<Long> masterIdList;
    /**
     * 字段描述：1-属地账成本明细_成本明细, 2-属地账成本明细_间接费用, 3-所得税明细
     */
    @JsonProperty
    @Excel(name = "1-属地账成本明细_成本明细, 2-属地账成本明细_间接费用, 3-所得税明细")
    private String dataType;
    /**
     * 字段描述：年份
     */
    @JsonProperty
    @Excel(name = "年份")
    private String year;
    /**
     * 字段描述：币种
     */
    @JsonProperty
    @Excel(name = "币种")
    private String currency;
    /**
     * 字段描述：汇率
     */
    @JsonProperty
    @Excel(name = "汇率")
    private BigDecimal rate;
    /**
     * 字段描述：内账成本/内账
     */
    @JsonProperty
    @Excel(name = "内账成本/内账")
    private BigDecimal innerAmt;
    /**
     * 字段描述：符合属地账要求成本
     */
    @JsonProperty
    @Excel(name = "符合属地账要求成本")
    private BigDecimal reqAmt;
    /**
     * 字段描述：属地账策划成本
     */
    @JsonProperty
    @Excel(name = "属地账策划成本")
    private BigDecimal localAmt;
    /**
     * 字段描述：(美元)内账成本/内账
     */
    @JsonProperty
    @Excel(name = "(美元)内账成本/内账")
    private BigDecimal usdInnerAmt;
    /**
     * 字段描述：(美元)符合属地账要求成本
     */
    @JsonProperty
    @Excel(name = "(美元)符合属地账要求成本")
    private BigDecimal usdReqAmt;
    /**
     * 字段描述：(美元)属地账策划成本
     */
    @JsonProperty
    @Excel(name = "(美元)属地账策划成本")
    private BigDecimal usdLocalAmt;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：叶子节点（1：是，0：否）
     */
    @JsonProperty
    @Excel(name = "叶子节点（1：是，0：否）")
    private String leaf;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    @Excel(name = "排序")
    private Integer sort;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    @Excel(name = "是否有效 1-有效 0-失效")
    private String valid;
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
    private Integer level;
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
    
    private Long recordId;

}
