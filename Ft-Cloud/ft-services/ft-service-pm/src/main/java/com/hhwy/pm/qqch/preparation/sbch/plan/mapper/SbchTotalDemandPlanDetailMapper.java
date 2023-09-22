package com.hhwy.pm.qqch.preparation.sbch.plan.mapper;

import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zqq
 * @create 2023-08-23 17:08
 */
public interface SbchTotalDemandPlanDetailMapper {
    List<SbchTotalDemandPlanDetail> selectSbchTotalDemandPlanDetailList(SbchTotalDemandPlanDetail detail);

    void deleteSbchTotalDemandPlanDetailByPlanId(@Param("planId") Long id, @Param("delUser") Long userId, @Param("delTime") Date date);

    void batchInsert(@Param("dataList") List<SbchTotalDemandPlanDetail> list);

    List<SbchTotalDemandPlanDetail> selectSbchTotalDemandPlanDetailLeaderList(SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail);

    List<SbchTotalDemandPlanDetail> getAllDemandDevice(BigDecimal version);
}
