package com.hhwy.pm.qqch.preparation.sbch.imported.material.service;


import com.hhwy.pm.qqch.preparation.sbch.imported.material.domain.SbchMaterialTranPlan;

import java.math.BigDecimal;
import java.util.List;

/**
 * 大型成套设备运输方案Service接口
 * 
 * @author zq
 * @date 2022-12-12
 */
public interface ISbchMaterialTranPlanService {
    /**
     * 查询大型成套设备运输方案
     * 
     * @param id 大型成套设备运输方案ID
     * @return 大型成套设备运输方案
     */
    SbchMaterialTranPlan selectSbchMaterialTranPlanById(Long id);

    /**
     * 查询大型成套设备运输方案列表
     * 
     * @param sbchMaterialTranPlan 大型成套设备运输方案
     * @return 大型成套设备运输方案集合
     */
    List<SbchMaterialTranPlan> selectSbchMaterialTranPlanList(SbchMaterialTranPlan sbchMaterialTranPlan);

    /**
     * 新增大型成套设备运输方案
     * 
     * @param sbchMaterialTranPlan 大型成套设备运输方案
     * @return 结果
     */
    int insertSbchMaterialTranPlan(SbchMaterialTranPlan sbchMaterialTranPlan);

    /**
     * 修改大型成套设备运输方案
     * 
     * @param sbchMaterialTranPlan 大型成套设备运输方案
     * @return 结果
     */
    int updateSbchMaterialTranPlan(SbchMaterialTranPlan sbchMaterialTranPlan);

    /**
     * 批量删除大型成套设备运输方案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchMaterialTranPlanByIds(String ids);

    /**
     * 删除大型成套设备运输方案信息
     * 
     * @param id 大型成套设备运输方案ID
     * @return 结果
     */
    int deleteSbchMaterialTranPlanById(Long id);

    SbchMaterialTranPlan getList(BigDecimal version);

    void batchSave(SbchMaterialTranPlan sbchMaterialTranPlan);
}
