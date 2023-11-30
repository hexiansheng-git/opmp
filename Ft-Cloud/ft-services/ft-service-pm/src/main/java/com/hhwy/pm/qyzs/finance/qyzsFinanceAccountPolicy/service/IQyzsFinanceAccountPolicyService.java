package com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.domain.FinanceAccountPolicyQueryVo;

/**
 * @author cj
 * @date 2023-11-27 16:01:11
 * @remark
 */
public interface IQyzsFinanceAccountPolicyService {

    AjaxResult getQyzsFinanceAccountPolicyList(FinanceAccountPolicyQueryVo queryVo);
}
