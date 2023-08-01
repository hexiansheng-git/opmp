package com.hhwy.utils.common;

import io.jsonwebtoken.lang.Assert;

import java.util.Collection;

/**
 * @author mls
 */
public class CommonAssert extends Assert {

    /**
     * 字符串不能为空 否则抛出异常
     *
     * @param str
     * @throws IllegalArgumentException 如果字符串为null或者为空格或者为 "", 就抛出异常。
     */
    public static void notBlank(String str) {
        notBlank(str, "字符串不能为空");
    }

    public static void notBlank(String str, String msg) {
        if (str == null || "".equals(str.trim())) {
            throw new IllegalArgumentException(msg);
        }
    }


    public static void notEmpty(Collection list) {
        notEmpty(list, "集合不能为空");
    }

    public static void notEmpty(Collection list, String msg) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException(msg);
        }
    }


    public static void notEmpty(Object[] objects) {
        notEmpty(objects, "集合不能为空");
    }

    public static void notEmpty(Object[] objects, String msg) {
        if (objects == null || objects.length == 0) {

        }
    }

    public static void isEmpty(Object obj, String msg) {
        if (obj == null) {
            throw new IllegalArgumentException(msg);
        }
    }


}
