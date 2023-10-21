package com.hhwy.pm.qqch.preparation.sbch.plan.service;

import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlan;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.plan.vo.SbchTotalDemandPlanDetailVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zqq
 * @create 2023-08-23 16:39
 */
public interface SbchTotalDemandPlanService {
    SbchTotalDemandPlan getList(BigDecimal version);

    void batchSave(SbchTotalDemandPlan vo);

    List<SbchTotalDemandPlanDetail> selectSbchTotalDemandPlanDetailLeaderList(SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail);

    SbchTotalDemandPlan getLeaderList(SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail);

}
