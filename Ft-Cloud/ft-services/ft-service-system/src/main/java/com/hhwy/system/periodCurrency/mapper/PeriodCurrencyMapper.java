package com.hhwy.system.periodCurrency.mapper;

import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 期次汇率Mapper接口
 * 
 * @author lcf
 * @date 2022-11-24
 */
public interface PeriodCurrencyMapper {
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
     * 删除期次汇率
     * 
     * @param id 期次汇率ID
     * @return 结果
     */
    int deletePeriodCurrencyById(Long id);

    /**
     * 批量删除期次汇率
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePeriodCurrencyByIds(String[] ids);

    /**
     * 根据期次id删除该
     *
     * @param periodCurrency
     */
    int deletePeriodCurrencyByPeriodId(PeriodCurrency periodCurrency);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int batchInsert(@Param("dataList") List<PeriodCurrency> list);

    /**
     * 根据币种和期次号查询汇率
     * @param periodCode
     * @param currency
     * @return
     */
    List<PeriodCurrency> selectRatePeriodByCodeAndCurrent(@Param("currency")String currency, @Param("periodCode") String periodCode  );

    void deleteAll();
}
