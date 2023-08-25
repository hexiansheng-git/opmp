package com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.mapper;

import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备申购管理Mapper接口
 * 
 * @author hwj
 * @date 2022-11-22
 */
public interface SbchEquipmentPurchaseMapper {
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
    List<SbchEquipmentPurchase> selectSbchEquipmentPurchaseList(SbchEquipmentPurchase sbchEquipmentPurchase);

    /**
     * 新增设备申购管理
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    int insertSbchEquipmentPurchase(SbchEquipmentPurchase sbchEquipmentPurchase);

    /**
     * 修改设备申购管理
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    int updateSbchEquipmentPurchase(SbchEquipmentPurchase sbchEquipmentPurchase);

    /**
     * 根据编码 修改设备申购管理
     *
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    int updateSbchEquipmentPurchaseByCode(SbchEquipmentPurchase sbchEquipmentPurchase);

    /**
     * 删除设备申购管理
     * 
     * @param id 设备申购管理ID
     * @return 结果
     */
    int deleteSbchEquipmentPurchaseById(Long id);

    /**
     * 批量删除设备申购管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentPurchaseByIds(@Param("ids") String[] ids, @Param("delUser") String delUser);
}
