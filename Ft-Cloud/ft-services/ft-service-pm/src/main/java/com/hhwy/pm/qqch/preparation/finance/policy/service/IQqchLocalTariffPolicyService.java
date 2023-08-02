package com.hhwy.pm.qqch.preparation.finance.policy.service;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalTariffPolicyVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:49
 * @remark 10.2.4当地关税政策描述
 */
public interface IQqchLocalTariffPolicyService {

    QqchLocalTariffPolicyVo getQqchLocalTariffPolicyList(BigDecimal version);

    void batchSave(QqchLocalTariffPolicyVo qqchLocalTariffPolicyVo);
}
