package com.hhwy.pm.jdgl.statistics.util;

import com.hhwy.common.core.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatisticsUtils {

    public static String getQuarter(String monthStr) {
        int month = 0;
        if(StringUtils.isEmpty(monthStr)) {
            Calendar instance = Calendar.getInstance();
            month = instance.get(Calendar.MONTH) + 1;
        } else {
            month = Integer.valueOf(monthStr);
        }
        int quarter;

        if (month >= 1 && month <= 3) {
            quarter = 1;
        } else if (month >= 4 && month <= 6) {
            quarter = 2;
        } else if (month >= 7 && month <= 9) {
            quarter = 3;
        } else {
            quarter = 4;
        }

        return quarter + "";
    };

    public static Map<String, Date> getDateRange4Quarter(String yearStr, String quarterStr) {
        int quarter = Integer.valueOf(quarterStr);
        int year = Integer.valueOf(yearStr);
        Map<String, Date> returnMap = new HashMap<String, Date>();
        Calendar cl = Calendar.getInstance();
        int endMonth = 3*quarter;
        cl.set(year, endMonth-2, 1);
        returnMap.put("start", cl.getTime());
        cl.set(year, endMonth, 1);
        int actualMaximum = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
        cl.set(Integer.valueOf(year),Integer.valueOf(endMonth),actualMaximum);
        returnMap.put("end", cl.getTime());
        return returnMap;
    }

    public static Map<String, Object> initDateParams(String queryDateType, String year, String quarter, String month, Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        // 完善日期参数
        switch (queryDateType) {
            case "z":
                if(startDate == null || endDate == null) {
                    return null;
                }
                String start = sdf.format(startDate);
                String end = sdf.format(startDate);
                if(StringUtils.isEmpty(year)) year = start.split("-")[0];
                if(StringUtils.isEmpty(month)) month = start.split("-")[1];
                if(StringUtils.isEmpty(quarter)) quarter = StatisticsUtils.getQuarter(month);
                break;
            case "y":
                if(StringUtils.isEmpty(month) || StringUtils.isEmpty(year)) {
                    return null;
                }
                if(StringUtils.isEmpty(quarter)) quarter = StatisticsUtils.getQuarter(month);
                cl.set(Integer.valueOf(year),Integer.valueOf(month),1);
                if(startDate == null) startDate = cl.getTime();
                if(endDate == null) {
                    int actualMaximum = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
                    cl.set(Integer.valueOf(year),Integer.valueOf(month),actualMaximum);
                    endDate = cl.getTime();
                }
                break;
            case "j":
                if(StringUtils.isEmpty(quarter) || StringUtils.isEmpty(year)) {
                    return null;
                }
                Map<String, Date> dateRange = getDateRange4Quarter(year, quarter);
                if(startDate == null) startDate = dateRange.get("start");
                if(endDate == null) endDate = dateRange.get("end");
                break;
            case "n":
                if(StringUtils.isEmpty(year)) {
                    return null;
                }
                break;
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("queryDateType",queryDateType);
        returnMap.put("year",year);
        returnMap.put("quarter",quarter);
        returnMap.put("month",month);
        returnMap.put("startDate",startDate);
        returnMap.put("endDate",endDate);
        return returnMap;
    }


}
