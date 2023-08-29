package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service;


import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiryCustoms;

import java.util.List;

/**
 * 设备进口策划 进口调查-港口详情Service接口
 * 
 * @author zq
 * @date 2022-12-05
 */
public interface ISbchImportInquiryCustomsService {
    /**
     * 查询设备进口策划 进口调查-港口详情
     * 
     * @param id 设备进口策划 进口调查-港口详情ID
     * @return 设备进口策划 进口调查-港口详情
     */
    SbchImportInquiryCustoms selectSbchImportInquiryCustomsById(Long id);

    /**
     * 查询设备进口策划 进口调查-港口详情列表
     * 
     * @param sbchImportInquiryCustoms 设备进口策划 进口调查-港口详情
     * @return 设备进口策划 进口调查-港口详情集合
     */
    List<SbchImportInquiryCustoms> selectSbchImportInquiryCustomsList(SbchImportInquiryCustoms sbchImportInquiryCustoms);

    /**
     * 新增设备进口策划 进口调查-港口详情
     * 
     * @param sbchImportInquiryCustoms 设备进口策划 进口调查-港口详情
     * @return 结果
     */
    int insertSbchImportInquiryCustoms(SbchImportInquiryCustoms sbchImportInquiryCustoms);

    /**
     * 修改设备进口策划 进口调查-港口详情
     * 
     * @param sbchImportInquiryCustoms 设备进口策划 进口调查-港口详情
     * @return 结果
     */
    int updateSbchImportInquiryCustoms(SbchImportInquiryCustoms sbchImportInquiryCustoms);

    /**
     * 批量删除设备进口策划 进口调查-港口详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportInquiryCustomsByIds(String ids);

    /**
     * 删除设备进口策划 进口调查-港口详情信息
     * 
     * @param id 设备进口策划 进口调查-港口详情ID
     * @return 结果
     */
    int deleteSbchImportInquiryCustomsById(Long id);

    void batchInsert(List<SbchImportInquiryCustoms> customsList);

    void deleteSbchImportInquiryCustomsByInquiryId(Long id);
}
