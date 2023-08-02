package com.hhwy.pm.qqch.preparation.finance.contract.service;

import com.hhwy.pm.qqch.preparation.finance.contract.domain.vo.QqchFinancialMainContractTermsVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:03
 * @remark 10.1财务相关主合同条款
 */
public interface IQqchFinancialMainContractTermsService {

    QqchFinancialMainContractTermsVo getQqchFinancialMainContractTermsList(BigDecimal version);

    void batchSave(QqchFinancialMainContractTermsVo qqchFinancialMainContractTermsVo);
}
