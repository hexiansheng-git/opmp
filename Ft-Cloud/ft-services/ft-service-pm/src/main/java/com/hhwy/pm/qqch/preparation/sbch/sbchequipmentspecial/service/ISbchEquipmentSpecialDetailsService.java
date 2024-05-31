package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecialDetails;

import java.math.BigDecimal;
import java.util.List;

/**
 * 特种设备管理详情Service接口
 * 
 * @author hwj
 * @date 2022-12-05
 */
public interface ISbchEquipmentSpecialDetailsService {
    /**
     * 查询特种设备管理详情
     * 
     * @param id 特种设备管理详情ID
     * @return 特种设备管理详情
     */
    SbchEquipmentSpecialDetails selectSbchEquipmentSpecialDetailsById(Long id);

    /**
     * 查询特种设备管理详情列表
     * 
     * @param sbchEquipmentSpecialDetails 特种设备管理详情
     * @return 特种设备管理详情集合
     */
    List<SbchEquipmentSpecialDetails> selectSbchEquipmentSpecialDetailsList(SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails);

    /**
     * 新增特种设备管理详情
     * 
     * @param sbchEquipmentSpecialDetails 特种设备管理详情
     * @return 结果
     */
    int insertSbchEquipmentSpecialDetails(SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails);

    /**
     * 修改特种设备管理详情
     * 
     * @param sbchEquipmentSpecialDetails 特种设备管理详情
     * @return 结果
     */
    int updateSbchEquipmentSpecialDetails(SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails);

    /**
     * 批量删除特种设备管理详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialDetailsByIds(String ids);

    /**
     * 删除特种设备管理详情信息
     * 
     * @param id 特种设备管理详情ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialDetailsById(Long id);

    int insertOrEditBatchByMainId(List<SbchEquipmentSpecialDetails> list, Long mainId, Boolean isAdjus, BigDecimal version);

}
