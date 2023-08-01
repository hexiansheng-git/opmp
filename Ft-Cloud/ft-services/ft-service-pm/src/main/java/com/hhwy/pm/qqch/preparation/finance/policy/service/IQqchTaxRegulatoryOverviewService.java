package com.hhwy.pm.qqch.preparation.finance.policy.service;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchTaxRegulatoryOverviewVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-01 16:02:35
 * @remark 10.2.1税务监管环境概述
 */
public interface IQqchTaxRegulatoryOverviewService {

    QqchTaxRegulatoryOverviewVo getQqchTaxRegulatoryOverview(BigDecimal version);

    void batchSave(QqchTaxRegulatoryOverviewVo qqchTaxRegulatoryOverviewVo);
}
