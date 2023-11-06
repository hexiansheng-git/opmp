package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.mapper;



import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlanDetails;

import java.util.List;

/**
 * 特种设备风险识别和措施策划详情Mapper接口
 * 
 * @author hwj
 * @date 2022-12-06
 */
public interface SbchEquipmentSpecialPlanDetailsMapper {
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
     * 删除特种设备风险识别和措施策划详情
     * 
     * @param id 特种设备风险识别和措施策划详情ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialPlanDetailsById(Long id);

    /**
     * 批量删除特种设备风险识别和措施策划详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialPlanDetailsByIds(String[] ids);

    /**
     * 根据main_id 删除详情
     *
     * @param mainId 详情ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialPlanDetailsByMainId(Long mainId);

    int batchInsert(List<SbchEquipmentSpecialPlanDetails> dataList);

    List<SbchEquipmentSpecialPlanDetails> getListByDeviceCode(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails);
}
