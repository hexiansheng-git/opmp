package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.mapper;

import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 同国别设备Mapper接口
 *
 * @author hwj
 * @date 2022-11-25
 */
public interface SbchEquipmentAllotMapper {
    /**
     * 查询同国别设备
     *
     * @param id 同国别设备ID
     * @return 同国别设备
     */
    SbchEquipmentAllot selectSbchEquipmentAllotById(Long id);

    /**
     * 查询同国别设备列表
     *
     * @param sbchEquipmentAllot 同国别设备
     * @return 同国别设备集合
     */
    List<SbchEquipmentAllot> selectSbchEquipmentAllotList(SbchEquipmentAllot sbchEquipmentAllot);

    /**
     * 新增同国别设备
     *
     * @param sbchEquipmentAllot 同国别设备
     * @return 结果
     */
    int insertSbchEquipmentAllot(SbchEquipmentAllot sbchEquipmentAllot);

    /**
     * 修改同国别设备
     *
     * @param sbchEquipmentAllot 同国别设备
     * @return 结果
     */
    int updateSbchEquipmentAllot(SbchEquipmentAllot sbchEquipmentAllot);

    /**
     * 根据编码 修改
     *
     * @param sbchEquipmentAllot
     * @return 结果
     */
    int updateSbchEquipmentAllotByCode(SbchEquipmentAllot sbchEquipmentAllot);

    /**
     * 删除同国别设备
     *
     * @param id 同国别设备ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotById(Long id);

    /**
     * 批量删除同国别设备
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentAllotByIds(@Param("ids") String[] ids, @Param("delUser") String delUser);
}
