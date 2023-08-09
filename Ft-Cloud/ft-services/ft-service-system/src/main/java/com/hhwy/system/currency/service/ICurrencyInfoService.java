package com.hhwy.system.currency.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;

import java.util.List;


/**
 * infoService接口
 * 
 * @author lcf
 * @date 2022-11-24
 */
public interface ICurrencyInfoService {
    /**
     * 查询info
     * 
     * @param id infoID
     * @return info
     */
    CurrencyInfo selectCurrencyInfoById(Long id);

    CurrencyInfo selectCurrencyInfoByCode(String code);

    String selectCurrencyNameByCode(String code);

    /**
     * 查询info列表
     * 
     * @param currencyInfo info
     * @return info集合
     */
    List<CurrencyInfo> selectCurrencyInfoList(CurrencyInfo currencyInfo);

    /**
     * 新增info
     * 
     * @param currencyInfo info
     * @return 结果
     */
    int insertCurrencyInfo(CurrencyInfo currencyInfo);

    /**
     * 修改info
     * 
     * @param currencyInfo info
     * @return 结果
     */
    int updateCurrencyInfo(CurrencyInfo currencyInfo);

    /**
     * 批量删除info
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCurrencyInfoByIds(String ids);

    /**
     * 删除info信息
     * 
     * @param id infoID
     * @return 结果
     */
    int deleteCurrencyInfoById(Long id);

    /**
     * 查询最大的排序
     *
     * @return
     */
    int selectMaxSort();

    /**
     * 导入
     *
     * @param list
     * @return
     */
    AjaxResult importData(List<CurrencyInfo> list);

    /**
     * 期次 汇率设置
     *
     * @param periodCurrency
     * @return
     */
    List<PeriodCurrency> selectPeriodCurrency(PeriodCurrency periodCurrency);

    /**
     * 批量新增
     *
     * @param rstList
     */
    void batchInsert(List<CurrencyInfo> rstList);
}
