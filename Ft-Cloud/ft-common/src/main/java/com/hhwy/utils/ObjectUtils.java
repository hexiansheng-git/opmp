package com.hhwy.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Object对象处理工具类
* <br/>@Author: wk
* <br/>@CreateDate: 2021/8/13 15:04
* <br/>@UpdateUser: wk
* <br/>@UpdateDate: 2021/8/13 15:04
* <br/>@UpdateRemark: 说明本次修改内容
* <br/>@Version: v1.0
*/
public class ObjectUtils {

    public static String toString(Object obj){
        return toString(obj, null);
    }

    public static String toString(Object obj,String defaultVal){
        if(obj == null)
            return defaultVal;
        return obj.toString();
    }

    public static String getForceStringValueFilterZero(Cell cell){
        String resu = getForceStringValue(cell);
        if(StringUtils.isNotBlank(resu))
            resu = resu.replace(".0","");
        return resu;
    }

    public static String getForceStringValue(Cell cell){
        if(cell == null)
            return null;
        cell.setCellType(CellType.STRING);
        String val = cell.getStringCellValue();
        if(val == null)
            return null;
        return val.toString();
    }

    public static Long toLong(Object obj){
        return toLong(obj, null);
    }

    public static Long toLong(Object obj,Long defaultVal){
        if(obj == null || "".equals(obj))
            return defaultVal;
        return Long.valueOf(obj.toString());
    }

    public static Integer toInteger(Object obj){
        return toInteger(obj, null);
    }

    public static Integer toInteger(Object obj,Integer defaultVal){
        if(obj == null)
            return defaultVal;
        return Integer.parseInt(obj+"");
    }

    public static Date toDate(Object obj){
        return toDate(obj, null);
    }

    public static Date toDate(Object obj, Date defaultVal){
        return toDate(obj, defaultVal, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date toDate(Object obj, Date defaultVal,String formatStr){
        if(obj == null)
            return defaultVal;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try{
            Date date = format.parse(obj.toString());
            return date;
        }catch(Exception e){
            return defaultVal;
        }
    }

    public static BigDecimal toDecimal(Object obj){
        return toDecimal(obj,BigDecimal.ZERO);
    }

    public static BigDecimal toDecimal(Object obj,BigDecimal defaultVal){
        if(obj == null)
            return defaultVal;
        return new BigDecimal(obj+"");
    }

    public static boolean isBlank(Object obj){
        return obj == null || "".equals(obj);
    }

    public static boolean isNotBlank(Object obj){
        return !ObjectUtils.isBlank(obj);
    }

    public static String toStringValue(Object obj){
        return toString(obj, "");
    }

    /**
     * 判断object是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj){
        return obj==null || "".equals(obj);
    }

    public static boolean isNotEmpty(Object obj){
        return obj!=null && !"".equals(obj);
    }
    /**
     * object转string ,为空返回""
     * @param obj  
     * @return String
     */
    public static String nvlString(Object obj){
        return ObjectUtils.nvlString(obj,"");
    }


    /**
     * object转string,为空返回指定值
     * @param obj
     * @param defaultVal 
     * @return string
     */
    public static String nvlString(Object obj,String defaultVal){
        if(obj == null)
            return defaultVal;
        return obj+"";
    }
    
    /**
     * object 转int 为空或异常返回0
     * @param obj
     * @return
     */
    public static int nvl(Object obj){
        return nvl(obj,0);            
    }
    
    /**
     * object 转int 为空或异常返回指定值
     * @param obj
     * @param defaultVal
     * @return
     */
    public static int nvl(Object obj,Integer defaultVal){
        if(obj == null)
            return defaultVal;
        int val = defaultVal;
        try{
            val = Integer.parseInt(obj+"");
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public static Boolean nvlBool(Object obj){
        return nvlBool(obj,false);
    }
    
    public static Boolean nvlBool(Object obj,Boolean defaultVal){
        if(obj == null)
            return defaultVal;
        return Boolean.valueOf(obj.toString());
    }

    public static BigDecimal nvlBigDecimal(Object obj){
        return ObjectUtils.nvlBigDecimal(obj,BigDecimal.ZERO);
    }
    public static BigDecimal nvlBigDecimal(Object obj,BigDecimal defaultVal){
        if(obj == null || "".equals(obj))
            return defaultVal;
        return new BigDecimal(obj+"");
    }

    public static Long nvlLong(Object obj){
        return ObjectUtils.nvlLong(obj,0L);
    }

    public static Long nvlLong(Object obj,Long defaultVal){
        if(obj == null || "".equals(obj))
            return defaultVal;
        return Long.valueOf(obj.toString());
    }
    
    /**
     * 构建map
     * @param objs key,val,key1,val1...
     * @return map
     */
    public static Map<String,Object> toMap(Object... objs){
        Map<String,Object> map = new HashMap<>(objs.length/2+1);
        for (int i = 0; i < objs.length; i+=2) {
            Object val = null;
            if(i+1 < objs.length )
                val = objs[i+1];
            map.put(objs[i].toString(),val);
        }
        return map;
    }

    /**
     * 构建map，value为指定类型
     * @param t   value的类型
     * @param objs
     * @param <T>
     * @return
     */
    public static <T> Map<String,T> toMap(Class<T> t,Object... objs){
        Map<String,T> map = new HashMap<>(objs.length/2+1);
        for (int i = 0; i < objs.length; i+=2) {
            Object val = null;
            if(i+1 < objs.length )
                val = objs[i+1];
            map.put(objs[i].toString(),(T)val);
        }
        return map;
    }

    public static <T> List<T> toList(T... objs){
        List<T> list = new ArrayList<>(objs.length);
        for (int i = 0; i < objs.length; i++) {
            list.add(objs[i]);
        }
        return list;
    }
    
    /**
     * 构建map
     * @param objs key,val,key1,val1...
     * @return map
     */
    public static Map<String,Object> toLinkedMap(Object... objs){
        Map<String,Object> map = new LinkedHashMap<>(objs.length/2+1);
        for (int i = 0; i < objs.length; i+=2) {
            Object val = null;
            if(i+1 < objs.length )
                val = objs[i+1];
            map.put(objs[i].toString(),val);
        }
        return map;
    }

    /**
     * 切割字符串为指定类型的list
     * @param t
     * @param str
     * @param <T>
     * @return
     */
    public static <T> List<T> splitToList(Class<T> t, String str){
        if(StringUtils.isBlank(str))
            return new ArrayList<>(2);
        String[] strs = str.split(",");
        List<T> resultList = new ArrayList<>(strs.length);
        for (int i = 0; i < strs.length; i++) {
            if(t == Integer.class){
                resultList.add((T)new Integer(Integer.parseInt(strs[i])));
            }else if(t == Double.class){
                resultList.add((T)new Double(Double.parseDouble(strs[i])));
            }else if(t == Long.class){
                resultList.add((T)new Long(Long.parseLong(strs[i])));
            }else if(t == BigDecimal.class){
                resultList.add((T)new BigDecimal(strs[i]));
            }else if(t == String.class){
                resultList.add((T)strs[i]);
            }
        }
        return resultList;
    }

    /**
     * 是否为数字，只判断第一位
     * @param str
     * @return
     */
    public static boolean isNum(String str){
        char c = str.charAt(0);
        return ObjectUtils.isNum(c);
    }
    /**
     * 是否为数字，
     * @param c
     * @return
     */
    public static boolean isNum(char c){
        return c>47 && c<58;
    }

    /**
     * 字符串转数字，忽略非数字字符
     * @param str
     * @return
     */
    public static Integer toNumber(String str){
        if(StringUtils.isBlank(str))
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(ObjectUtils.isNum(c)){
                 sb.append(c);        
            }
        }
        return Integer.parseInt(sb.toString());
    }
    
    /**
     * 将字符转成数字(忽略非数字字符)，然后比较大小
     * @param str
     * @param str1
     * @return -1:参数二大 ,0:相等, 1:参数一大
     */
    public static int compareStrAsNum(String str,String str1){
        Integer num = ObjectUtils.toNumber(str);
        Integer num1 = ObjectUtils.toNumber(str1);
        if(num == null){
            return 0;
        }
        if(num1==null){
            return 0;
        }
        if(num.equals(num1))
            return 0;
        return num > num1?1:-1;
    }

    /**
     * 更好的比较字符串转为数字比较大小
     * 取出字符中的数字比较而不是一个一个字符比较
     * 例如 传入 "S-2","S-10">false   
     *          "S-300-2","S-300-1">true
     * @param str
     * @param str1
     * @param ignoreCase 无视大小写
     * @return
     */
    public static boolean compareStrAsNumPrime(String str,String str1,boolean ignoreCase){
        int limitLen = str.length()-str1.length();
        //需要填补的str
        String fixStr = ObjectUtils.buildStr(" ", Math.abs(limitLen));
        if(limitLen< 0){
            str+=fixStr;
        }else{
            str1+=fixStr;
        }
        //计算长度较长字符
        int startIndex = -1;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            char c1 = str1.charAt(i);
            boolean isNum = ObjectUtils.isNum(c);
            boolean isNum1 = ObjectUtils.isNum(c1);
            //都是非数字字符，    
            if(!isNum && !isNum1){
                if(startIndex > -1){
                    startIndex = -1;
                    //对比字符串
//                    System.out.println(sb+"::"+sb1);
                    int result = ObjectUtils.compareStrAsNum(sb.toString(),sb1.toString());
                    sb = new StringBuilder();
                    sb1 = new StringBuilder();
                    if(result != 0){
                        return result > 0;
                    }
                }
                //字符之间比较
                int result = -2;
                if(ignoreCase){
                    result = StringUtils.compareIgnoreCase((c+""), (c1+""));
                }else{
                    result = StringUtils.compare((c+""), (c1+""));
                }
                if(result != 0) //若字符不相等，直接返回对比结果
                    return result > 0;
                continue;
            }
            if(startIndex == -1)
                startIndex = i;
            sb.append(c);
            sb1.append(c1);
        }
        if(startIndex > -1){
//            System.out.println(sb+":|:"+sb1);
            return ObjectUtils.compareStrAsNum(sb.toString(),sb1.toString()) > 0;
        }
        return str.compareTo(str1)>0;
    }

    /**
     * 构建指定长度的字符串 
     * 例如传入 "F" 3 > "FFF"
     * @param str  
     * @param len  长度 
     * @return
     */
    public static String buildStr(String str,int len){
        if(len < 1)
            return "";
        if(StringUtils.isBlank(str))
            str = " ";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    /**
     * 累加值到map的value
     * @param map
     * @param key
     * @param val
     */
    public static void add2Map(Map map, String key,Object val){
        Object sval = map.get(key);
        if(sval == null){
            map.put(key, val);   
        }else{
            if(val instanceof BigDecimal){
                map.put(key, ((BigDecimal)sval).add((BigDecimal)val));
            }else if(val instanceof Double){
                map.put(key, ((Double)sval)+((Double)val));
            }else if(val instanceof Long){
                map.put(key, ((Long)sval)+((Long)val));
            }else if(val instanceof Integer){
                map.put(key, ((Integer)sval)+((Integer)val));
            }
        }
    }

    /**
     * 存放val到map中,map的值为list
     * @param map <key:list<T>>
     * @param key
     * @param val
     */
    public static <T,T1> void add2MapList(Map<T,List<T1>> map, T key, T1 val){
        List<T1> list = map.get(key);
        if(list == null){
            list = new ArrayList<>();
            map.put(key, list);
        }
        list.add(val);
    }

    /**
     * 存放val到map中,map的值为list
     * @param map <key:list<T>>
     * @param key
     * @param val
     */
    public static <T,T1> void add2MapSet(Map<T,Set<T1>> map, T key, T1 val){
        Set<T1> list = map.get(key);
        if(list == null){
            list = new HashSet<>();
            map.put(key, list);
        }
        list.add(val);
    }

    /**
     * 累加值到list
     * @param list
     * @param o
     */
    public static <T> List<T> add2List(List<T> list,T o){
        if(list==null)
            list = new ArrayList<>(10);
        list.add(o);
        return list;
    }


    public static <T,T1> void addStr2MapList(Map<T,String> map, T key, String val){
        String str = map.get(key);
        String prefix = StringUtils.isBlank(str)?"":str+",";
        map.put(key,prefix+val);
    }

    /**
     * 拼接url,而不会产生多余的/
     * (www.XX.com/ , /getUser) > www.XX.com/getUser
     * @param url
     * @param url
     */
    public static String concatUrl(String url,String url1){
        if(StringUtils.isBlank(url))
            return url1;
        if(StringUtils.isBlank(url1))
            return url;
        url = StringUtils.trim(url);
        url1 = StringUtils.trim(url1);
        if(url.endsWith("/")){
            url = url.substring(0,url.length()-1);
        }
        if(!url1.startsWith("/")){
            url1 = "/"+url1;
        }
        return url+url1;
    }

    /**
     * 拼接祖级字段
     * 例: A,B -> A,B ; null,B -> B
     * @param ancestors
     * @param me
     */
    public static String concatAncestors(String ancestors,String me){
        if(StringUtils.isBlank(ancestors))
            return me;
        if(StringUtils.isBlank(me))
            return ancestors;
        return ancestors+","+me;
    }

    /**
     * 将参数一按照map的对应关系转化 
     * "A,B,C",{A:1,B:2} -> "1,2,C"
     * @param anceStr
     * @param map
     * @return
     */
    public static String replaceWithMap(String anceStr, Map map){
        if(StringUtils.isBlank(anceStr))
            return "";
        String[] strs = anceStr.split(",");
        List<String> resuList = new ArrayList<>(strs.length);
        for (int i = 0; i < strs.length; i++) {
            if(StringUtils.isBlank(strs[i]) || "null".equals(strs[i]))
                continue;
            resuList.add(ObjectUtils.nvlString(map.get(strs[i]+""),strs[i]));
        }
        return StringUtils.join(resuList, ",");    
    }

    public static String replaceWithLongMap(String anceStr, Map map){
        if(StringUtils.isBlank(anceStr))
            return "";
        String[] strs = anceStr.split(",");
        List<String> resuList = new ArrayList<>(strs.length);
        for (int i = 0; i < strs.length; i++) {
            if(StringUtils.isBlank(strs[i]) || "null".equals(strs[i]))
                continue;
            resuList.add(ObjectUtils.nvlString(map.get(Long.valueOf(strs[i])),strs[i]));
        }
        return StringUtils.join(resuList, ",");
    }

    /**
     * 限制金额大小
     * @param num
     * @param min
     * @param max
     * @return
     */
    public static BigDecimal limitNumber(BigDecimal num,BigDecimal min,BigDecimal max){
        if(num == null)
            return null;
        if(min == null)
            min = new BigDecimal("0");
        if(num.compareTo(min) == -1)
            num = min;
        if(max != null && num.compareTo(max) == 1)
            num = max;
        System.out.println(num);
        return num; 
    }
    /**
     * 累加值到map的value
     * @param map
     * @param key
     * @param val
     */
    public static void add2StrMap(Map<String,String> map, String key,String val){
        String sval = map.get(key);
        if(sval == null){
            map.put(key, val);
        }else{
            map.put(key, sval+","+val);
        }
    }
    
//    public static void join(String limit,String... strs){
////        StringUtils.join()    
//    }
}
