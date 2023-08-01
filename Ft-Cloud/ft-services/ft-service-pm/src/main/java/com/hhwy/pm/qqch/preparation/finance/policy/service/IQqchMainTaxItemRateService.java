package com.hhwy.pm.qqch.preparation.finance.policy.service;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchMainTaxItemRateVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-01 16:03:00
 * @remark 10.2.2主要税目税率
 */
public interface IQqchMainTaxItemRateService {

    QqchMainTaxItemRateVo getQqchMainTaxItemRateList(BigDecimal version);

    void batchSave(QqchMainTaxItemRateVo qqchMainTaxItemRateVo);
}
