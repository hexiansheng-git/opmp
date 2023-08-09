package com.hhwy.utils.common;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PmsUtils {
    /**
     * 字符串数字+1
     *
     * @param testStr
     * @return
     */
    public static String strAddOne(String testStr) {
        String[] strings = testStr.split("[^0-9]");
        String numStr = strings[strings.length-1];
        if (numStr!=null&&numStr.length()>0){
            int n = numStr.length();
           // int num = Integer.parseInt(numStr)+1;
            Long num = Long.parseLong(numStr)+1;
            String added = String.valueOf(num);
            n=Math.min(n,added.length());
            return testStr.subSequence(0,testStr.length()-n)+added;
        }else {
            return null;
        }
    }

    public static void main(String[] args) {
        String str="1301050340010020001";
        String s = strAddOne(str);
        System.out.println("1"+s);
    }
    /**
     * 根据汇率转换金额
     * 比如: 100,5,6.9 > 100/5*6.9=138
     * @param amount                金额
     * @param amountExchangeRate    金额的汇率
     * @param targetExchangeRate    要转换成的币种汇率
     * @return
     */
    public static BigDecimal amountTransfer(BigDecimal amount,BigDecimal amountExchangeRate,BigDecimal targetExchangeRate){
        if(amount == null || amountExchangeRate ==null || targetExchangeRate == null){
            return BigDecimal.ZERO;
        }
        BigDecimal result = amount.divide(amountExchangeRate, 4,RoundingMode.HALF_UP).multiply(targetExchangeRate);
        return result;    
    }

    /**
     * 金额转换成美元
     * @param amount   要转换的金额
     * @param amountExchangeRate  汇率
     * @param currency 币种 
     * @return
     */
    public static BigDecimal amountTransferUSD(BigDecimal amount,BigDecimal amountExchangeRate,String currency){
        if(StringUtils.equals(currency,"USD"))
            return amount;
        BigDecimal resu = BigDecimalUtils.divideMay0(amount, amountExchangeRate, 4, 4);
        return resu==null?BigDecimal.ZERO:resu;
    }
}
