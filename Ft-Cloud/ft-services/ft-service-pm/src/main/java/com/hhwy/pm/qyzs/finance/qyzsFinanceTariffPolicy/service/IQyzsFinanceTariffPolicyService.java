package com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.domain.FinanceTariffPolicyQueryVo;

/**
 * @author cjh
 * @date 2023-11-27 16:41:26
 * @remark
 */
public interface IQyzsFinanceTariffPolicyService {
    AjaxResult getQyzsFinanceTariffPolicyList(FinanceTariffPolicyQueryVo queryVo);
}
