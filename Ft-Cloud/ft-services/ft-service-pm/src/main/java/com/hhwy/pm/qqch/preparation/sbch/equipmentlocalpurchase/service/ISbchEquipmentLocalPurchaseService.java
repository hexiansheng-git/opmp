package com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.domain.SbchEquipmentLocalPurchase;
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
public interface ISbchEquipmentLocalPurchaseService {

    SbchEquipmentLocalPurchase selectSbchEquipmentPurchaseList(BigDecimal version);

    String insertSbchEquipmentPurchaseAndDetails(SbchEquipmentLocalPurchase sbchEquipmentPurchase);
}