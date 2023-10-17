package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

@Data
public class XmslContractPayinfoVo {

    /**
     * 字段描述：支付货币币种编号
     */
    @JsonProperty
    private String currencyCode;

    /**
     * 字段描述：支付货币币种(名称)
     */
    @JsonProperty
    @Excel(name = "支付货币币种")
    private String currencyName;

    /**
     * 字段描述：比例
     */
    @JsonProperty
    @Excel(name = "比例")
    private String proportion;
    /**
     * 字段描述：折算汇率类型 字典项（rate_type）
     */
    @JsonProperty
    @Excel(name = "折算汇率类型")
    private String rateType;
    /**
     * 字段描述：折算汇率
     */
    @JsonProperty
    @Excel(name = "折算汇率")
    private String obversionRate;
    /**
     * 字段描述：资金来源
     */
    @JsonProperty
    @Excel(name = "资金来源")
    private String sourceFunds;
    /**
     * 字段描述：延期利率
     */
    @JsonProperty
    @Excel(name = "延期利率")
    private String extensionRate;
    /**
     * 字段描述：延期利息周期
     */
    @JsonProperty
    @Excel(name = "延期利息周期")
    private String extensionInterestPeriod;
    /**
     * 字段描述：合同约定支付天数
     */
    @JsonProperty
    @Excel(name = "合同约定支付天数")
    private String paymentDays;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
}
