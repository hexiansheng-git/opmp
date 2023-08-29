package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.mapper;

import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiry;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 设备进口策划 进口调查Mapper接口
 * 
 * @author zq
 * @date 2022-12-05
 */
public interface SbchImportInquiryMapper {
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
     * 删除设备进口策划 进口调查
     * 
     * @param id 设备进口策划 进口调查ID
     * @return 结果
     */
    int deleteSbchImportInquiryById(Long id);

    /**
     * 批量删除设备进口策划 进口调查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportInquiryByIds(@Param("ids") String[] ids, @Param("delUser") Long userId, @Param("delTime") Date date);
}
