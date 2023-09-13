package com.hhwy.pm.qqch.wzch.approach.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 优先进场物资详情年份数据对象 wzch_prior_approach_year_count
 * 
 * @author mls
 * @date 2022-11-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WzchPriorApproachYearCount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 来源策划物资详情id或优先进场物资详情id */
    @Excel(name = "来源策划物资详情id或优先进场物资详情id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long detailId;

    /** 类型:1-来源策划物资;2-优先进场物资 */
    @Excel(name = "类型:1-来源策划物资;2-优先进场物资")
    private String type;

    /** 物资编码 */
    @Excel(name = "物资编码")
    @JsonSerialize(using= ToStringSerializer.class)
    private String materialCode;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 国内采购数量 */
    
    @Excel(name = "国内采购数量")
    private BigDecimal internalNum;

    /** 第三国采购数量 */
    
    @Excel(name = "第三国采购数量")
    private BigDecimal otherStateNum;

    /** 第三国采购数量 */
    
    @Excel(name = "第三国采购数量")
    private BigDecimal localNum;

    /** 租赁数量 */
    
    @Excel(name = "租赁数量")
    private BigDecimal rentNum;

    /** 内部调剂数量 */
    
    @Excel(name = "内部调剂数量")
    private BigDecimal innerAdjustNum;

    /** 删除标识：0有效1无效 */
    private String delFlag;
    
    private BigDecimal version;


}
