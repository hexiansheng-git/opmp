package com.hhwy.pm.qqch.preparation.finance.contract.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.finance.contract.domain.QqchFinancialMainContractTerms;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:03
 * @remark 10.1财务相关主合同条款
 */
@Data
public class QqchFinancialMainContractTermsVo extends PreparationEntity {

    /**
     * 字段描述：财务相关主合同条款集合
     */
    private List<QqchFinancialMainContractTerms> list;
}
