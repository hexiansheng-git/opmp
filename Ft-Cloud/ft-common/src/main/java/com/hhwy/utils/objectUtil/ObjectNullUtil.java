package com.hhwy.utils.objectUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : ObjectNullUtil
 * @Description : 优雅的判定对象是否为空
 * @Author : zxb
 * @Date :  8:26
 * @Version : V1.0
 **/
public class ObjectNullUtil<T> {

    public static boolean isEmpty(List list){
        if(list == null || list.size() == 0){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String t){
        if(t == null || "".equals(t)){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Collection collection){
        if(collection == null || collection.size() == 0){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object[] obj){
        if(obj == null || obj.length == 0){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object obj){
        if(obj == null){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Map map){
        if(map == null || map.size() == 0){
            return true;
        }
        return false;
    }

}
