package com.hhwy.pm.qqch.tax.qqchTaxIn.vo;

import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxIn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class TaxInVO {

    // 年份信息
    private List<String> yearList;
    // 币种信息
    private List<CurrencyVO> currencyVOList;
    // 主收入
    private List<QqchTaxIn> inList;
    // 其他收入
    private List<QqchTaxIn> otherList;
    
    


    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CurrencyVO {
        private String currency;
        private String currencyName;
        private BigDecimal rate;
    }


}
