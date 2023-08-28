package com.hhwy.pm.qqch.preparation.sbch.sblease.mapper;

import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentSupplierDetails;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-26 15:26
 */
public interface SbchEquipmentSupplierDetailsMapper {
    /**
     * 查询设备租赁供应商详情列表
     *
     * @param sbchEquipmentSupplierDetails 设备租赁供应商详情
     * @return 设备租赁供应商详情集合
     */
    List<SbchEquipmentSupplierDetails> selectSbchEquipmentSupplierDetailsList(SbchEquipmentSupplierDetails sbchEquipmentSupplierDetails);

    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
    int deleteSbchEquipmentSupplierDetailsByMainId(Long mainId);

    int batchInsert(List<SbchEquipmentSupplierDetails> dataList);


}
