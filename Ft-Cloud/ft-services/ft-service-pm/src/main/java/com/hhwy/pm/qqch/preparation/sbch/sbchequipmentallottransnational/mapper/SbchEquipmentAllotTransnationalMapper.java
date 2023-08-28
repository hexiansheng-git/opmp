package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper;

import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnational;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 跨国别设备调拨Mapper接口
 * 
 * @author hwj
 * @date 2022-12-20
 */
public interface SbchEquipmentAllotTransnationalMapper {
    /**
     * 查询跨国别设备调拨
     * 
     * @param id 跨国别设备调拨ID
     * @return 跨国别设备调拨
     */
    SbchEquipmentAllotTransnational selectSbchEquipmentAllotTransnationalById(Long id);

    /**
     * 查询跨国别设备调拨列表
     * 
     * @param sbchEquipmentAllotTransnational 跨国别设备调拨
     * @return 跨国别设备调拨集合
     */
    List<SbchEquipmentAllotTransnational> selectSbchEquipmentAllotTransnationalList(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational);

    /**
     * 新增跨国别设备调拨
     * 
     * @param sbchEquipmentAllotTransnational 跨国别设备调拨
     * @return 结果
     */
    int insertSbchEquipmentAllotTransnational(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational);

    /**
     * 修改跨国别设备调拨
     * 
     * @param sbchEquipmentAllotTransnational 跨国别设备调拨
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnational(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational);
    int updateSbchEquipmentAllotTransnationalByCode(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational);

    /**
     * 删除跨国别设备调拨
     * 
     * @param id 跨国别设备调拨ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalById(Long id);

    /**
     * 批量删除跨国别设备调拨
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalByIds(@Param("ids") String[] ids, @Param("delUser") String delUser);
}
