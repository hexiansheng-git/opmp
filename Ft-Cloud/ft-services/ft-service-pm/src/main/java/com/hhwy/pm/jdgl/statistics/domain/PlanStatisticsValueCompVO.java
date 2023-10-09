package com.hhwy.pm.jdgl.statistics.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanStatisticsValueCompVO {

    @ExcelProperty(value = "")
    private String type;

    @ExcelProperty(value = "本周")
    private BigDecimal weekValue;

    @ExcelProperty(value = "本月")
    private BigDecimal monthValue;

    @ExcelProperty(value = "本季")
    private BigDecimal quarterValue;

    @ExcelProperty(value = "本年")
    private BigDecimal yearValue;

    @ExcelProperty(value = "开累")
    private BigDecimal totalValue;

}
