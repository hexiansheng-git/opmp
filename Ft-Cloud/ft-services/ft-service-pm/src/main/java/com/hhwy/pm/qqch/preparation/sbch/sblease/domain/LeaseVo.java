package com.hhwy.pm.qqch.preparation.sbch.sblease.domain;

import com.hhwy.utils.common.MyPrepareBaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-26 16:43
 */
@Data
public class LeaseVo extends MyPrepareBaseEntity {
    //租赁设备信息调查
    private List<SbchEquipmentLeaseDetails> leaseDetailsList;

    //设备租赁供应商调查表
    private List<SbchEquipmentSupplierDetails> supplierList;
}
