package com.hhwy.pm.qqch.utils;

import io.seata.common.util.CollectionUtils;
import io.seata.common.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class DataCheckUtil {

    @SafeVarargs
    public static <T> void checkSingle(List<T> source, Function<T, Object>... getFields){

        if(CollectionUtils.isEmpty(source) || CollectionUtils.isEmpty(getFields)){
            return;
        }

        for (Function<T, Object> getField : getFields) {
            Set<Object> valueSet = new HashSet<>();

            for (T t : source) {
                Object value = getField.apply(t);
                if(value == null){
                    continue;
                }
                if (value instanceof String && StringUtils.isBlank(String.valueOf(value))) {
                    continue;
                }
                if(valueSet.contains(value)){
                    throw new RuntimeException("检测到重复值！" + value);
                }
                valueSet.add(value);
            }
        }

    }

    public static <T> boolean checkSingle1(List<T> source, Function<T, Object> getField){

        if(CollectionUtils.isEmpty(source) || getField == null){
            return true;
        }

        Set<Object> valueSet = new HashSet<>();

        for (T t : source) {
            Object value = getField.apply(t);
            if(value == null){
                continue;
            }
            if (value instanceof String && StringUtils.isBlank(String.valueOf(value))) {
                continue;
            }
            if(valueSet.contains(value)){
                return false;
            }
            valueSet.add(value);
        }

        return true;
    }

    public static <T> void checkSingle2(List<T> source, Function<T, Object> getField, String message){

        if(CollectionUtils.isEmpty(source) || getField == null){
            return;
        }

        Set<Object> valueSet = new HashSet<>();

        for (T t : source) {
            Object value = getField.apply(t);
            if(value == null){
                continue;
            }
            if (value instanceof String && StringUtils.isBlank(String.valueOf(value))) {
                continue;
            }
            if(valueSet.contains(value)){
                throw new RuntimeException(message);
            }
            valueSet.add(value);
        }

    }
}
