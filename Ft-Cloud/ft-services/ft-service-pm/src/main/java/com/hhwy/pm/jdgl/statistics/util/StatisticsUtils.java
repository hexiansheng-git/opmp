package com.hhwy.pm.jdgl.statistics.util;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsQueryVO;

import java.text.ParseException;
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Date> returnMap = new HashMap<String, Date>();
        try {
            int quarter = Integer.valueOf(quarterStr);
            int year = Integer.valueOf(yearStr);

            Calendar cl = Calendar.getInstance();
            int endMonth = 3*quarter;
//        cl.set(year, endMonth-2, 1);
            String startStr = endMonth-3 < 10 ? "0"+(endMonth-3) : (endMonth-3)+ "";
            returnMap.put("start", sdf.parse(year + "-" + startStr + "-21"));
//            cl.set(year, endMonth, 1);
//            int actualMaximum = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
//            cl.set(Integer.valueOf(year),Integer.valueOf(endMonth),actualMaximum);
            String endStr = endMonth<10?"0"+endMonth : endMonth+ "";
            returnMap.put("end", sdf.parse(year + "-" + endStr+ "-20"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    public static void initDateParams(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {
        String queryDateType = iPlanStatisticsQueryVO.getQueryDateType();
        String year = iPlanStatisticsQueryVO.getYear();
        String quarter = iPlanStatisticsQueryVO.getQuarter();
        String month = iPlanStatisticsQueryVO.getMonth();
        String weekStr = iPlanStatisticsQueryVO.getWeek();
        Date startDate = iPlanStatisticsQueryVO.getStartDate();
        Date endDate = iPlanStatisticsQueryVO.getEndDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        // 完善日期参数
        try {
            switch (queryDateType) {
                case "z":
                    if(startDate == null || endDate == null) {
                        return;
                    }
                    String start = sdf.format(startDate);
                    String end = sdf.format(endDate);
                    if(StringUtils.isEmpty(year)) year = end.split("-")[0];
                    if(StringUtils.isEmpty(month)) month = end.split("-")[1];
                    if(StringUtils.isEmpty(quarter)) quarter = StatisticsUtils.getQuarter(month);
                    if(StringUtils.isEmpty(weekStr)) {
                        cl.setTime(endDate);
                        int week = cl.get(Calendar.WEEK_OF_YEAR);
                        if (cl.get(Calendar.MONTH)>=11 && week<=1 ){
                            week +=52;
                        }
                        weekStr = week+"";
                    }
                    break;
                case "y":
                    if(StringUtils.isEmpty(month) || StringUtils.isEmpty(year)) {
                        return;
                    }
                    if(StringUtils.isEmpty(quarter)) quarter = StatisticsUtils.getQuarter(month);
                    cl.set(Integer.valueOf(year),Integer.valueOf(month),1);

    //                if(startDate == null) startDate = cl.getTime();
    //                if(endDate == null) {
    //                    int actualMaximum = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
    //                    cl.set(Integer.valueOf(year),Integer.valueOf(month),actualMaximum);
    //                    endDate = cl.getTime();
    //                }
                    if(startDate == null) {
                        if(1 == Integer.valueOf(month)) {
                            startDate = sdf.parse((Integer.valueOf(year)-1)+"-12"+"-21");
                        } else {
                            startDate = sdf.parse(year + "-" + (Integer.valueOf(month)-1) + "-21");
                        }
                    }
                    if(endDate == null) sdf.parse(year+"-"+month+"-20");

                    break;
                case "j":
                    if(StringUtils.isEmpty(quarter) || StringUtils.isEmpty(year)) {
                        return;
                    }
                    Map<String, Date> dateRange = getDateRange4Quarter(year, quarter);
                    if(startDate == null) startDate = dateRange.get("start");
                    if(endDate == null) endDate = dateRange.get("end");
                    break;
                case "n":
                    if(StringUtils.isEmpty(year)) {
                        return;
                    }
                    if(startDate == null) startDate = sdf.parse((Integer.valueOf(year)-1) + "-12-21");
                    if(endDate == null) endDate = sdf.parse(year + "-12-20");
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        iPlanStatisticsQueryVO.setQueryDateType(queryDateType);
        iPlanStatisticsQueryVO.setYear(year);
        iPlanStatisticsQueryVO.setQuarter(quarter);
        iPlanStatisticsQueryVO.setMonth(month);
        iPlanStatisticsQueryVO.setWeek(weekStr);
        iPlanStatisticsQueryVO.setStartDate(startDate);
        iPlanStatisticsQueryVO.setEndDate(endDate);
    }


    public static Map<String, Date> getDateRange4YearMonth(String yearStr, String monthStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Date> returnMap = new HashMap<String, Date>();
        try {
            int month = Integer.valueOf(monthStr);
            int year = Integer.valueOf(yearStr);

            Calendar cl = Calendar.getInstance();
            if(month == 1) {
                returnMap.put("start", sdf.parse((year-1) + "-12-21"));
            } else {
                String startM = (month-1)<10 ? "0"+(month-1) : "" + (month-1);
                returnMap.put("start", sdf.parse(year + "-" + startM + "-21"));
            }

            returnMap.put("end", sdf.parse(year + "-" + monthStr + "-20"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnMap;

    }

    public static Map<String, Date> getDateRange4YearMonth(Date period) {
        if(period == null) {
            return new HashMap<>();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String format = sdf.format(period);
        String[] split = format.split("-");

        return getDateRange4YearMonth(split[0], split[1]);

    }
}
