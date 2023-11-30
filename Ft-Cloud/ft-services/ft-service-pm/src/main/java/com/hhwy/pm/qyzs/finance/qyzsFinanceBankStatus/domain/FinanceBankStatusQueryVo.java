package com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.domain;

import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-27 16:41:22
 * @remark qyzs_finance_bank_status
 */
@Data
public class FinanceBankStatusQueryVo {
    /**
     * 字段描述：国家编号
     */
    private String countryCode;
    /**
     * 字段描述：性质
     */
    private String nature;
}
