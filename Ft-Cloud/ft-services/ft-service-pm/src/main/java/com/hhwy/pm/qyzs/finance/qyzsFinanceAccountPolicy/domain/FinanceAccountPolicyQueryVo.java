package com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.domain;

import lombok.Data;

/**
 * @author cj
 * @date 2023-11-27 16:01:11
 * @remark qyzs_finance_account_policy
 */
@Data
public class FinanceAccountPolicyQueryVo {
    /**
     * 字段描述：国家编号
     */
    private String countryCode;
    /**
     * 字段描述：会计政策
     */
    private String accountingPolicy;
}
