package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalTechnology;

import java.util.List;

/**
 * 跨国别设备调拨详情-技术评估Mapper接口
 * 
 * @author hwj
 * @date 2022-12-20
 */
public interface SbchEquipmentAllotTransnationalTechnologyMapper {
    /**
     * 查询跨国别设备调拨详情-技术评估
     * 
     * @param id 跨国别设备调拨详情-技术评估ID
     * @return 跨国别设备调拨详情-技术评估
     */
    SbchEquipmentAllotTransnationalTechnology selectSbchEquipmentAllotTransnationalTechnologyById(Long id);
    SbchEquipmentAllotTransnationalTechnology selectDetailsByMainId(Long id);

    /**
     * 查询跨国别设备调拨详情-技术评估列表
     * 
     * @param sbchEquipmentAllotTransnationalTechnology 跨国别设备调拨详情-技术评估
     * @return 跨国别设备调拨详情-技术评估集合
     */
    List<SbchEquipmentAllotTransnationalTechnology> selectSbchEquipmentAllotTransnationalTechnologyList(SbchEquipmentAllotTransnationalTechnology sbchEquipmentAllotTransnationalTechnology);

    /**
     * 新增跨国别设备调拨详情-技术评估
     * 
     * @param sbchEquipmentAllotTransnationalTechnology 跨国别设备调拨详情-技术评估
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalTechnology(SbchEquipmentAllotTransnationalTechnology sbchEquipmentAllotTransnationalTechnology);

    /**
     * 修改跨国别设备调拨详情-技术评估
     * 
     * @param sbchEquipmentAllotTransnationalTechnology 跨国别设备调拨详情-技术评估
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalTechnology(SbchEquipmentAllotTransnationalTechnology sbchEquipmentAllotTransnationalTechnology);

    /**
     * 删除跨国别设备调拨详情-技术评估
     * 
     * @param id 跨国别设备调拨详情-技术评估ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalTechnologyById(Long id);

    /**
     * 批量删除跨国别设备调拨详情-技术评估
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalTechnologyByIds(String[] ids);
    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
    int deleteDetailsByMainId(Long mainId);
    int batchInsert(List<SbchEquipmentAllotTransnationalTechnology> dataList);
}
