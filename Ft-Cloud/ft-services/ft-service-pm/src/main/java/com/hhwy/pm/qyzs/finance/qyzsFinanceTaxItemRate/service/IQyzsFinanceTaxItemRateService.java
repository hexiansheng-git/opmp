package com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.domain.FinanceTaxItemRateQueryVo;

/**
 * @author cjh
 * @date 2023-11-27 16:01:25
 * @remark
 */
public interface IQyzsFinanceTaxItemRateService {

    AjaxResult getQyzsFinanceTaxItemRateList(FinanceTaxItemRateQueryVo queryVo);
}
