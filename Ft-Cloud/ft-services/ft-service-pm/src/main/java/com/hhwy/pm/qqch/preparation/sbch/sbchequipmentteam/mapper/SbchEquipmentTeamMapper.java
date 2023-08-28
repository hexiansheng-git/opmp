package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.mapper;

import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 协作单位设备管理Mapper接口
 * 
 * @author hwj
 * @date 2022-11-30
 */
public interface SbchEquipmentTeamMapper {
    /**
     * 查询协作单位设备管理
     * 
     * @param id 协作单位设备管理ID
     * @return 协作单位设备管理
     */
    SbchEquipmentTeam selectSbchEquipmentTeamById(Long id);

    /**
     * 查询协作单位设备管理列表
     * 
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 协作单位设备管理集合
     */
    List<SbchEquipmentTeam> selectSbchEquipmentTeamList(SbchEquipmentTeam sbchEquipmentTeam);

    /**
     * 新增协作单位设备管理
     * 
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    int insertSbchEquipmentTeam(SbchEquipmentTeam sbchEquipmentTeam);

    /**
     * 修改协作单位设备管理
     * 
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    int updateSbchEquipmentTeam(SbchEquipmentTeam sbchEquipmentTeam);
    int updateSbchEquipmentTeamByCode(SbchEquipmentTeam sbchEquipmentTeam);

    /**
     * 删除协作单位设备管理
     * 
     * @param id 协作单位设备管理ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamById(Long id);

    /**
     * 批量删除协作单位设备管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamByIds(@Param("ids") String[] ids, @Param("delUser") String delUser);
}
