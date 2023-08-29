package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service;


import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiry;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 设备进口策划 进口调查Service接口
 * 
 * @author zq
 * @date 2022-12-05
 */
public interface ISbchImportInquiryService {
    /**
     * 查询设备进口策划 进口调查
     * 
     * @param id 设备进口策划 进口调查ID
     * @return 设备进口策划 进口调查
     */
    SbchImportInquiry selectSbchImportInquiryById(Long id);

    /**
     * 查询设备进口策划 进口调查列表
     * 
     * @param sbchImportInquiry 设备进口策划 进口调查
     * @return 设备进口策划 进口调查集合
     */
    List<SbchImportInquiry> selectSbchImportInquiryList(SbchImportInquiry sbchImportInquiry);

    /**
     * 新增设备进口策划 进口调查
     * 
     * @param sbchImportInquiry 设备进口策划 进口调查
     * @return 结果
     */
    int insertSbchImportInquiry(SbchImportInquiry sbchImportInquiry);

    /**
     * 修改设备进口策划 进口调查
     * 
     * @param sbchImportInquiry 设备进口策划 进口调查
     * @return 结果
     */
    int updateSbchImportInquiry(SbchImportInquiry sbchImportInquiry);

    /**
     * 批量删除设备进口策划 进口调查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportInquiryByIds(String ids);

    /**
     * 删除设备进口策划 进口调查信息
     * 
     * @param id 设备进口策划 进口调查ID
     * @return 结果
     */
    int deleteSbchImportInquiryById(Long id);

    Map selectInquiryDetailList(Long id);

    SbchImportInquiry getList(BigDecimal version);

    void batchSave(SbchImportInquiry sbchImportInquiry);
}
