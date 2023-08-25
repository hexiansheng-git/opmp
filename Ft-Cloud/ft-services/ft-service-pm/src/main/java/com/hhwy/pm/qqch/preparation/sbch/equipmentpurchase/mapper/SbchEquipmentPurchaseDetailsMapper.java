package com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.mapper;

import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchaseDetails;

import java.util.List;

/**
 * 设备申购管理详情Mapper接口
 * 
 * @author hwj
 * @date 2022-11-22
 */
public interface SbchEquipmentPurchaseDetailsMapper {
    /**
     * 查询设备申购管理详情
     * 
     * @param id 设备申购管理详情ID
     * @return 设备申购管理详情
     */
    SbchEquipmentPurchaseDetails selectSbchEquipmentPurchaseDetailsById(Long id);

    /**
     * 查询设备申购管理详情列表
     * 
     * @param sbchEquipmentPurchaseDetails 设备申购管理详情
     * @return 设备申购管理详情集合
     */
    List<SbchEquipmentPurchaseDetails> selectSbchEquipmentPurchaseDetailsList(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails);

    /**
     * 新增设备申购管理详情
     * 
     * @param sbchEquipmentPurchaseDetails 设备申购管理详情
     * @return 结果
     */
    int insertSbchEquipmentPurchaseDetails(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails);

    /**
     * 修改设备申购管理详情
     * 
     * @param sbchEquipmentPurchaseDetails 设备申购管理详情
     * @return 结果
     */
    int updateSbchEquipmentPurchaseDetails(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails);

    /**
     * 删除设备申购管理详情
     * 
     * @param id 设备申购管理详情ID
     * @return 结果
     */
    int deleteSbchEquipmentPurchaseDetailsById(Long id);

    /**
     * 批量删除设备申购管理详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentPurchaseDetailsByIds(String[] ids);

    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
//    int deleteSbchEquipmentPurchaseDetailsByMainId(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails);
    int deleteSbchEquipmentPurchaseDetailsByMainId(Long mainId);

    int batchInsert(List<SbchEquipmentPurchaseDetails> dataList);
}
