package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalClearance;

import java.util.List;

/**
 * 跨国别设备调拨详情-清关档案核查Service接口
 * 
 * @author hwj
 * @date 2022-12-20
 */
public interface ISbchEquipmentAllotTransnationalClearanceService {
    /**
     * 查询跨国别设备调拨详情-清关档案核查
     * 
     * @param id 跨国别设备调拨详情-清关档案核查ID
     * @return 跨国别设备调拨详情-清关档案核查
     */
    SbchEquipmentAllotTransnationalClearance selectSbchEquipmentAllotTransnationalClearanceById(Long id);

    /**
     * 查询跨国别设备调拨详情-清关档案核查列表
     * 
     * @param sbchEquipmentAllotTransnationalClearance 跨国别设备调拨详情-清关档案核查
     * @return 跨国别设备调拨详情-清关档案核查集合
     */
    List<SbchEquipmentAllotTransnationalClearance> selectSbchEquipmentAllotTransnationalClearanceList(SbchEquipmentAllotTransnationalClearance sbchEquipmentAllotTransnationalClearance);

    /**
     * 新增跨国别设备调拨详情-清关档案核查
     * 
     * @param sbchEquipmentAllotTransnationalClearance 跨国别设备调拨详情-清关档案核查
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalClearance(SbchEquipmentAllotTransnationalClearance sbchEquipmentAllotTransnationalClearance);

    /**
     * 修改跨国别设备调拨详情-清关档案核查
     * 
     * @param sbchEquipmentAllotTransnationalClearance 跨国别设备调拨详情-清关档案核查
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalClearance(SbchEquipmentAllotTransnationalClearance sbchEquipmentAllotTransnationalClearance);

    /**
     * 批量删除跨国别设备调拨详情-清关档案核查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalClearanceByIds(String ids);

    /**
     * 删除跨国别设备调拨详情-清关档案核查信息
     * 
     * @param id 跨国别设备调拨详情-清关档案核查ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalClearanceById(Long id);
}
