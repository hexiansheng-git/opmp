package com.hhwy.system.equipment.mapper;

import com.hhwy.domain.base.system.equipment.EquipmentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基础模块设备分类子表Mapper接口
 * 
 * @author jzq
 * @date 2023-03-03
 */
public interface EquipmentInfoMapper {
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
     * 删除基础模块设备分类子表
     * 
     * @param id 基础模块设备分类子表ID
     * @return 结果
     */
    int deleteEquipmentInfoById(Long id);

    /**
     * 批量删除基础模块设备分类子表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteEquipmentInfoByIds(String[] ids);

    /**
     *根据equipCode批量查询
     *
     * @param equipCodeList
     * @return
     */
    List<EquipmentInfo> selectInfoByEquipmenInfo(@Param(value = "equipCodeList") List<String> equipCodeList);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertBath(@Param(value = "dataList") List<EquipmentInfo> list);

    /**
     * 批量查询根据t_equipment_type 表id 批量查询
     *
     * @param equipTypeIds
     * @return
     */
    List<EquipmentInfo> selectByTypeIds(@Param(value = "equipTypeIds")List<Long> equipTypeIds);

    /**
     * 根据id批量查询信息
     *
     * @param equipInfoIds
     * @return
     */
    List<EquipmentInfo> selectInfoByIds(@Param(value = "equipInfoIds")List<Long> idsList);
}
