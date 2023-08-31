package com.hhwy.pm.jdgl.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanStatisticsWbsValueVO extends TreeNode<PlanStatisticsWbsValueVO> {

    @JsonProperty
    private String wbsCode;
    @JsonProperty
    private String wbsName;
    @JsonProperty
    private String wbsUnit;
    @JsonProperty
    private BigDecimal ThisPlanValue;
    @JsonProperty
    private BigDecimal ThisActValue;
    @JsonProperty
    private BigDecimal TotalActValue;

}
