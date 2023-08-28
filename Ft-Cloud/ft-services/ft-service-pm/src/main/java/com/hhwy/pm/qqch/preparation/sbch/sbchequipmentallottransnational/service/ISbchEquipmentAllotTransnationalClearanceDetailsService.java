package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalClearanceDetails;

import java.util.List;

/**
 * 跨国别设备调拨详情-清关档案核查-清关档案核查明细Service接口
 * 
 * @author hwj
 * @date 2022-12-20
 */
public interface ISbchEquipmentAllotTransnationalClearanceDetailsService {
    /**
     * 查询跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * 
     * @param id 跨国别设备调拨详情-清关档案核查-清关档案核查明细ID
     * @return 跨国别设备调拨详情-清关档案核查-清关档案核查明细
     */
    SbchEquipmentAllotTransnationalClearanceDetails selectSbchEquipmentAllotTransnationalClearanceDetailsById(Long id);

    /**
     * 查询跨国别设备调拨详情-清关档案核查-清关档案核查明细列表
     * 
     * @param sbchEquipmentAllotTransnationalClearanceDetails 跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * @return 跨国别设备调拨详情-清关档案核查-清关档案核查明细集合
     */
    List<SbchEquipmentAllotTransnationalClearanceDetails> selectSbchEquipmentAllotTransnationalClearanceDetailsList(SbchEquipmentAllotTransnationalClearanceDetails sbchEquipmentAllotTransnationalClearanceDetails);

    /**
     * 新增跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * 
     * @param sbchEquipmentAllotTransnationalClearanceDetails 跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalClearanceDetails(SbchEquipmentAllotTransnationalClearanceDetails sbchEquipmentAllotTransnationalClearanceDetails);

    /**
     * 修改跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * 
     * @param sbchEquipmentAllotTransnationalClearanceDetails 跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalClearanceDetails(SbchEquipmentAllotTransnationalClearanceDetails sbchEquipmentAllotTransnationalClearanceDetails);

    /**
     * 批量删除跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalClearanceDetailsByIds(String ids);

    /**
     * 删除跨国别设备调拨详情-清关档案核查-清关档案核查明细信息
     * 
     * @param id 跨国别设备调拨详情-清关档案核查-清关档案核查明细ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalClearanceDetailsById(Long id);
}
