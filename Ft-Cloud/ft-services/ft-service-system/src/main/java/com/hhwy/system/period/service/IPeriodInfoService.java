package com.hhwy.system.period.service;


import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.period.vo.PeriodCurrencyInfoVo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;

import java.text.ParseException;
import java.util.List;

/**
 * infoService接口
 * 
 * @author lcf
 * @date 2022-11-24
 */
public interface IPeriodInfoService {
    /**
     * 查询info
     * 
     * @param id infoID
     * @return info
     */
    PeriodInfo selectPeriodInfoById(Long id);

    /**
     * 查询info列表
     * 
     * @param periodInfo info
     * @return info集合
     */
    List<PeriodInfo> selectPeriodInfoList(PeriodInfo periodInfo);

    /**
     * 新增info
     * 
     * @param periodInfo info
     * @return 结果
     */
    int insertPeriodInfo(PeriodInfo periodInfo);

    /**
     * 新增批次
     *
     * @param periodInfo
     * @return
     */
    Long insertPeriodInfoReturnId(PeriodInfo periodInfo);

    /**
     * 修改info
     * 
     * @param periodInfo info
     * @return 结果
     */
    int updatePeriodInfo(PeriodInfo periodInfo);

    /**
     * 批量删除info
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePeriodInfoByIds(String ids);

    /**
     * 删除info信息
     * 
     * @param id infoID
     * @return 结果
     */
    int deletePeriodInfoById(Long id);

    /**
     * 根据日期获取币种、汇率、期次
     *
     * @param periodInfo
     * @return
     */
    List<PeriodCurrencyInfoVo> selectPeriodByDate(PeriodInfo periodInfo);

    /**
     * 新增新增 20230306
     *
     * @return
     */
    int newAddPeriodInfo() throws ParseException;

    /**
     * 提供给融智
     * 根据月份拿汇率
     *
     * @param periodInfo
     * @return
     */
    List<PeriodCurrencyInfoVo> selectPeriodByMonth(PeriodInfo periodInfo);

    /**
     * 根据时间获取期次信息
     * @param date
     * @return
     */
    PeriodInfo selectPeriodInfoByDate(PeriodInfo date);

    /**
     * 根据年查询期次信息
     *
     * @param periodInfo
     * @return
     */
    List<PeriodCurrency> selectPeriodByYear(PeriodInfo periodInfo);

    /**
     * to张倩
     * 根据日期批量查询期次信息
     *
     * @return
     */
    List<PeriodInfo> selectBathByDate(List<String> list);

    /**
     * 批量新增批次
     *
     * @param list
     * @return
     */
    int batchInsert(List<PeriodInfo> list);

    /**
     * 批量新增批次
     *
     * @param list
     * @return
     */
    void dataSync(List<PeriodInfo> list);
}
