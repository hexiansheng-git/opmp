package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service;


import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiryCountry;

import java.util.List;

/**
 * 设备进口策划 进口调查-国家详情Service接口
 * 
 * @author zq
 * @date 2022-12-05
 */
public interface ISbchImportInquiryCountryService {
    /**
     * 查询设备进口策划 进口调查-国家详情
     * 
     * @param id 设备进口策划 进口调查-国家详情ID
     * @return 设备进口策划 进口调查-国家详情
     */
    SbchImportInquiryCountry selectSbchImportInquiryCountryById(Long id);

    /**
     * 查询设备进口策划 进口调查-国家详情列表
     * 
     * @param sbchImportInquiryCountry 设备进口策划 进口调查-国家详情
     * @return 设备进口策划 进口调查-国家详情集合
     */
    List<SbchImportInquiryCountry> selectSbchImportInquiryCountryList(SbchImportInquiryCountry sbchImportInquiryCountry);

    /**
     * 新增设备进口策划 进口调查-国家详情
     * 
     * @param sbchImportInquiryCountry 设备进口策划 进口调查-国家详情
     * @return 结果
     */
    int insertSbchImportInquiryCountry(SbchImportInquiryCountry sbchImportInquiryCountry);

    /**
     * 修改设备进口策划 进口调查-国家详情
     * 
     * @param sbchImportInquiryCountry 设备进口策划 进口调查-国家详情
     * @return 结果
     */
    int updateSbchImportInquiryCountry(SbchImportInquiryCountry sbchImportInquiryCountry);

    /**
     * 批量删除设备进口策划 进口调查-国家详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportInquiryCountryByIds(String ids);

    /**
     * 删除设备进口策划 进口调查-国家详情信息
     * 
     * @param id 设备进口策划 进口调查-国家详情ID
     * @return 结果
     */
    int deleteSbchImportInquiryCountryById(Long id);

    void batchInsert(List<SbchImportInquiryCountry> countryList);

    void deleteSbchImportInquiryCountryByInquiryId(Long id);
}
