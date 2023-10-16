package com.hhwy.pm.jdgl.statistics.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanStatisticsBillValueVO extends TreeNode<PlanStatisticsBillValueVO> {

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long billId;

    /**
     * 清单编码
     */
    @ExcelProperty(value = "清单编码")
    @JsonProperty
    private String billCode;
    /**
     * 清单名称
     */
    @ExcelProperty(value = "清单名称")
    @JsonProperty
    private String billName;
    /**
     * 清单单位
     */
    @ExcelProperty(value = "清单单位")
    @JsonProperty
    private String billUnit;
    /**
     * 清单价格
     */
    @ExcelProperty(value = "清单价格")
    @JsonProperty
    private BigDecimal billPrice;
    /**
     * 上期末累计完成设计量
     */
    @ExcelProperty(value = "上期末累计完成设计量")
    @JsonProperty
    private BigDecimal lastTotalDesignNum;
    /**
     * 剩余设计量
     */
    @ExcelProperty(value = "剩余设计量")
    @JsonProperty
    private BigDecimal remainDesignNum;
    /**
     * 本周计划设计量
     */
    @ExcelProperty(value = "本周计划设计量")
    @JsonProperty
    private BigDecimal thisPlanDesignNum;
    /**
     * 本周计划金额
     */
    @ExcelProperty(value = "本周计划金额")
    @JsonProperty
    private BigDecimal thisPlanValue;
    /**
     * 本次完成设计量
     */
    @ExcelProperty(value = "本次完成设计量")
    @JsonProperty
    private BigDecimal thisCompDesignNum;
    /**
     * 本次完成产值
     */
    @ExcelProperty(value = "本次完成产值")
    @JsonProperty
    private BigDecimal thisCompValue;
    /**
     * 开累完成设计量
     */
    @ExcelProperty(value = "开累完成设计量")
    @JsonProperty
    private BigDecimal thisTotalDesignNum;

}
