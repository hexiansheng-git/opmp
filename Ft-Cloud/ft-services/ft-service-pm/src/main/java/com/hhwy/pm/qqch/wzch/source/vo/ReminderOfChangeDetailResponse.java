package com.hhwy.pm.qqch.wzch.source.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReminderOfChangeDetailResponse {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
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

    private String year;

}
