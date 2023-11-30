package com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.domain.SafeEnvRiskProcQueryVo;

/**
 * @author cjh
 * @date 2023-11-17 16:25:55
 * @remark
 */
public interface IQyzsSafeEnvRiskProcService {

    AjaxResult getQyzsSafeEnvRiskProcList(SafeEnvRiskProcQueryVo queryVo);
}
