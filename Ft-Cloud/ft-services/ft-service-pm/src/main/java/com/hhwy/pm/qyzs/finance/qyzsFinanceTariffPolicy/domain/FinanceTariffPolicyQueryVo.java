package com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.domain;

import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-27 16:41:26
 * @remark qyzs_finance_tariff_policy
 */
@Data
public class FinanceTariffPolicyQueryVo {
    /**
     * 字段描述：国家编号
     */
    private String countryCode;
    /**
     * 字段描述：名称
     */
    private String name;
}
