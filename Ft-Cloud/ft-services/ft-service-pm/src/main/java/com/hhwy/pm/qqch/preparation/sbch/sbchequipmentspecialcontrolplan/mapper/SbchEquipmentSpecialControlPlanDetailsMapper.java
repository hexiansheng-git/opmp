package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.mapper;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.domain.SbchEquipmentSpecialControlPlanDetails;

import java.util.List;

/**
 * 特种设备过程管控策划详情Mapper接口
 * 
 * @author hwj
 * @date 2022-12-07
 */
public interface SbchEquipmentSpecialControlPlanDetailsMapper {
    /**
     * 查询特种设备过程管控策划详情
     * 
     * @param id 特种设备过程管控策划详情ID
     * @return 特种设备过程管控策划详情
     */
    SbchEquipmentSpecialControlPlanDetails selectSbchEquipmentSpecialControlPlanDetailsById(Long id);

    /**
     * 查询特种设备过程管控策划详情列表
     * 
     * @param sbchEquipmentSpecialControlPlanDetails 特种设备过程管控策划详情
     * @return 特种设备过程管控策划详情集合
     */
    List<SbchEquipmentSpecialControlPlanDetails> selectSbchEquipmentSpecialControlPlanDetailsList(SbchEquipmentSpecialControlPlanDetails sbchEquipmentSpecialControlPlanDetails);

    /**
     * 新增特种设备过程管控策划详情
     * 
     * @param sbchEquipmentSpecialControlPlanDetails 特种设备过程管控策划详情
     * @return 结果
     */
    int insertSbchEquipmentSpecialControlPlanDetails(SbchEquipmentSpecialControlPlanDetails sbchEquipmentSpecialControlPlanDetails);

    /**
     * 修改特种设备过程管控策划详情
     * 
     * @param sbchEquipmentSpecialControlPlanDetails 特种设备过程管控策划详情
     * @return 结果
     */
    int updateSbchEquipmentSpecialControlPlanDetails(SbchEquipmentSpecialControlPlanDetails sbchEquipmentSpecialControlPlanDetails);

    /**
     * 删除特种设备过程管控策划详情
     * 
     * @param id 特种设备过程管控策划详情ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialControlPlanDetailsById(Long id);

    /**
     * 批量删除特种设备过程管控策划详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialControlPlanDetailsByIds(String[] ids);
    /**
     * 根据main_id 删除详情
     *
     * @param mainId 详情ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialControlPlanDetailsByMainId(Long mainId);

    int batchInsert(List<SbchEquipmentSpecialControlPlanDetails> dataList);
}
