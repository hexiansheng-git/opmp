package com.hhwy.pm.jdgl.statistics.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanStatisticsWbsValueVO extends TreeNode<PlanStatisticsWbsValueVO> {

    @JsonProperty
    @ExcelProperty(value = "wbs编码")
    private String wbsCode;
    @JsonProperty
    @ExcelProperty(value = "wbs名称")
    private String wbsName;
    @JsonProperty
    @ExcelProperty(value = "单位")
    private String wbsUnit;
    @JsonProperty
    @ExcelProperty(value = "本阶段计划数量")
    private BigDecimal ThisPlanValue = new BigDecimal(0);
    @JsonProperty
    @ExcelProperty(value = "本阶段完成数量")
    private BigDecimal ThisActValue = new BigDecimal(0);
    @JsonProperty
    @ExcelProperty(value = "开累完成数量")
    private BigDecimal TotalActValue = new BigDecimal(0);

}
