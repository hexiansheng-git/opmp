package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.domain.SbchEquipmentSpecialControlPlan;
import com.hhwy.utils.common.CommonBaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 特种设备过程管控策划Service接口
 * 
 * @author hwj
 * @date 2022-12-07
 */
public interface ISbchEquipmentSpecialControlPlanService {
//    /**
//     * 新增 编辑 详情数据回显
//     *
//     * @param map 参数
//     * @return
//     */
//    CommonBaseEntity baseInfo(Map<String, String> map);
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
     * 批量删除特种设备过程管控策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialControlPlanByIds(String ids);

    /**
     * 删除特种设备过程管控策划信息
     * 
     * @param id 特种设备过程管控策划ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialControlPlanById(Long id);

    SbchEquipmentSpecialControlPlan getList(BigDecimal version);

    void batchSave(SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan);
}
