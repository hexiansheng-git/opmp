package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalExit;

import java.util.List;

/**
 * 跨国别设备调拨详情-再出口调查Mapper接口
 * 
 * @author hwj
 * @date 2022-12-22
 */
public interface SbchEquipmentAllotTransnationalExitMapper {
    /**
     * 查询跨国别设备调拨详情-再出口调查
     * 
     * @param id 跨国别设备调拨详情-再出口调查ID
     * @return 跨国别设备调拨详情-再出口调查
     */
    SbchEquipmentAllotTransnationalExit selectSbchEquipmentAllotTransnationalExitById(Long id);

    /**
     * 查询跨国别设备调拨详情-再出口调查列表
     * 
     * @param sbchEquipmentAllotTransnationalExit 跨国别设备调拨详情-再出口调查
     * @return 跨国别设备调拨详情-再出口调查集合
     */
    List<SbchEquipmentAllotTransnationalExit> selectSbchEquipmentAllotTransnationalExitList(SbchEquipmentAllotTransnationalExit sbchEquipmentAllotTransnationalExit);

    /**
     * 新增跨国别设备调拨详情-再出口调查
     * 
     * @param sbchEquipmentAllotTransnationalExit 跨国别设备调拨详情-再出口调查
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalExit(SbchEquipmentAllotTransnationalExit sbchEquipmentAllotTransnationalExit);

    /**
     * 修改跨国别设备调拨详情-再出口调查
     * 
     * @param sbchEquipmentAllotTransnationalExit 跨国别设备调拨详情-再出口调查
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalExit(SbchEquipmentAllotTransnationalExit sbchEquipmentAllotTransnationalExit);

    /**
     * 删除跨国别设备调拨详情-再出口调查
     * 
     * @param id 跨国别设备调拨详情-再出口调查ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalExitById(Long id);

    /**
     * 批量删除跨国别设备调拨详情-再出口调查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalExitByIds(String[] ids);
    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
    int deleteDetailsByMainId(Long mainId);
    int batchInsert(List<SbchEquipmentAllotTransnationalExit> dataList);
}
