package com.hhwy.pm.jdgl.statistics.service;

import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsQueryVO;
import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsWbsValueVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IPlanStatisticsService {

    Map<String, Map<String, BigDecimal>> getValueCompData(PlanStatisticsQueryVO iPlanStatisticsQueryVO);

    List<PlanStatisticsWbsValueVO> getWbsValueList(PlanStatisticsQueryVO iPlanStatisticsQueryVO);

}
