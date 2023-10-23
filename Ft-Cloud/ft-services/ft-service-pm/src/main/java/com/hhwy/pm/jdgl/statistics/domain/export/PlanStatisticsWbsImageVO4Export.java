package com.hhwy.pm.jdgl.statistics.domain.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanStatisticsWbsImageVO4Export {

    @ExcelProperty(value = "wbs编码")
    @JsonProperty
    private String wbsCode;
    @ExcelProperty(value = "wbs名称")
    @JsonProperty
    private String wbsName;
    @ExcelProperty(value = "单位")
    @JsonProperty
    private String wbsUnit;
    @ExcelProperty(value = "设计量")
    @JsonProperty
    private BigDecimal designNum;
    @ExcelProperty(value = "完成数量")
    @JsonProperty
    private BigDecimal ThisCompNum;

}
