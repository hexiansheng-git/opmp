package com.hhwy.pm.jdgl.statistics.util;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsQueryVO;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

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
                        cl.setWeekDate(Integer.valueOf(year), Integer.valueOf(weekStr), 1);
                        startDate = cl.getTime();
                        cl.setWeekDate(Integer.valueOf(year), Integer.valueOf(weekStr), 7);
                        endDate = cl.getTime();
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
                    if(endDate == null) endDate = sdf.parse(year+"-"+month+"-20");

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

    public static Map<String, Date> getDateRange4Year(String yearStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Date> returnMap = new HashMap<String, Date>();

        Integer year = Integer.valueOf(yearStr);

        try {
            returnMap.put("start", sdf.parse((year-1)+"-12-21"));
            returnMap.put("end", sdf.parse(yearStr+"-12-20"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnMap;
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

    public static Map<String, Date> getDateRange4Week(String year, String week) {

        Map<String, Date> returnMap = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isEmpty(year)|| StringUtils.isEmpty(week)) {
            return returnMap;
        }

        try {
            Calendar cl = Calendar.getInstance();

            cl.setWeekDate(Integer.valueOf(year), Integer.valueOf(week), 1);
            cl.add(Calendar.DATE, 1);
            Date time = cl.getTime();
            String format = simpleDateFormat.format(time);
            returnMap.put("start", simpleDateFormat.parse(format));
            cl.setWeekDate(Integer.valueOf(year), Integer.valueOf(week), 7);
            cl.add(Calendar.DATE, 1);
            time = cl.getTime();
            format = simpleDateFormat.format(time);
            returnMap.put("end", simpleDateFormat.parse(format));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnMap;

    }

    /**
     * 获取日期区间天数(1位小数)
     * @return
     */
    public static Integer getDaysByRangeDate(Date startDate, Date endDate) {

        if(startDate == null || endDate == null) {
            return 0;
        }

        long timeS = startDate.getTime();
        long timeE = endDate.getTime();

        BigDecimal bigE = new BigDecimal(timeE);
        BigDecimal bigS = new BigDecimal(timeS);

        BigDecimal divide = bigE.subtract(bigS).divide(new BigDecimal(24 * 60 * 60 * 1000), 0, BigDecimal.ROUND_UP).add(new BigDecimal(1));

        return divide.intValue();
//        int i = (int) Math.round((timeE - timeS) / 24 / 60 / 60 / 1000) + 1;

//        return (int) Math.round((timeE - timeS) / 24 / 60 / 60 / 1000) + 1;

    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    public static boolean isNumeric2(String str) {
        return str != null && NUMBER_PATTERN.matcher(str).matches();
    }

    public static void getDayList(List<Date> dateList, Date startDate, Date finishDate) {

        dateList.add(startDate);

        Date nowDate = DateUtils.getNowDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = simpleDateFormat.format(nowDate);
        try {
            nowDate = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(startDate.compareTo(finishDate) >= 0 || startDate.compareTo(nowDate) >= 0) {
            return;
        }

        Calendar cl = Calendar.getInstance();

        cl.setTime(startDate);
        cl.add(Calendar.DATE, 1);

        startDate = cl.getTime();

        getDayList(dateList, startDate, finishDate);
    }

    // TODO 获取p6计划的开始月份和结束月份
    public static void getTime(List<Date> dateList, Date startDate, Date finishDate) {

        Date s = getRealMonth(startDate);
        Date f = getRealMonth(finishDate);

        if(s != null) {
            dateList.add(s);
        }

        if(s.compareTo(f) == 0) {
            return;
        }

        Calendar cl = Calendar.getInstance();

        cl.setTime(startDate);
        cl.add(Calendar.MONTH, 1);

        startDate = cl.getTime();

        getTime(dateList, startDate, finishDate);
    }

    public static Date getRealMonth(Date date) {
        Date returnDate = null;
        if(date == null) {
            return returnDate;
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int day = cl.get(Calendar.DAY_OF_MONTH);
        if(day >= 21) {
            cl.add(Calendar.MONTH, 1);
            returnDate = cl.getTime();
        } else {
            returnDate = date;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String format = sdf.format(returnDate);
        try {
            returnDate = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnDate;
    }

    public static Date addDays(Date date, int i) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DATE, i);
        return instance.getTime();
    }

    /**
     * 除以10000
     * @return
     */
    public static BigDecimal getDivideTenThousand(BigDecimal num) {
        return getDivideNum(num, 10000);
    }

    /**
     * 除以100
     * @return
     */
    public static BigDecimal getDivideHundred(BigDecimal num) {
        return getDivideNum(num, 100);
    }

    /**
     * 除以除数(num/divideNum)
     * @param num 被除数
     * @param divideNum 除数
     * @return
     */
    public static BigDecimal getDivideNum(BigDecimal num, int divideNum) {
        if(num == null || divideNum == 0) {
            return BigDecimal.ZERO;
        }
        return num.divide(new BigDecimal(divideNum), 2, BigDecimal.ROUND_HALF_UP);
    }

}
