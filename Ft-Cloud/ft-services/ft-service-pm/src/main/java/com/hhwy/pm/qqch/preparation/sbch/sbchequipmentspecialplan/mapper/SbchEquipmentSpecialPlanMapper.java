package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.mapper;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 特种设备风险识别和措施策划Mapper接口
 * 
 * @author hwj
 * @date 2022-12-06
 */
public interface SbchEquipmentSpecialPlanMapper {
    /**
     * 查询特种设备风险识别和措施策划
     * 
     * @param id 特种设备风险识别和措施策划ID
     * @return 特种设备风险识别和措施策划
     */
    SbchEquipmentSpecialPlan selectSbchEquipmentSpecialPlanById(Long id);

    /**
     * 查询特种设备风险识别和措施策划列表
     * 
     * @param sbchEquipmentSpecialPlan 特种设备风险识别和措施策划
     * @return 特种设备风险识别和措施策划集合
     */
    List<SbchEquipmentSpecialPlan> selectSbchEquipmentSpecialPlanList(SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan);

    /**
     * 新增特种设备风险识别和措施策划
     * 
     * @param sbchEquipmentSpecialPlan 特种设备风险识别和措施策划
     * @return 结果
     */
    int insertSbchEquipmentSpecialPlan(SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan);

    /**
     * 修改特种设备风险识别和措施策划
     * 
     * @param sbchEquipmentSpecialPlan 特种设备风险识别和措施策划
     * @return 结果
     */
    int updateSbchEquipmentSpecialPlan(SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan);

    /**
     * 删除特种设备风险识别和措施策划
     * 
     * @param id 特种设备风险识别和措施策划ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialPlanById(Long id);

    /**
     * 批量删除特种设备风险识别和措施策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialPlanByIds(@Param("ids") String[] ids, @Param("delUser") String delUser);
}
