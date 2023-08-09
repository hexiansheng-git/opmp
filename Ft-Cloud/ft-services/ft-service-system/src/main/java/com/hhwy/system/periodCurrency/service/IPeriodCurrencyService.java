package com.hhwy.system.periodCurrency.service;


import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;

import java.util.List;
import java.util.Map;

/**
 * 期次汇率Service接口
 * 
 * @author lcf
 * @date 2022-11-24
 */
public interface IPeriodCurrencyService {
    /**
     * 查询期次汇率
     * 
     * @param id 期次汇率ID
     * @return 期次汇率
     */
    PeriodCurrency selectPeriodCurrencyById(Long id);

    /**
     * 查询期次汇率列表
     * 
     * @param periodCurrency 期次汇率
     * @return 期次汇率集合
     */
    List<PeriodCurrency> selectPeriodCurrencyList(PeriodCurrency periodCurrency);

    /**
     * 新增期次汇率
     * 
     * @param periodCurrency 期次汇率
     * @return 结果
     */
    int insertPeriodCurrency(PeriodCurrency periodCurrency);

    /**
     * 修改期次汇率
     * 
     * @param periodCurrency 期次汇率
     * @return 结果
     */
    int updatePeriodCurrency(PeriodCurrency periodCurrency);

    /**
     * 批量删除期次汇率
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePeriodCurrencyByIds(String ids);

    /**
     * 删除期次汇率信息
     * 
     * @param id 期次汇率ID
     * @return 结果
     */
    int deletePeriodCurrencyById(Long id);

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    PeriodCurrency editPageInfo(Long id);

    /**
     * 批量插入
     *
     * @param periodInfo
     * @return
     */
    int insertBathPeriodCurrency(PeriodInfo periodInfo);


    /**
     * 根据币种编码和期次号查询汇率
     *
     * @param map
     * @return
     */
    List<PeriodCurrency> selectRatePeriodByCodeAndCurrent(Map<String, String> map);

    /**
     * 批量新增汇率
     *
     * @param list
     * @return
     */
    int batchInsert(List<PeriodCurrency> list);
}
