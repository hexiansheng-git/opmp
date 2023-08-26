package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalContainer;

import java.util.List;

/**
 * 跨国别设备调拨详情-集装箱运输方案Mapper接口
 * 
 * @author hwj
 * @date 2022-12-22
 */
public interface SbchEquipmentAllotTransnationalContainerMapper {
    /**
     * 查询跨国别设备调拨详情-集装箱运输方案
     * 
     * @param id 跨国别设备调拨详情-集装箱运输方案ID
     * @return 跨国别设备调拨详情-集装箱运输方案
     */
    SbchEquipmentAllotTransnationalContainer selectSbchEquipmentAllotTransnationalContainerById(Long id);

    /**
     * 查询跨国别设备调拨详情-集装箱运输方案列表
     * 
     * @param sbchEquipmentAllotTransnationalContainer 跨国别设备调拨详情-集装箱运输方案
     * @return 跨国别设备调拨详情-集装箱运输方案集合
     */
    List<SbchEquipmentAllotTransnationalContainer> selectSbchEquipmentAllotTransnationalContainerList(SbchEquipmentAllotTransnationalContainer sbchEquipmentAllotTransnationalContainer);

    /**
     * 新增跨国别设备调拨详情-集装箱运输方案
     * 
     * @param sbchEquipmentAllotTransnationalContainer 跨国别设备调拨详情-集装箱运输方案
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnationalContainer(SbchEquipmentAllotTransnationalContainer sbchEquipmentAllotTransnationalContainer);

    /**
     * 修改跨国别设备调拨详情-集装箱运输方案
     * 
     * @param sbchEquipmentAllotTransnationalContainer 跨国别设备调拨详情-集装箱运输方案
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnationalContainer(SbchEquipmentAllotTransnationalContainer sbchEquipmentAllotTransnationalContainer);

    /**
     * 删除跨国别设备调拨详情-集装箱运输方案
     * 
     * @param id 跨国别设备调拨详情-集装箱运输方案ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalContainerById(Long id);

    /**
     * 批量删除跨国别设备调拨详情-集装箱运输方案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalContainerByIds(String[] ids);
    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
    int deleteDetailsByMainId(Long mainId);
    int batchInsert(List<SbchEquipmentAllotTransnationalContainer> dataList);
}
