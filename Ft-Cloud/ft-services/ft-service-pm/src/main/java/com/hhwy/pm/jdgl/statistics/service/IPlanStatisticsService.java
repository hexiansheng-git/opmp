package com.hhwy.pm.jdgl.statistics.service;

import com.hhwy.pm.jdgl.statistics.domain.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IPlanStatisticsService {

    Map<String, Map<String, BigDecimal>> getValueCompData(PlanStatisticsQueryVO iPlanStatisticsQueryVO) ;

    List<PlanStatisticsWbsValueVO> getWbsValueList(PlanStatisticsQueryVO iPlanStatisticsQueryVO) ;

    List<PlanStatisticsBillValueVO> getBillValueList(PlanStatisticsQueryVO iPlanStatisticsQueryVO);

    List<PlanStatisticsWbsImageVO> getImageWbsList(PlanStatisticsQueryVO iPlanStatisticsQueryVO);

    Map<String, PlanStatisticsPeriodValueVO> getYearValueCompareList(PlanStatisticsQueryVO iPlanStatisticsQueryVO);



}
