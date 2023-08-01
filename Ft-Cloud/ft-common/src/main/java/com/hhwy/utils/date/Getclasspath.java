package com.hhwy.utils.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *  获取当前时间段包含的月份
 */
public class Getclasspath {

    public static List<Date> getmous(String start, String end){
        List<Date> date=new ArrayList<Date>();
        try{
            Date d1 = new SimpleDateFormat("yyyy-MM").parse(start);//定义起始日期

            Date d2 = new SimpleDateFormat("yyyy-MM").parse(end);//定义结束日期

            Calendar dd = Calendar.getInstance();//定义日期实例

            dd.setTime(d1);//设置日期起始时间

            while (dd.getTime().before(d2)) {//判断是否到结束日期

                date.add(dd.getTime());

                dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
            }
            date.add(d2);;//输出日期结果

        }catch (Exception e){
            System.out.println("异常"+e.getMessage());
        }
        return date;
    }
}
