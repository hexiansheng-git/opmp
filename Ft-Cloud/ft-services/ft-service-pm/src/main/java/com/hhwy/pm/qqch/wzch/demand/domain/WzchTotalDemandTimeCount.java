package com.hhwy.pm.qqch.wzch.demand.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

/**
 * 物资总需用详情时间数据对象 wzch_total_demand_time_count
 * 
 * @author mls
 * @date 2022-11-16
 */
@Data
@ToString
@NoArgsConstructor
public class WzchTotalDemandTimeCount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** 物资总需用详情id */
    @Excel(name = "物资总需用详情id")

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long totalDemandDetailId;
    
    private BigDecimal version;

    /** 物资编码 */
    @Excel(name = "物资编码")

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String materialCode;

    /** 年份 */

    @Excel(name = "年份")
    private String year;

    /** 年份数量 */
    
    @Excel(name = "年份数量")
    private BigDecimal yearNum;

    /** 一月数量 */
    
    @Excel(name = "一月数量")
    private BigDecimal janNum;

    /** 二月数量 */
    
    @Excel(name = "二月数量")
    private BigDecimal febNum;

    /** 三月数量 */
    
    @Excel(name = "三月数量")
    private BigDecimal marNum;

    /** 四月数量 */
    
    @Excel(name = "四月数量")
    private BigDecimal aprNum;

    /** 五月数量 */
    
    @Excel(name = "五月数量")
    private BigDecimal mayNum;

    /** 六月数量 */
    
    @Excel(name = "六月数量")
    private BigDecimal junNum;

    /** 七月数量 */
    
    @Excel(name = "七月数量")
    private BigDecimal julNum;

    /** 八月数量 */
    
    @Excel(name = "八月数量")
    private BigDecimal augNum;

    /** 九月数量 */
    
    @Excel(name = "九月数量")
    private BigDecimal septNum;

    /** 十月数量 */
    
    @Excel(name = "十月数量")
    private BigDecimal octNum;

    /** 十一月数量 */
    
    @Excel(name = "十一月数量")
    private BigDecimal novNum;

    /** 十二月数量 */
    
    @Excel(name = "十二月数量")
    private BigDecimal decNum;

    /** 第一季度数量 */
    
    @Excel(name = "第一季度数量")
    private BigDecimal firstQuarterNum;

    /** 第二季度数量 */
    
    @Excel(name = "第二季度数量")
    private BigDecimal secondQuarterNum;

    /** 第三季度数量 */
    
    @Excel(name = "第三季度数量")
    private BigDecimal thirdQuarterNum;

    /** 第四季度数量 */
    
    @Excel(name = "第四季度数量")
    private BigDecimal fourthQuarterNum;

    /** 删除标识：0有效1无效 */
    private String delFlag = "0";

    private Long projectId;

    private String materialStandard;
    
    public static WzchTotalDemandTimeCount initZero(String year){
        WzchTotalDemandTimeCount wzchTotalDemandTimeCount = new WzchTotalDemandTimeCount();
        BigDecimal zero = BigDecimal.ZERO;
        wzchTotalDemandTimeCount.year = year;
        wzchTotalDemandTimeCount.yearNum = zero;
        wzchTotalDemandTimeCount.janNum = zero;
        wzchTotalDemandTimeCount.febNum = zero;
        wzchTotalDemandTimeCount.marNum = zero;
        wzchTotalDemandTimeCount.aprNum = zero;
        wzchTotalDemandTimeCount.mayNum = zero;
        wzchTotalDemandTimeCount.junNum = zero;
        wzchTotalDemandTimeCount.julNum = zero;
        wzchTotalDemandTimeCount.augNum = zero;
        wzchTotalDemandTimeCount.septNum = zero;
        wzchTotalDemandTimeCount.octNum = zero;
        wzchTotalDemandTimeCount.novNum = zero;
        wzchTotalDemandTimeCount.decNum = zero;
        wzchTotalDemandTimeCount.firstQuarterNum = zero;
        wzchTotalDemandTimeCount.secondQuarterNum = zero;
        wzchTotalDemandTimeCount.thirdQuarterNum = zero;
        wzchTotalDemandTimeCount.fourthQuarterNum = zero;
        return wzchTotalDemandTimeCount;
    }
    

    public WzchTotalDemandTimeCount(String year) {
        this.year = year;
    }
}
