package com.hhwy.pm.qqch.wzch.source.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 来源策划物资详情年份数据,优先进场物资详情年份数据对象 wzch_source_approach_year_count
 * 
 * @author mls
 * @date 2022-11-21
 */

public class WzchSourceApproachYearCount extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /** 年份 */
    private String year;

    /** id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 来源策划物资详情id或优先进场物资详情id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long detailId;

    /** 物资编码 */
    @JsonSerialize(using= ToStringSerializer.class)
    private String materialCode;

    /** 国内采购 */
    
   @ExcelProperty
    private BigDecimal internalNum;

    /** 第三国采购数量 */
   @ExcelProperty
   
    private BigDecimal otherStateNum;

    /** 第三国采购数量 */
    
   @ExcelProperty
    private BigDecimal localNum;

    /** 租赁数量 */
    
   @ExcelProperty
    private BigDecimal rentNum;

    /** 内部调剂数量 */
    
   @ExcelProperty
    private BigDecimal innerAdjustNum;

    /** 删除标识：0有效1无效 */
    private String delFlag = "0";

    public WzchSourceApproachYearCount() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public BigDecimal getInternalNum() {
        return internalNum;
    }

    public void setInternalNum(BigDecimal internalNum) {
        this.internalNum = internalNum;
    }

    public BigDecimal getOtherStateNum() {
        return otherStateNum;
    }

    public void setOtherStateNum(BigDecimal otherStateNum) {
        this.otherStateNum = otherStateNum;
    }

    public BigDecimal getLocalNum() {
        return localNum;
    }

    public void setLocalNum(BigDecimal localNum) {
        this.localNum = localNum;
    }

    public BigDecimal getRentNum() {
        return rentNum;
    }

    public void setRentNum(BigDecimal rentNum) {
        this.rentNum = rentNum;
    }

    public BigDecimal getInnerAdjustNum() {
        return innerAdjustNum;
    }

    public void setInnerAdjustNum(BigDecimal innerAdjustNum) {
        this.innerAdjustNum = innerAdjustNum;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
