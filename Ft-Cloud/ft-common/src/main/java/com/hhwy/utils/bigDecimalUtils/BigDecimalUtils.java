package com.hhwy.utils.bigDecimalUtils;

import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BigDecimalUtils {
    //过滤bigDecimal对象为null
    public static BigDecimal ifNullSet0(BigDecimal in) {
        if (in != null) {
            return in;
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal ifNagativeSet0(BigDecimal in) {
        if (in == null)
            return BigDecimal.ZERO;
        return in.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : in;
    }

    public static BigDecimal sum(BigDecimal... in) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < in.length; i++) {
            result = result.add(ifNullSet0(in[i]));
        }
        return result;
    }

    /**
     * 相除 取整 默认四舍五入
     *
     * @param n1 被除数
     * @param n2 除数
     * @return 结果
     */
    public static BigDecimal divide(Number n1, Number n2) {
        return divide(n1, n2, 0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 相除 指定小数位数 默认四舍五入
     *
     * @param n1    被除数
     * @param n2    除数
     * @param scale 保留小数点后位数
     * @return 结果
     */
    public static BigDecimal divide(Number n1, Number n2, int scale) {
        return divide(n1, n2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @param n1    被除数
     * @param n2    除数
     * @param scale 小数点后位数
     * @param mode  应用舍入模式
     * @return
     */
    public static BigDecimal divide(Number n1, Number n2, int scale, int mode) {
        if (n2 == null || new BigDecimal(n2 + "").compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("除数不能为空或者为0");
        }
        return new BigDecimal((n1 == null ? 0 : n1) + "").divide(new BigDecimal(n2 + ""), scale, mode);
    }


    /**
     * 相除 取整 默认四舍五入
     *
     * @param n1 被除数
     * @param n2 除数
     * @return 结果
     */
    public static BigDecimal divideMay0(Number n1, Number n2) {
        return divideMay0(n1, n2, 0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 相除 指定小数位数 默认四舍五入
     *
     * @param n1    被除数
     * @param n2    除数
     * @param scale 保留小数点后位数
     * @return 结果
     */
    public static BigDecimal divideMay0(Number n1, Number n2, int scale) {
        return divideMay0(n1, n2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide0(Number n1, Number n2, int scale) {
        BigDecimal bigDecimal = divideMay0(n1, n2, scale, BigDecimal.ROUND_HALF_UP);
        return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
    }

    /**
     * 相除；考虑到除数可能会为0或者null的情况，如果满足这一情况，直接返回null
     *
     * @param n1           被除数
     * @param n2           除数
     *                     
     * @param scale        小数点后位数
     * @param roundingMode 应用舍入模式
     * @return
     */
    public static BigDecimal divideMay0(Number n1, Number n2, int scale, int roundingMode) {
        if (n2 == null) return null;
        BigDecimal bn2 = new BigDecimal(n2 + "");
        if (new BigDecimal(n2 + "").compareTo(BigDecimal.ZERO) == 0) return null;
        return new BigDecimal((n1 == null ? 0 : n1) + "").divide(bn2, scale, roundingMode);
    }


    /**
     * 相减
     *
     * @param n1 被减数 为null按照0处理
     * @param n2 减数 为null按照0处理
     * @return
     */
    public static BigDecimal subtract(Number n1, Number n2) {
        return new BigDecimal((n1 == null ? 0 : n1) + "").subtract(new BigDecimal((n2 == null ? 0 : n2) + ""));
    }

    /**
     * 相乘
     *
     * @param numbers 为null按照0处理
     * @return
     */
    public static BigDecimal multiply(Number... numbers) {
        List<Number> nullArr = Arrays.stream(numbers).filter(Objects::isNull).collect(Collectors.toList());
        // 有空数据就直接返回 0
        if (CollectionUtils.isNotEmpty(nullArr)) return BigDecimal.ZERO;
        return Arrays.stream(numbers).map(item -> new BigDecimal(item + "")).reduce(BigDecimal::multiply).get();
    }


}
