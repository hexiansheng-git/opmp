package com.hhwy.pm.qqch.preparation.sbch.sblease.mapper;

import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentSupplier;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-26 15:23
 */
public interface SbchEquipmentSupplierMapper {
    /**
     * 查询设备租赁供应商列表
     *
     * @param sbchEquipmentSupplier 设备租赁供应商
     * @return 设备租赁供应商集合
     */
    List<SbchEquipmentSupplier> selectSbchEquipmentSupplierList(SbchEquipmentSupplier sbchEquipmentSupplier);

    /**
     * 修改设备租赁供应商
     *
     * @param sbchEquipmentSupplier 设备租赁供应商
     * @return 结果
     */
    int updateSbchEquipmentSupplier(SbchEquipmentSupplier sbchEquipmentSupplier);

    /**
     * 新增设备租赁供应商
     *
     * @param sbchEquipmentSupplier 设备租赁供应商
     * @return 结果
     */
    int insertSbchEquipmentSupplier(SbchEquipmentSupplier sbchEquipmentSupplier);

}
