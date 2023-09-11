package com.hhwy.utils.core;

import com.hhwy.common.core.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    private static String[] parsePatterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
    
    /**
     * 获取当前月的第一天日期
     *
     * @return
     */
    public static String getFirstToMonth() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String monthFirstDay = format.format(cal_1.getTime());
        return monthFirstDay;
    }

    /**
     * 获取当前月的第一天日期
     *
     * @return
     */
    public static String getFirstToPattern(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String monthFirstDay = format.format(cal_1.getTime());
        return monthFirstDay;
    }


    /**
     * 获取当月的最后一天
     *
     */
    public static String getLastDayOfMonth()
    {
        Calendar cal = Calendar.getInstance();
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    public static void main(String[] args) {
        String s = getLastDayOfMonth();
        String lastDayOfMonth = getLastDayOfMonth();
        System.out.println("s-->"+s+"lastDayOfMonth--->"+lastDayOfMonth);
    }
    public static List<String> getMonthBetweenDate(Date startDate,Date endDate) {
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        while (startDate.getTime()<=endDate.getTime()){
            list.add(sdf.format(startDate));
            cal.setTime(startDate);
            cal.add(Calendar.MONTH,1);
            startDate=cal.getTime();
        }
        return list;
    }

    public static Date getLastDay(Date date)
    {
        Calendar cal = DateUtils.toCalendar(date);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        return cal.getTime();
    }
    public static Date getfirstDay(Date date)
    {
        Calendar cal = DateUtils.toCalendar(date);
        //获取某月最大天数
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //格式化日期
        return cal.getTime();
    }

    /**
     * 日期 加相应年/月/日
     * @param date 日期
     * @param days  天数
     * @param gs
     * @param type    0年 1月 2日
     * @return
     */
    public static String getDayAddTimes(Date date,int days,String gs,int type) {
        SimpleDateFormat sdf=new SimpleDateFormat(gs);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        if (type == 0) {
            rightNow.add(Calendar.YEAR,days);//日期加 年
        } else if (type == 1) {
            rightNow.add(Calendar.MONTH, days);//日期加 月
        } else {
            rightNow.add(Calendar.DAY_OF_YEAR,days);//日期加天
        }
        Date dt1=rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }
    /**
     * 日期天数差
     * @return
     */
    public static Long getDays(Date startDate,Date endDate) {
        long nd = 86400000L;
        long diff = endDate.getTime()-startDate.getTime();
        long day = diff / nd;
        return day;
    }

    public static Date parseDate(Object str) throws ParseException{
        if (str == null) {
            return null;
        } else {
            try {
                return DateUtils.parseDate(str.toString(), parsePatterns);
            } catch (ParseException var2) {
                throw var2;
            }
        }
    }
}
