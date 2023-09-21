package com.hhwy.pm.common.service;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.utils.ParamUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.field.FieldUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
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
     * @param names 当地币种价格
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
            res.put(currencyInfo.getCurrencyName(), currencyInfo);
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
            res.put(currencyInfo.getCurrencyName(), currencyInfo.getCurrencyCode());
        }
        return res;
    }


    /**
     * 设置币种名称
     *
     * @param tList
     * @param currencyFiledName
     * @param currencyNameFiledName
     * @param <T>
     */
    public static <T> void setCurrentName(List<T> tList, String currencyFiledName, String currencyNameFiledName) {
        StringBuilder codes = new StringBuilder();
        FieldUtils fieldUtils = FieldUtils.init();

        if (CollectionUtils.isEmpty(tList)) return;
        try {
            // 获取所有的币种编码
            for (T t : tList) {
                // 获取字段值
                Object fieldValue = fieldUtils.getFieldVal(currencyFiledName, t);
                codes.append(fieldValue).append(",");
            }

            // 获取币种信息
            CurrencyInfo currencyInfo = new CurrencyInfo();
            currencyInfo.setParams(ParamUtils.init().add("currencyCodes", codes.toString()).get());

            List<CurrencyInfo> currencyInfos = systemServiceApi.selectCurrencyList(currencyInfo);
            for (T t : tList) {
                // 获取字段值
                String finalCode = String.valueOf(fieldUtils.getFieldVal(currencyFiledName, t));

                currencyInfos.stream().filter(item -> finalCode != null && finalCode.equals(item.getCurrencyCode())).findFirst().ifPresent(curr -> {
                    // 获取到币种名称并进行设置
                    fieldUtils.setFieldVal(currencyNameFiledName, curr.getCurrencyName(), t);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static <T> void setCurrentName(List<T> tList) {
        setCurrentName(tList, "currency", "currencyName");
    }

    public static Map<String, BigDecimal> getUsdRate(String periodCode, List<String> currencyList) {
        try {
            Map<String, String> map = new HashMap<>(2);
            map.put("currency", String.join(",", currencyList));
            map.put("periodCode", periodCode);
            AjaxResult ajaxResult = systemServiceApi.selectListRatePeriodByCodeAndCurrent(map);
            List<PeriodCurrency> periodCurrencies = JSONObject.parseArray(JSONObject.toJSONString(ajaxResult.get(AjaxResult.DATA_TAG)), PeriodCurrency.class);
            return periodCurrencies.stream().collect(Collectors.toMap(PeriodCurrency::getCurrencyCode, PeriodCurrency::getRate));
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }

    }
   
    
    public static Map<String, BigDecimal> getUsdRate(List<String> currencyList) {
        
        try {
            Map<String, String> map = new HashMap<>(2);
            map.put("currency", String.join(",", currencyList));
            map.put("periodDate", FtDateUtils.formatDate(new Date()));
            AjaxResult ajaxResult = systemServiceApi.selectListRatePeriodByCodeAndCurrent(map);
            List<PeriodCurrency> periodCurrencies = JSONObject.parseArray(JSONObject.toJSONString(ajaxResult.get(AjaxResult.DATA_TAG)), PeriodCurrency.class);
            return periodCurrencies.stream().collect(Collectors.toMap(PeriodCurrency::getCurrencyCode, PeriodCurrency::getRate));
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }

    }
}
