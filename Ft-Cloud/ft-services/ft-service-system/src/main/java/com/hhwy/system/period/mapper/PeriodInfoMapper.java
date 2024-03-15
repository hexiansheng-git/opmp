package com.hhwy.system.period.mapper;


import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.period.vo.PeriodCurrencyInfoVo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * infoMapper接口
 * 
 * @author lcf
 * @date 2022-11-24
 */
public interface PeriodInfoMapper {
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
     * 修改info
     * 
     * @param periodInfo info
     * @return 结果
     */
    int updatePeriodInfo(PeriodInfo periodInfo);

    /**
     * 删除info
     * 
     * @param id infoID
     * @return 结果
     */
    int deletePeriodInfoById(Long id);

    /**
     * 批量删除info
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePeriodInfoByIds(String[] ids);

    /**
     * 获取期次
     * @param date
     * @return
     */
//    PeriodInfo selectPeriodByDatePure(Date date);

    /**
     * 根据日期获取币种、汇率、期次
     *
     * @param periodInfo
     * @return
     */
    List<PeriodCurrencyInfoVo> selectPeriodByDate(PeriodInfo periodInfo);

    /**
     * 根据期次编码查询期次信息
     *
     * @return
     */
    List<PeriodInfo> validRepeat(PeriodInfo periodInfo);

    /**
     * 查询库中已有的月份
     *
     * @return
     */
    PeriodInfo selectLastMonth();

    /**
     * 提供给融智
     * 根据月份拿汇率
     *
     * @param periodInfo
     * @return
     */
    List<PeriodCurrencyInfoVo> selectPeriodByMonth(PeriodInfo periodInfo);

    /**
     * 根据日期查询期次信息 只能查到一个
     *
     * @param date
     * @return
     */
    PeriodInfo selectPeriodInfoByDate(PeriodInfo date);

    /**
     * to国欣  （根据年查询改年所有的期次）
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
     * 数据批量新增
     *
     * @param list
     * @return
     */
    int batchInsert(@Param(value = "dataList") List<PeriodInfo> list);

    void deleteAll();

    List<PeriodCurrency> selectAllPeriodByYear(@Param("year") String year);
}
