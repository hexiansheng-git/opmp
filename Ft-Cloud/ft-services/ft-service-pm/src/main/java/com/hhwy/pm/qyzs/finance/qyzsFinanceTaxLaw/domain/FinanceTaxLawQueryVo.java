package com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.domain;

import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-27 16:01:30
 * @remark qyzs_finance_tax_law
 */
@Data
public class FinanceTaxLawQueryVo {
    /**
     * 字段描述：国家编号
     */
    private String countryCode;
    /**
     * 字段描述：税法英文名称
     */
    private String taxLawEngName;
    /**
     * 字段描述：税法中文名称
     */
    private String taxLawChnName;
}
