package com.hhwy.pm.common.service;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物资策划通用业务类
 *
 * @author mls
 */
@Slf4j
public class CommonServiceUtil {
    private static CommonMapper commonMapper;
    private static SystemServiceApi systemServiceApi;

    static {
        systemServiceApi = SpringUtils.getBean(SystemServiceApi.class);
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



    /**
     * 获取美元价格
     *
     * @param names  当地币种价格
     * @return 美元价格
     */
    public static Map<String, CurrencyInfo> getCurrencyInfoByNames(List<String> names) {
        CurrencyInfo where = new CurrencyInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("currencyNames", String.join(",", names));
        where.setParams(params);
        where.setDelFlag("0");
        List<CurrencyInfo> currencyInfoList = systemServiceApi.selectCurrencyList(where);

        HashMap<String, CurrencyInfo> res = new HashMap<>(currencyInfoList.size());
        for (CurrencyInfo currencyInfo : currencyInfoList) {
            res.put(currencyInfo.getCurrencyName(),currencyInfo);
        }
        return res;
    }


    public static Map<String, String> getCurrencyCodesByNames(List<String> names) {
        CurrencyInfo where = new CurrencyInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("currencyNames", String.join(",", names));
        where.setParams(params);
        where.setDelFlag("0");
        List<CurrencyInfo> currencyInfoList = systemServiceApi.selectCurrencyList(where);

        HashMap<String, String> res = new HashMap<>(currencyInfoList.size());
        for (CurrencyInfo currencyInfo : currencyInfoList) {
            res.put(currencyInfo.getCurrencyName(),currencyInfo.getCurrencyCode());
        }
        return res;
    }


    public static Map<String, BigDecimal> getRateByCodes(List<String> codes) {
        
        // TODO 获取币种的汇率
        return new HashMap<>();
    }
}
