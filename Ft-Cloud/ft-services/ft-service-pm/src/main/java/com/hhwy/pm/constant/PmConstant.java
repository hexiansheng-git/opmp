package com.hhwy.pm.constant;

import java.math.BigDecimal;

/**
 * 常量类
 * 
 * @author m
 */
public interface PmConstant {
    String END_STAGE = "end";
    String ZERO = "0";
    String ONE = "1";
    String TWO = "2";
    String THREE = "3";
    String MINUS_ONE = "-1";
    BigDecimal MAX_LONG_DECIMAL = new BigDecimal(Long.MAX_VALUE);
    BigDecimal MIN_LONG_DECIMAL = new BigDecimal(Long.MIN_VALUE);


    public static void main(String[] args) {
        System.out.println(MAX_LONG_DECIMAL);
        System.out.println(MIN_LONG_DECIMAL);
    }
}
