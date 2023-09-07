package com.hhwy.system.currency.mapper;

import com.hhwy.domain.base.system.currency.CurrencyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * infoMapper接口
 * 
 * @author lcf
 * @date 2022-11-24
 */
public interface CurrencyInfoMapper {
    /**
     * 查询info
     * 
     * @param id infoID
     * @return info
     */
    CurrencyInfo selectCurrencyInfoById(Long id);

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
     * 删除info
     * 
     * @param id infoID
     * @return 结果
     */
    int deleteCurrencyInfoById(Long id);

    /**
     * 批量删除info
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCurrencyInfoByIds(String[] ids);

    /**
     * 根据currencyCode判断是否重复
     *
     * @param info
     * @return
     */
    List<CurrencyInfo> validRepeat(CurrencyInfo info);

    /**
     * 排序号最大
     *
     * @return
     */
    Integer selectMaxSort();

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int batchInsert(@Param(value = "dataList") List<CurrencyInfo> list);

    void deleteAll();
}
