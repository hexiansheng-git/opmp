package com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.domain;

import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-27 16:01:25
 * @remark qyzs_finance_tax_item_rate
 */
@Data
public class FinanceTaxItemRateQueryVo {
    /**
     * 字段描述：国家编号
     */
    private String countryCode;
    /**
     * 字段描述：税种
     */
    private String taxesCategories;
}
