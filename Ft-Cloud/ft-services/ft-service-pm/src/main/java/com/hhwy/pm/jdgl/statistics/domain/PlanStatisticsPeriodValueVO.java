package com.hhwy.pm.jdgl.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PlanStatisticsPeriodValueVO {

    /**
     * 期次
     */
    @JsonProperty
    private String period;

    /**
     * 计划产值
     */
    @JsonProperty
    private BigDecimal planValue;

    /**
     * 完成产值
     */
    @JsonProperty
    private BigDecimal compValue;

    /**
     * 偏差
     */
    @JsonProperty
    private BigDecimal diffValue;

    private List<String> periodList;
    private List<BigDecimal> planValueList;
    private List<BigDecimal> compValueList;


}
