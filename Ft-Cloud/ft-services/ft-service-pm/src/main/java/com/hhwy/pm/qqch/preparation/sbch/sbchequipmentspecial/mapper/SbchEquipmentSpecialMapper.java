package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.mapper;

import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 特种设备管理Mapper接口
 * 
 * @author hwj
 * @date 2022-12-05
 */
public interface SbchEquipmentSpecialMapper {
    /**
     * 查询特种设备管理
     * 
     * @param id 特种设备管理ID
     * @return 特种设备管理
     */
    SbchEquipmentSpecial selectSbchEquipmentSpecialById(Long id);

    /**
     * 查询特种设备管理列表
     * 
     * @param sbchEquipmentSpecial 特种设备管理
     * @return 特种设备管理集合
     */
    List<SbchEquipmentSpecial> selectSbchEquipmentSpecialList(SbchEquipmentSpecial sbchEquipmentSpecial);

    /**
     * 新增特种设备管理
     * 
     * @param sbchEquipmentSpecial 特种设备管理
     * @return 结果
     */
    int insertSbchEquipmentSpecial(SbchEquipmentSpecial sbchEquipmentSpecial);

    /**
     * 修改特种设备管理
     * 
     * @param sbchEquipmentSpecial 特种设备管理
     * @return 结果
     */
    int updateSbchEquipmentSpecial(SbchEquipmentSpecial sbchEquipmentSpecial);

    /**
     * 删除特种设备管理
     * 
     * @param id 特种设备管理ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialById(Long id);

    /**
     * 批量删除特种设备管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialByIds(@Param("ids") String[] ids, @Param("delUser") String delUser);
}
