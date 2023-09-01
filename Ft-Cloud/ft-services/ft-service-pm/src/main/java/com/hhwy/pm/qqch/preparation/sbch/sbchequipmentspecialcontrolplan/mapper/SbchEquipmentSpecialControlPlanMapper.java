package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.mapper;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.domain.SbchEquipmentSpecialControlPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 特种设备过程管控策划Mapper接口
 * 
 * @author hwj
 * @date 2022-12-07
 */
public interface SbchEquipmentSpecialControlPlanMapper {
    /**
     * 查询特种设备过程管控策划
     * 
     * @param id 特种设备过程管控策划ID
     * @return 特种设备过程管控策划
     */
    SbchEquipmentSpecialControlPlan selectSbchEquipmentSpecialControlPlanById(Long id);

    /**
     * 查询特种设备过程管控策划列表
     * 
     * @param sbchEquipmentSpecialControlPlan 特种设备过程管控策划
     * @return 特种设备过程管控策划集合
     */
    List<SbchEquipmentSpecialControlPlan> selectSbchEquipmentSpecialControlPlanList(SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan);

    /**
     * 新增特种设备过程管控策划
     * 
     * @param sbchEquipmentSpecialControlPlan 特种设备过程管控策划
     * @return 结果
     */
    int insertSbchEquipmentSpecialControlPlan(SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan);

    /**
     * 修改特种设备过程管控策划
     * 
     * @param sbchEquipmentSpecialControlPlan 特种设备过程管控策划
     * @return 结果
     */
    int updateSbchEquipmentSpecialControlPlan(SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan);

    /**
     * 删除特种设备过程管控策划
     * 
     * @param id 特种设备过程管控策划ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialControlPlanById(Long id);

    /**
     * 批量删除特种设备过程管控策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialControlPlanByIds(@Param("ids") String[] ids, @Param("delUser") String delUser);
}
