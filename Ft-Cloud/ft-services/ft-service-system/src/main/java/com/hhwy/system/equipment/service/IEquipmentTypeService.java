package com.hhwy.system.equipment.service;



import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.domain.base.system.equipment.EquipmentType;
import com.hhwy.system.equipment.vo.EquipmentTreeVo;

import java.util.List;

/**
 * 基础模块---设备分类Service接口
 * 
 * @author lcf
 * @date 2023-03-03
 */
public interface IEquipmentTypeService {
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
     * 批量删除基础模块---设备分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteEquipmentTypeByIds(String ids);

    /**
     * 删除基础模块---设备分类信息
     * 
     * @param id 基础模块---设备分类ID
     * @return 结果
     */
    int deleteEquipmentTypeById(Long id);

    /**
     * 左侧树形结构查询
     *
     * @param equipmentType
     * @return
     */
    List<SysTreeUtil> treeList(EquipmentType equipmentType);

    /**
     * 提供给融智的接口
     * 需要把equipment_type和equipment_info
     * 结合成一个树形结构返回
     *
     * @param equipmentType
     * @return
     */
    List<EquipmentTreeVo> treeInfoList(EquipmentType equipmentType);
}
