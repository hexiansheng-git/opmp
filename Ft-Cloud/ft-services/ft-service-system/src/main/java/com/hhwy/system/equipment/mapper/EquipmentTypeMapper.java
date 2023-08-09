package com.hhwy.system.equipment.mapper;


import com.hhwy.domain.base.system.equipment.EquipmentType;
import com.hhwy.system.equipment.vo.EquipmentTreeVo;

import java.util.List;

/**
 * 基础模块---设备分类Mapper接口
 * 
 * @author lcf
 * @date 2023-03-03
 */
public interface EquipmentTypeMapper {

    /**
     * 查询基础模块---设备分类列表
     *
     * @param equipmentType 基础模块---设备分类
     * @return 基础模块---设备分类集合
     */
    List<EquipmentTreeVo> selectEquipmentTypeSCList(EquipmentType equipmentType);

    /**
     * 查询基础模块---设备分类
     * 
     * @param id 基础模块---设备分类ID
     * @return 基础模块---设备分类
     */
    EquipmentType selectEquipmentTypeById(Long id);

    /**
     * 查询基础模块---设备分类列表
     * 
     * @param equipmentType 基础模块---设备分类
     * @return 基础模块---设备分类集合
     */
    List<EquipmentType> selectEquipmentTypeList(EquipmentType equipmentType);

    /**
     * 新增基础模块---设备分类
     * 
     * @param equipmentType 基础模块---设备分类
     * @return 结果
     */
    int insertEquipmentType(EquipmentType equipmentType);

    /**
     * 修改基础模块---设备分类
     * 
     * @param equipmentType 基础模块---设备分类
     * @return 结果
     */
    int updateEquipmentType(EquipmentType equipmentType);

    /**
     * 删除基础模块---设备分类
     * 
     * @param id 基础模块---设备分类ID
     * @return 结果
     */
    int deleteEquipmentTypeById(Long id);

    /**
     * 批量删除基础模块---设备分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteEquipmentTypeByIds(String[] ids);

    List<EquipmentType> selectEquipTypeInfo(EquipmentType equipmentType);
}
