package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalBulk;

import java.util.List;

/**
 * 跨国别设备调拨详情-散货运输方案Service接口
 * 
 * @author hwj
 * @date 2022-12-20
 */
public interface ISbchEquipmentAllotTransnationalBulkService {
    /**
     * 查询跨国别设备调拨详情-散货运输方案
     * 
     * @param id 跨国别设备调拨详情-散货运输方案ID
     * @return 跨国别设备调拨详情-散货运输方案
     */
    SbchEquipmentAllotTransnationalBulk selectSbchEquipmentAllotTransnationalBulkById(Long id);

    /**
     * 查询跨国别设备调拨详情-散货运输方案列表
     * 
     * @param sbchEquipmentAllotTransnationalBulk 跨国别设备调拨详情-散货运输方案
     * @return 跨国别设备调拨详情-散货运输方案集合
     */
    List<SbchEquipmentAllotTransnationalBulk> selectSbchEquipmentAllotTransnationalBulkList(SbchEquipmentAllotTransnationalBulk sbchEquipmentAllotTransnationalBulk);

    /**
     * 新增跨国别设备调拨详情-散货运输方案
     * 
     * @param sbchEquipmentAllotTransnationalBulk 跨国别设备调拨详情-散货运输方案
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalBulk(SbchEquipmentAllotTransnationalBulk sbchEquipmentAllotTransnationalBulk);

    /**
     * 修改跨国别设备调拨详情-散货运输方案
     * 
     * @param sbchEquipmentAllotTransnationalBulk 跨国别设备调拨详情-散货运输方案
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalBulk(SbchEquipmentAllotTransnationalBulk sbchEquipmentAllotTransnationalBulk);

    /**
     * 批量删除跨国别设备调拨详情-散货运输方案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalBulkByIds(String ids);

    /**
     * 删除跨国别设备调拨详情-散货运输方案信息
     * 
     * @param id 跨国别设备调拨详情-散货运输方案ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalBulkById(Long id);
}
