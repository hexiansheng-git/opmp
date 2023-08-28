package com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.service;


import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.domain.SbchEquipmentLocalPurchaseDetails;

import java.util.List;

/**
 * 设备申购管理详情Service接口
 * 
 * @author hwj
 * @date 2022-11-22
 */
public interface ISbchEquipmentLocalPurchaseDetailsService {
    /**
     * 查询设备申购管理详情
     * 
     * @param id 设备申购管理详情ID
     * @return 设备申购管理详情
     */
    SbchEquipmentLocalPurchaseDetails selectSbchEquipmentPurchaseDetailsById(Long id);

    /**
     * 查询设备申购管理详情列表
     * 
     * @param sbchEquipmentPurchaseDetails 设备申购管理详情
     * @return 设备申购管理详情集合
     */
    List<SbchEquipmentLocalPurchaseDetails> selectSbchEquipmentPurchaseDetailsList(SbchEquipmentLocalPurchaseDetails sbchEquipmentPurchaseDetails);

    /**
     * 新增设备申购管理详情
     * 
     * @param sbchEquipmentPurchaseDetails 设备申购管理详情
     * @return 结果
     */
    int insertSbchEquipmentPurchaseDetails(SbchEquipmentLocalPurchaseDetails sbchEquipmentPurchaseDetails);

    /**
     * 修改设备申购管理详情
     * 
     * @param sbchEquipmentPurchaseDetails 设备申购管理详情
     * @return 结果
     */
    int updateSbchEquipmentPurchaseDetails(SbchEquipmentLocalPurchaseDetails sbchEquipmentPurchaseDetails);

    /**
     * 批量删除设备申购管理详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentPurchaseDetailsByIds(String ids);

    /**
     * 删除设备申购管理详情信息
     * 
     * @param id 设备申购管理详情ID
     * @return 结果
     */
    int deleteSbchEquipmentPurchaseDetailsById(Long id);

    /**
     * 新增和编辑 数据保存
     * @return 结果
     */
    int insertOrEditBatchByMainId(List<SbchEquipmentLocalPurchaseDetails> list, Long mainId, Boolean isAdjus);
}
