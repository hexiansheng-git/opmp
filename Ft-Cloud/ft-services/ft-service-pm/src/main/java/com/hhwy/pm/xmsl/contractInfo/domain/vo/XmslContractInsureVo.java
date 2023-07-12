package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class XmslContractInsureVo {

    /**
     * 字段描述：保险名称
     */
    @JsonProperty
    @Excel(name = "保险名称")
    private String name;
    /**
     * 字段描述：保险金额
     */
    @JsonProperty
    @Excel(name = "保险金额")
    private BigDecimal amount;
}
