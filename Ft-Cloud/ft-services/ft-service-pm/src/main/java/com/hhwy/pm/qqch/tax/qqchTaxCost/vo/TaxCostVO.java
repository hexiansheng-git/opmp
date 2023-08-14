package com.hhwy.pm.qqch.tax.qqchTaxCost.vo;

import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCost;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCostDetail;
import com.hhwy.pm.qqch.tax.qqchTaxIn.vo.TaxInVO;
import com.hhwy.utils.JsonUtils;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TaxCostVO {

    // 年份信息
    private List<String> yearList;
    // 币种信息
    private List<TaxInVO.CurrencyVO> currencyVOList;
    // 成本明细
    private List<QqchTaxCost> costList;
    // 间接费用
    private List<QqchTaxCost> otherList;
    // 税费
    private List<QqchTaxCost> taxList;

    public static void main(String[] args) {
        JsonUtils.soutJsonStr(QqchTaxCost.class);
        JsonUtils.soutJsonStr(QqchTaxCostDetail.class);
    }
    
    
}
