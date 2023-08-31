package com.hhwy.pm.jdgl.statistics.service;

import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsQueryVO;
import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsWbsValueVO;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IPlanStatisticsService {

    Map<String, Map<String, BigDecimal>> getValueCompData(PlanStatisticsQueryVO iPlanStatisticsQueryVO) throws ParseException;

    List<PlanStatisticsWbsValueVO> getWbsValueList(PlanStatisticsQueryVO iPlanStatisticsQueryVO) throws ParseException;

}
