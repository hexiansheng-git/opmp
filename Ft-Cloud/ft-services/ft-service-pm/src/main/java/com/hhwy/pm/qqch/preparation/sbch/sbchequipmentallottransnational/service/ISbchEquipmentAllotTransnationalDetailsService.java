package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalDetails;

import java.util.List;

/**
 * 跨国别设备调拨详情Service接口
 * 
 * @author hwj
 * @date 2022-12-20
 */
public interface ISbchEquipmentAllotTransnationalDetailsService {
    /**
     * 查询跨国别设备调拨详情
     * 
     * @param id 跨国别设备调拨详情ID
     * @return 跨国别设备调拨详情
     */
    SbchEquipmentAllotTransnationalDetails selectSbchEquipmentAllotTransnationalDetailsById(Long id);

    /**
     * 查询跨国别设备调拨详情列表
     * 
     * @param sbchEquipmentAllotTransnationalDetails 跨国别设备调拨详情
     * @return 跨国别设备调拨详情集合
     */
    List<SbchEquipmentAllotTransnationalDetails> selectSbchEquipmentAllotTransnationalDetailsList(SbchEquipmentAllotTransnationalDetails sbchEquipmentAllotTransnationalDetails);

    /**
     * 新增跨国别设备调拨详情
     * 
     * @param sbchEquipmentAllotTransnationalDetails 跨国别设备调拨详情
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalDetails(SbchEquipmentAllotTransnationalDetails sbchEquipmentAllotTransnationalDetails);

    /**
     * 修改跨国别设备调拨详情
     * 
     * @param sbchEquipmentAllotTransnationalDetails 跨国别设备调拨详情
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalDetails(SbchEquipmentAllotTransnationalDetails sbchEquipmentAllotTransnationalDetails);

    /**
     * 批量删除跨国别设备调拨详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalDetailsByIds(String ids);

    /**
     * 删除跨国别设备调拨详情信息
     * 
     * @param id 跨国别设备调拨详情ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalDetailsById(Long id);
    int insertOrEditBatchByMainId(List<SbchEquipmentAllotTransnationalDetails> detailList, Long mainId, Boolean isAdjust);
}
