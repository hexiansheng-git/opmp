package com.hhwy.pm.qqch.preparation.sbch.plan.service;

import com.hhwy.pm.qqch.preparation.sbch.plan.vo.SbchTotalDemandPlanDetailVo;

import java.math.BigDecimal;

/**
 * @author zqq
 * @create 2023-08-23 16:39
 */
public interface SbchTotalDemandPlanService {
    SbchTotalDemandPlanDetailVo getList(BigDecimal version);

    void batchSave(SbchTotalDemandPlanDetailVo vo);
}
