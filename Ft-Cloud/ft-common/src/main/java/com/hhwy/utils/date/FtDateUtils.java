package com.hhwy.utils.date;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具
 *
 * @author mls
 */
public class FtDateUtils extends DateUtils {


    public static int getYear(Date date) {

        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.YEAR);
    }

    public static int getYear() {
        return getYear(new Date());
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    public static Date getFirstDayDateOfMonth(final Date date) {

        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, last);

        return cal.getTime();

    }

    public static Date getLastDayOfMonth(final Date date) {

        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, last);

        return cal.getTime();

    }

    public static int getQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) / 3 + 1;
    }


    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }


    public static String formatDate(Date date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date parseDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDateYm(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    public static int compareMonth(Date bigDate, Date smallDate) {

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(bigDate);
        c2.setTime(smallDate);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
        return monthsDiff;

    }


    public static List<Date> getDateList(Date startDate, Date endDate) {
        List<Date> resDates = new ArrayList<>();
        try {
            //定义日期实例
            Calendar dd = Calendar.getInstance();
            //设置日期起始时间
            dd.setTime(startDate);
            //判断是否到结束日期
            while (dd.getTime().before(endDate)) {
                //输出日期结果
                resDates.add(dd.getTime());
                //进行当前日期月份加1
                dd.add(Calendar.MONTH, 1);

            }
            //输出日期结果
            resDates.add(endDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resDates;
    }

    public static Date getFirstDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 设置年份
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        // 设置月份，因为月份从0开始，所以用month - 1
        c.set(Calendar.MONTH, c.get(Calendar.MONTH));
        // 设置日期
        c.set(Calendar.DAY_OF_MONTH, 1);

        return c.getTime();
    }


    public static Date getLastDay(Date date) {
        // 获取Calendar类的实例
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 设置年份
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        // 设置月份，因为月份从0开始，所以用month - 1
        c.set(Calendar.MONTH, c.get(Calendar.MONTH));
        // 获取当前时间下，该月的最大日期的数字
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 将获取的最大日期数设置为Calendar实例的日期数
        c.set(Calendar.DAY_OF_MONTH, lastDay);

        return c.getTime();
    }

    /**
     * 把日期转为字符串 yyyy-MM
     *
     * @param date
     * @return
     */
    public static String getYearMonthStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    /**
     * 格式yyyy-MM
     *
     * @return
     * @throws ParseException
     */
    public static Date getYearMonthDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String str = sdf.format(date);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前日期 格式yyyy-MM-dd
     *
     * @return
     * @throws ParseException
     */
    public static Date getYearMonthDayDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期天数差
     *
     * @return
     */
    public static Long getDays(Date startDate, Date endDate) {
        long nd = 86400000L;
        long diff = endDate.getTime() - startDate.getTime();
        return diff / nd;
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    }
}
