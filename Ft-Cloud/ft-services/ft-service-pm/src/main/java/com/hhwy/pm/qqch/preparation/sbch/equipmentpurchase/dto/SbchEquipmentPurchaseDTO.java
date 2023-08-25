package com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.dto;

import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchase;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchaseDetails;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备申购管理对象 sbch_equipment_purchase
 * 
 * @author hwj
 * @date 2022-11-22
 */
@Data
public class SbchEquipmentPurchaseDTO extends SbchEquipmentPurchase {
    /**
     * 物资详情
     */
    private List<SbchEquipmentPurchaseDetails> detailsList=new ArrayList<>();

    /**
     * 版本号 v1.0
     */
    private String versionCodeStr;
    /**
     * 当前任务名
     */
    private String processTaskName;
    /**
     * 当前处理人
     */
    private String processTaskMan;

}
