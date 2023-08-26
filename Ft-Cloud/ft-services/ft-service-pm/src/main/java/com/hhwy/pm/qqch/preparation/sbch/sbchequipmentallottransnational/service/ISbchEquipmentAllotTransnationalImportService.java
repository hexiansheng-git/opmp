package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalImport;

import java.util.List;

/**
 * 跨国别设备调拨详情-进口调查Service接口
 * 
 * @author hwj
 * @date 2022-12-22
 */
public interface ISbchEquipmentAllotTransnationalImportService {
    /**
     * 查询跨国别设备调拨详情-进口调查
     * 
     * @param id 跨国别设备调拨详情-进口调查ID
     * @return 跨国别设备调拨详情-进口调查
     */
    SbchEquipmentAllotTransnationalImport selectSbchEquipmentAllotTransnationalImportById(Long id);

    /**
     * 查询跨国别设备调拨详情-进口调查列表
     * 
     * @param sbchEquipmentAllotTransnationalImport 跨国别设备调拨详情-进口调查
     * @return 跨国别设备调拨详情-进口调查集合
     */
    List<SbchEquipmentAllotTransnationalImport> selectSbchEquipmentAllotTransnationalImportList(SbchEquipmentAllotTransnationalImport sbchEquipmentAllotTransnationalImport);

    /**
     * 新增跨国别设备调拨详情-进口调查
     * 
     * @param sbchEquipmentAllotTransnationalImport 跨国别设备调拨详情-进口调查
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalImport(SbchEquipmentAllotTransnationalImport sbchEquipmentAllotTransnationalImport);

    /**
     * 修改跨国别设备调拨详情-进口调查
     * 
     * @param sbchEquipmentAllotTransnationalImport 跨国别设备调拨详情-进口调查
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalImport(SbchEquipmentAllotTransnationalImport sbchEquipmentAllotTransnationalImport);

    /**
     * 批量删除跨国别设备调拨详情-进口调查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalImportByIds(String ids);

    /**
     * 删除跨国别设备调拨详情-进口调查信息
     * 
     * @param id 跨国别设备调拨详情-进口调查ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalImportById(Long id);
}
