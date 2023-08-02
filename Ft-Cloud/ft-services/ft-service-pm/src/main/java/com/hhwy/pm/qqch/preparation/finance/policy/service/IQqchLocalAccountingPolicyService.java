package com.hhwy.pm.qqch.preparation.finance.policy.service;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalAccountingPolicyVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:44
 * @remark 10.2.3当地会计政策描述
 */
public interface IQqchLocalAccountingPolicyService {

    QqchLocalAccountingPolicyVo getQqchLocalAccountingPolicyList(BigDecimal version);

    void batchSave(QqchLocalAccountingPolicyVo qqchLocalAccountingPolicyVo);
}
