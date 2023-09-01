package com.hhwy.system.country.mapper;


import com.hhwy.domain.base.system.country.CountryInfo;

import java.util.List;

/**
 * 国别Mapper接口
 * 
 * @author jzq
 * @date 2022-11-01
 */
public interface CountryInfoMapper {
    /**
     * 查询国别
     * 
     * @param id 国别ID
     * @return 国别
     */
    CountryInfo selectCountryInfoById(Long id);
    /**
     * 查询国别
     *
     * @param countryName 国别name
     * @return 国别
     */
    CountryInfo selectCountryInfoByName(String countryName);

    /**
     * 查询国别列表
     * 
     * @param tCountryInfo 国别
     * @return 国别集合
     */
    List<CountryInfo> selectCountryInfoList(CountryInfo tCountryInfo);

    /**
     * 新增国别
     * 
     * @param tCountryInfo 国别
     * @return 结果
     */
    int insertCountryInfo(CountryInfo tCountryInfo);

    /**
     * 修改国别
     * 
     * @param tCountryInfo 国别
     * @return 结果
     */
    int updateCountryInfo(CountryInfo tCountryInfo);

    /**
     * 删除国别
     * 
     * @param id 国别ID
     * @return 结果
     */
    int deleteCountryInfoById(Long id);

    /**
     * 批量删除国别
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCountryInfoByIds(String[] ids);

    List<CountryInfo> selectCountryInfoByNames(String name);
}
