package com.hhwy.domain.base.system.period.vo;

import java.math.BigDecimal;

/**
 * 汇率、币种、期次信息
 *
 * @author lcf
 */
public class PeriodCurrencyInfoVo {
    /**期次编码**/
    private String periodCode;
    /**汇率**/
    private BigDecimal rate;
    /**币种编码**/
    private String currencyCode;
    /**币种名称**/
    private String currencyName;

    public String getPeriodCode() {
        return periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
