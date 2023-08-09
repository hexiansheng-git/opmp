package com.hhwy.system.equipment.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.equipment.EquipmentInfo;

import java.util.List;

/**
 * 基础模块设备分类子表Service接口
 * 
 * @author jzq
 * @date 2023-03-03
 */
public interface IEquipmentInfoService {
    /**
     * 查询基础模块设备分类子表
     * 
     * @param id 基础模块设备分类子表ID
     * @return 基础模块设备分类子表
     */
    EquipmentInfo selectEquipmentInfoById(Long id);

    /**
     * 查询基础模块设备分类子表列表
     * 
     * @param equipmentInfo 基础模块设备分类子表
     * @return 基础模块设备分类子表集合
     */
    List<EquipmentInfo> selectEquipmentInfoList(EquipmentInfo equipmentInfo);

    /**
     * 新增基础模块设备分类子表
     * 
     * @param equipmentInfo 基础模块设备分类子表
     * @return 结果
     */
    int insertEquipmentInfo(EquipmentInfo equipmentInfo);

    /**
     * 修改基础模块设备分类子表
     * 
     * @param equipmentInfo 基础模块设备分类子表
     * @return 结果
     */
    int updateEquipmentInfo(EquipmentInfo equipmentInfo);

    /**
     * 批量删除基础模块设备分类子表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteEquipmentInfoByIds(String ids);

    /**
     * 删除基础模块设备分类子表信息
     * 
     * @param id 基础模块设备分类子表ID
     * @return 结果
     */
    int deleteEquipmentInfoById(Long id);

    /**
     *
     *
     * @param list
     * @param parentId
     * @return
     */
    AjaxResult importData(List<EquipmentInfo> list, Long parentId);
}
