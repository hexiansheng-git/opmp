package com.hhwy.pm.qqch.preparation.sbch.plan.service;


import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlan;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 设备总部计划总需用详情Service接口
 * 
 * @author zq
 * @date 2022-11-23
 */
public interface ISbchTotalDemandPlanDetailService {

    List<SbchTotalDemandPlanDetail> selectSbchTotalDemandPlanDetailLeaderList(SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail);

    SbchTotalDemandPlan syncData(BigDecimal version);
}
