package com.hhwy.pm.jdgl.statistics.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface IPlanStatisticsService {

    Map<String, Map<String, BigDecimal>> getValueCompData(
            String queryDateType, String year, String quarter, String month,
            Date startDate, Date endDate
    );

}
