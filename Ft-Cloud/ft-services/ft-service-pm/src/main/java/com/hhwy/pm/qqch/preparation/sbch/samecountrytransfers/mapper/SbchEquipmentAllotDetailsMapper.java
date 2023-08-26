package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.mapper;


import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllotDetails;

import java.util.List;

/**
 * 同国别设备详情Mapper接口
 *
 * @author hwj
 * @date 2022-11-25
 */
public interface SbchEquipmentAllotDetailsMapper {
    /**
     * 查询同国别设备详情
     *
     * @param id 同国别设备详情ID
     * @return 同国别设备详情
     */
    SbchEquipmentAllotDetails selectSbchEquipmentAllotDetailsById(Long id);

    /**
     * 查询同国别设备详情列表
     *
     * @param sbchEquipmentAllotDetails 同国别设备详情
     * @return 同国别设备详情集合
     */
    List<SbchEquipmentAllotDetails> selectSbchEquipmentAllotDetailsList(SbchEquipmentAllotDetails sbchEquipmentAllotDetails);

    /**
     * 新增同国别设备详情
     *
     * @param sbchEquipmentAllotDetails 同国别设备详情
     * @return 结果
     */
    int insertSbchEquipmentAllotDetails(SbchEquipmentAllotDetails sbchEquipmentAllotDetails);

    /**
     * 修改同国别设备详情
     *
     * @param sbchEquipmentAllotDetails 同国别设备详情
     * @return 结果
     */
    int updateSbchEquipmentAllotDetails(SbchEquipmentAllotDetails sbchEquipmentAllotDetails);

    /**
     * 删除同国别设备详情
     *
     * @param id 同国别设备详情ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotDetailsById(Long id);

    /**
     * 批量删除同国别设备详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotDetailsByIds(String[] ids);

    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
//    int deleteSbchEquipmentPurchaseDetailsByMainId(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails);
    int deleteSbchEquipmentAllotDetailsByMainId(Long mainId);

    int batchInsert(List<SbchEquipmentAllotDetails> dataList);
}
