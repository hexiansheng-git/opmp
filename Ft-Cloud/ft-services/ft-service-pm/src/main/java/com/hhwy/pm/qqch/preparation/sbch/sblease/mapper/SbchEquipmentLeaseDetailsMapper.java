package com.hhwy.pm.qqch.preparation.sbch.sblease.mapper;

import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentLeaseDetails;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-26 15:25
 */
public interface SbchEquipmentLeaseDetailsMapper {
    /**
     * 查询设备租赁详情列表
     *
     * @param sbchEquipmentLeaseDetails 设备租赁详情
     * @return 设备租赁详情集合
     */
    List<SbchEquipmentLeaseDetails> selectSbchEquipmentLeaseDetailsList(SbchEquipmentLeaseDetails sbchEquipmentLeaseDetails);
    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
    int deleteSbchEquipmentLeaseDetailsByMainId(Long mainId);
    int batchInsert(List<SbchEquipmentLeaseDetails> dataList);
}
