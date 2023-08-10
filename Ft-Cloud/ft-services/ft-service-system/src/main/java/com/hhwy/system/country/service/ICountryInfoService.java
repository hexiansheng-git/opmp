package com.hhwy.system.country.service;


import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.domain.base.system.country.CountryInfo;

import java.util.List;

/**
 * 国别Service接口
 * 
 * @author jzq
 * @date 2022-11-01
 */
public interface ICountryInfoService {
    /**
     * 查询国别
     * 
     * @param id 国别ID
     * @return 国别
     */
    CountryInfo selectCountryInfoById(Long id);

    /**
     * 查询国别列表
     * 
     * @param countryInfo 国别
     * @return 国别集合
     */
    List<CountryInfo> selectCountryInfoList(CountryInfo countryInfo);

    /**
     * 新增国别
     * 
     * @param countryInfo 国别
     * @return 结果
     */
    int insertCountryInfo(CountryInfo countryInfo);

    /**
     * 修改国别
     * 
     * @param countryInfo 国别
     * @return 结果
     */
    int updateCountryInfo(CountryInfo countryInfo);

    /**
     * 批量删除国别
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCountryInfoByIds(String ids);

    /**
     * 删除国别信息
     * 
     * @param id 国别ID
     * @return 结果
     */
    int deleteCountryInfoById(Long id);

//    /**
//     * 国家下的项目（级联）
//     *
//     * @param countryInfo
//     * @return
//     */
//    List<SysTreeUtil> countryProjectCascade(CountryInfo countryInfo);
}
