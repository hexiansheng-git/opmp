package com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.domain.FinanceTaxLawQueryVo;

/**
 * @author cjh
 * @date 2023-11-27 16:01:30
 * @remark
 */
public interface IQyzsFinanceTaxLawService {

    AjaxResult getQyzsFinanceTaxLawList(FinanceTaxLawQueryVo queryVo);
}
