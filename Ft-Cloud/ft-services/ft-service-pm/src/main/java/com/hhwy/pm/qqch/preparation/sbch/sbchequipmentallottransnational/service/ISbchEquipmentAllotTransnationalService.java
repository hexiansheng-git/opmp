package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnational;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 跨国别设备调拨Service接口
 * 
 * @author hwj
 * @date 2022-12-20
 */
public interface ISbchEquipmentAllotTransnationalService {
    /**
     * 新增 编辑 详情数据回显
     *
     * @param map 参数
     * @return
     */
    List baseInfo(Map<String, String> map);
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
    String insertSbchEquipmentAllotTransnational(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational);

    /**
     * 修改跨国别设备调拨
     * 
     * @param sbchEquipmentAllotTransnational 跨国别设备调拨
     * @return 结果
     */
    int updateSbchEquipmentAllotTransnational(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational);

    String adjustSbchEquipmentAllotTransnational(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational);
    /**
     * 批量删除跨国别设备调拨
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalByIds(String ids);

    /**
     * 删除跨国别设备调拨信息
     * 
     * @param id 跨国别设备调拨ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotTransnationalById(Long id);

    int updateValidStatus(String mainId);

    }
