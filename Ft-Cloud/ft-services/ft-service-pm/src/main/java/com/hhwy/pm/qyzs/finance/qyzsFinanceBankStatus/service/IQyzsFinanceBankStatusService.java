package com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.domain.FinanceBankStatusQueryVo;

/**
 * @author cjh
 * @date 2023-11-27 16:41:22
 * @remark
 */
public interface IQyzsFinanceBankStatusService {

    AjaxResult getQyzsFinanceBankStatusList(FinanceBankStatusQueryVo queryVo);
}
