package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlan;
import com.hhwy.utils.common.CommonBaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 特种设备风险识别和措施策划Service接口
 * 
 * @author hwj
 * @date 2022-12-06
 */
public interface ISbchEquipmentSpecialPlanService {
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
     * 批量删除特种设备风险识别和措施策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialPlanByIds(String ids);

    /**
     * 删除特种设备风险识别和措施策划信息
     * 
     * @param id 特种设备风险识别和措施策划ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialPlanById(Long id);

    SbchEquipmentSpecialPlan getList(BigDecimal version);

    void batchSave(SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan);
}
