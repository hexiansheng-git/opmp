package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalPort;

import java.util.List;

/**
 * 跨国别设备调拨详情-港口调查Mapper接口
 * 
 * @author hwj
 * @date 2022-12-22
 */
public interface SbchEquipmentAllotTransnationalPortMapper {
    /**
     * 查询跨国别设备调拨详情-港口调查
     * 
     * @param id 跨国别设备调拨详情-港口调查ID
     * @return 跨国别设备调拨详情-港口调查
     */
    SbchEquipmentAllotTransnationalPort selectSbchEquipmentAllotTransnationalPortById(Long id);

    /**
     * 查询跨国别设备调拨详情-港口调查列表
     * 
     * @param sbchEquipmentAllotTransnationalPort 跨国别设备调拨详情-港口调查
     * @return 跨国别设备调拨详情-港口调查集合
     */
    List<SbchEquipmentAllotTransnationalPort> selectSbchEquipmentAllotTransnationalPortList(SbchEquipmentAllotTransnationalPort sbchEquipmentAllotTransnationalPort);

    /**
     * 新增跨国别设备调拨详情-港口调查
     * 
     * @param sbchEquipmentAllotTransnationalPort 跨国别设备调拨详情-港口调查
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalPort(SbchEquipmentAllotTransnationalPort sbchEquipmentAllotTransnationalPort);

    /**
     * 修改跨国别设备调拨详情-港口调查
     * 
     * @param sbchEquipmentAllotTransnationalPort 跨国别设备调拨详情-港口调查
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalPort(SbchEquipmentAllotTransnationalPort sbchEquipmentAllotTransnationalPort);

    /**
     * 删除跨国别设备调拨详情-港口调查
     * 
     * @param id 跨国别设备调拨详情-港口调查ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalPortById(Long id);

    /**
     * 批量删除跨国别设备调拨详情-港口调查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalPortByIds(String[] ids);
    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
    int deleteDetailsByMainId(Long mainId);
    int batchInsert(List<SbchEquipmentAllotTransnationalPort> dataList);
}
