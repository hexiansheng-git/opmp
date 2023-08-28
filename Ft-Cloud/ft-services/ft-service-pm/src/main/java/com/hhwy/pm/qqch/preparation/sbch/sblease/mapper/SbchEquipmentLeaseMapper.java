package com.hhwy.pm.qqch.preparation.sbch.sblease.mapper;

import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentLease;
import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentSupplier;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-26 15:25
 */
public interface SbchEquipmentLeaseMapper {
    /**
     * 查询设备租赁列表
     *
     * @param sbchEquipmentLease 设备租赁
     * @return 设备租赁集合
     */
    List<SbchEquipmentLease> selectSbchEquipmentLeaseList(SbchEquipmentLease sbchEquipmentLease);

    /**
     * 新增设备租赁
     *
     * @param sbchEquipmentLease 设备租赁
     * @return 结果
     */
    int insertSbchEquipmentLease(SbchEquipmentLease sbchEquipmentLease);

    /**
     * 修改设备租赁
     *
     * @param sbchEquipmentLease 设备租赁
     * @return 结果
     */
    int updateSbchEquipmentLease(SbchEquipmentLease sbchEquipmentLease);

}
