package com.hhwy.pm.qyzs.safe.qyzsSafeCultureMeasureFee.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeCultureMeasureFee.domain.SafeCultureMeasureFeeQueryVo;

/**
 * @author cjh
 * @date 2023-11-21 10:46:55
 * @remark
 */
public interface IQyzsSafeCultureMeasureFeeService {
    AjaxResult getQyzsSafeCultureMeasureFeeList(SafeCultureMeasureFeeQueryVo queryVo);
}
