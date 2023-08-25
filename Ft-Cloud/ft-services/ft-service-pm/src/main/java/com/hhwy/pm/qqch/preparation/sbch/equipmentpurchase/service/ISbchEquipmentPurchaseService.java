package com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchase;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.dto.SbchEquipmentPurchaseDTO;
import com.hhwy.utils.common.CommonBaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 设备申购管理Service接口
 * 
 * @author hwj
 * @date 2022-11-22
 */
public interface ISbchEquipmentPurchaseService {

    /**
     * 查询设备申购管理
     * 
     * @param id 设备申购管理ID
     * @return 设备申购管理
     */
    SbchEquipmentPurchase selectSbchEquipmentPurchaseById(Long id);

    /**
     * 查询设备申购管理列表
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 设备申购管理集合
     */
    SbchEquipmentPurchase selectSbchEquipmentPurchaseList(BigDecimal version);

    /**
     * 新增设备申购管理和详情
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    String insertSbchEquipmentPurchaseAndDetails(SbchEquipmentPurchase sbchEquipmentPurchase);

    /**
     * 修改设备申购管理和详情
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    int updateSbchEquipmentPurchaseAndDetails(SbchEquipmentPurchaseDTO sbchEquipmentPurchase);
    /**
     * 修改设备申购管理
     *
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    int updateSbchEquipmentPurchase(SbchEquipmentPurchase sbchEquipmentPurchase);

    /**
     * 调整设备申购管理和详情
     *
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    String adjustSbchEquipmentPurchaseAndDetails(SbchEquipmentPurchaseDTO sbchEquipmentPurchase);

    /**
     * 批量删除设备申购管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentPurchaseByIds(String ids);

    /**
     * 删除设备申购管理信息
     * 
     * @param id 设备申购管理ID
     * @return 结果
     */
    int deleteSbchEquipmentPurchaseById(Long id);
}
