package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlanDetails;

import java.util.List;

/**
 * 特种设备风险识别和措施策划详情Service接口
 * 
 * @author hwj
 * @date 2022-12-06
 */
public interface ISbchEquipmentSpecialPlanDetailsService {
    /**
     * 查询特种设备风险识别和措施策划详情
     * 
     * @param id 特种设备风险识别和措施策划详情ID
     * @return 特种设备风险识别和措施策划详情
     */
    SbchEquipmentSpecialPlanDetails selectSbchEquipmentSpecialPlanDetailsById(Long id);

    /**
     * 查询特种设备风险识别和措施策划详情列表
     * 
     * @param sbchEquipmentSpecialPlanDetails 特种设备风险识别和措施策划详情
     * @return 特种设备风险识别和措施策划详情集合
     */
    List<SbchEquipmentSpecialPlanDetails> selectSbchEquipmentSpecialPlanDetailsList(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails);
    List<SbchEquipmentSpecialPlanDetails> selectSbchEquipmentSpecialPlanDetailshistoryList(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails);

    /**
     * 新增特种设备风险识别和措施策划详情
     * 
     * @param sbchEquipmentSpecialPlanDetails 特种设备风险识别和措施策划详情
     * @return 结果
     */
    int insertSbchEquipmentSpecialPlanDetails(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails);

    /**
     * 修改特种设备风险识别和措施策划详情
     * 
     * @param sbchEquipmentSpecialPlanDetails 特种设备风险识别和措施策划详情
     * @return 结果
     */
    int updateSbchEquipmentSpecialPlanDetails(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails);

    /**
     * 批量删除特种设备风险识别和措施策划详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialPlanDetailsByIds(String ids);

    /**
     * 删除特种设备风险识别和措施策划详情信息
     * 
     * @param id 特种设备风险识别和措施策划详情ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialPlanDetailsById(Long id);
    int insertOrEditBatchByMainId(List<SbchEquipmentSpecialPlanDetails> list, Long mainId, Boolean isAdjus);

    List<SbchEquipmentSpecialPlanDetails> getListByDeviceCode(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails);
}
