package com.hhwy.pm.common.service;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 物资策划通用业务类
 *
 * @author mls
 */
@Slf4j
public class CommonServiceUtil {
    private static CommonMapper commonMapper;

    static {
        commonMapper = SpringUtils.getBean(CommonMapper.class);
    }

    /**
     * 获取美元价格
     *
     * @param amt  当地币种价格
     * @param rate 汇率
     * @return 美元价格
     */
    public static BigDecimal getUsdAmt(BigDecimal amt, BigDecimal rate) {
        return BigDecimalUtils.divideMay0(amt, rate, 4);
    }


}
