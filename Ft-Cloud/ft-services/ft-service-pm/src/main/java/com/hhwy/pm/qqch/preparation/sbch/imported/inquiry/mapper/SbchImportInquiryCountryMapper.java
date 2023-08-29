package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.mapper;

import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiryCountry;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 设备进口策划 进口调查-国家详情Mapper接口
 * 
 * @author zq
 * @date 2022-12-05
 */
public interface SbchImportInquiryCountryMapper {
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
     * 删除设备进口策划 进口调查-国家详情
     * 
     * @param id 设备进口策划 进口调查-国家详情ID
     * @return 结果
     */
    int deleteSbchImportInquiryCountryById(Long id);

    /**
     * 批量删除设备进口策划 进口调查-国家详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportInquiryCountryByIds(String[] ids);

    void batchInsert(@Param("countryList") List<SbchImportInquiryCountry> countryList);

    void deleteSbchImportInquiryCountryByInquiryId(@Param("inquiryId") Long id, @Param("userId") Long userId, @Param("date") Date date);
}
