package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeam;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.dto.SbchEquipmentTeamDTO;
import com.hhwy.utils.common.CommonBaseEntity;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 协作单位设备管理Service接口
 * 
 * @author hwj
 * @date 2022-11-30
 */
public interface ISbchEquipmentTeamService {
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
    int updateSbchEquipmentTeamAndDetails(SbchEquipmentTeamDTO sbchEquipmentTeam);

    /**
     * 新增协作单位设备管理
     *
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    String insertSbchEquipmentTeamAndDetails(SbchEquipmentTeam sbchEquipmentTeam);

    /**
     * 调整协作单位设备管理
     *
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    String adjustSbchEquipmentTeamAndDetails(SbchEquipmentTeamDTO sbchEquipmentTeam);

    /**
     * 修改协作单位设备管理
     *
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    int updateSbchEquipmentTeam(SbchEquipmentTeam sbchEquipmentTeam);

    /**
     * 批量删除协作单位设备管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamByIds(String ids);

    /**
     * 删除协作单位设备管理信息
     * 
     * @param id 协作单位设备管理ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamById(Long id);
    /**
     * 设置有效
     *
     * @param mianId 设备申购管理ID
     * @return 结果
     */
    int updateValidStatus(String mianId);

    SbchEquipmentTeam getList(BigDecimal version);
}
