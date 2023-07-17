package com.hhwy.utils.common;

import io.jsonwebtoken.lang.Assert;

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


}
