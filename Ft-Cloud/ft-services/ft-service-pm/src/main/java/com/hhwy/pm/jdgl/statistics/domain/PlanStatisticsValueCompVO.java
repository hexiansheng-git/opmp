package com.hhwy.pm.jdgl.statistics.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanStatisticsValueCompVO {

    @ExcelProperty(value = "-")
    @JsonProperty
    private String type;

    @ExcelProperty(value = "本周")
    @JsonProperty
    private BigDecimal weekValue;

    @ExcelProperty(value = "本月")
    @JsonProperty
    private BigDecimal monthValue;

    @ExcelProperty(value = "本季")
    @JsonProperty
    private BigDecimal quarterValue;

    @ExcelProperty(value = "本年")
    @JsonProperty
    private BigDecimal yearValue;

    @ExcelProperty(value = "开累")
    @JsonProperty
    private BigDecimal totalValue;

}
