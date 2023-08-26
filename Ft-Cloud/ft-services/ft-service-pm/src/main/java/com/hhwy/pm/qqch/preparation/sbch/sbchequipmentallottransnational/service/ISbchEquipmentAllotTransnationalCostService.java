package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalCost;

import java.util.List;

/**
 * 跨国别设备调拨详情-费用估算Service接口
 * 
 * @author hwj
 * @date 2022-12-22
 */
public interface ISbchEquipmentAllotTransnationalCostService {
    /**
     * 查询跨国别设备调拨详情-费用估算
     * 
     * @param id 跨国别设备调拨详情-费用估算ID
     * @return 跨国别设备调拨详情-费用估算
     */
    SbchEquipmentAllotTransnationalCost selectSbchEquipmentAllotTransnationalCostById(Long id);

    /**
     * 查询跨国别设备调拨详情-费用估算列表
     * 
     * @param sbchEquipmentAllotTransnationalCost 跨国别设备调拨详情-费用估算
     * @return 跨国别设备调拨详情-费用估算集合
     */
    List<SbchEquipmentAllotTransnationalCost> selectSbchEquipmentAllotTransnationalCostList(SbchEquipmentAllotTransnationalCost sbchEquipmentAllotTransnationalCost);

    /**
     * 新增跨国别设备调拨详情-费用估算
     * 
     * @param sbchEquipmentAllotTransnationalCost 跨国别设备调拨详情-费用估算
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalCost(SbchEquipmentAllotTransnationalCost sbchEquipmentAllotTransnationalCost);

    /**
     * 修改跨国别设备调拨详情-费用估算
     * 
     * @param sbchEquipmentAllotTransnationalCost 跨国别设备调拨详情-费用估算
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalCost(SbchEquipmentAllotTransnationalCost sbchEquipmentAllotTransnationalCost);

    /**
     * 批量删除跨国别设备调拨详情-费用估算
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalCostByIds(String ids);

    /**
     * 删除跨国别设备调拨详情-费用估算信息
     * 
     * @param id 跨国别设备调拨详情-费用估算ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalCostById(Long id);
}
