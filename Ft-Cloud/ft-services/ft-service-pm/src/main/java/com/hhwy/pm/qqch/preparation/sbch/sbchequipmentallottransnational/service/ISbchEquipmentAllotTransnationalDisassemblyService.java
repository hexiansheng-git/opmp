package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalDisassembly;

import java.util.List;

/**
 * 跨国别设备调拨详情-拆卸吊装方案Service接口
 * 
 * @author hwj
 * @date 2022-12-20
 */
public interface ISbchEquipmentAllotTransnationalDisassemblyService {
    /**
     * 查询跨国别设备调拨详情-拆卸吊装方案
     * 
     * @param id 跨国别设备调拨详情-拆卸吊装方案ID
     * @return 跨国别设备调拨详情-拆卸吊装方案
     */
    SbchEquipmentAllotTransnationalDisassembly selectSbchEquipmentAllotTransnationalDisassemblyById(Long id);

    /**
     * 查询跨国别设备调拨详情-拆卸吊装方案列表
     * 
     * @param sbchEquipmentAllotTransnationalDisassembly 跨国别设备调拨详情-拆卸吊装方案
     * @return 跨国别设备调拨详情-拆卸吊装方案集合
     */
    List<SbchEquipmentAllotTransnationalDisassembly> selectSbchEquipmentAllotTransnationalDisassemblyList(SbchEquipmentAllotTransnationalDisassembly sbchEquipmentAllotTransnationalDisassembly);

    /**
     * 新增跨国别设备调拨详情-拆卸吊装方案
     * 
     * @param sbchEquipmentAllotTransnationalDisassembly 跨国别设备调拨详情-拆卸吊装方案
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalDisassembly(SbchEquipmentAllotTransnationalDisassembly sbchEquipmentAllotTransnationalDisassembly);

    /**
     * 修改跨国别设备调拨详情-拆卸吊装方案
     * 
     * @param sbchEquipmentAllotTransnationalDisassembly 跨国别设备调拨详情-拆卸吊装方案
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalDisassembly(SbchEquipmentAllotTransnationalDisassembly sbchEquipmentAllotTransnationalDisassembly);

    /**
     * 批量删除跨国别设备调拨详情-拆卸吊装方案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalDisassemblyByIds(String ids);

    /**
     * 删除跨国别设备调拨详情-拆卸吊装方案信息
     * 
     * @param id 跨国别设备调拨详情-拆卸吊装方案ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalDisassemblyById(Long id);
}
