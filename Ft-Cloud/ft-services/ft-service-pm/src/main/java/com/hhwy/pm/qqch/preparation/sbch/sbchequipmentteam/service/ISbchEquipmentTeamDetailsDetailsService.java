package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetailsDetails;

import java.util.List;

/**
 * 协作单位设备详情Service接口
 * 
 * @author hwj
 * @date 2022-11-30
 */
public interface ISbchEquipmentTeamDetailsDetailsService {

    /**
     * 查询协作单位设备详情列表
     * 
     * @param sbchEquipmentTeamDetailsDetails 协作单位设备详情
     * @return 协作单位设备详情集合
     */
    List<SbchEquipmentTeamDetailsDetails> selectSbchEquipmentTeamDetailsDetailsList(SbchEquipmentTeamDetailsDetails sbchEquipmentTeamDetailsDetails);

    /**
     * 新增协作单位设备详情
     * 
     * @param sbchEquipmentTeamDetailsDetails 协作单位设备详情
     * @return 结果
     */
    int insertSbchEquipmentTeamDetailsDetails(SbchEquipmentTeamDetailsDetails sbchEquipmentTeamDetailsDetails);

    /**
     * 修改协作单位设备详情
     * 
     * @param sbchEquipmentTeamDetailsDetails 协作单位设备详情
     * @return 结果
     */
    int updateSbchEquipmentTeamDetailsDetails(SbchEquipmentTeamDetailsDetails sbchEquipmentTeamDetailsDetails);

    /**
     * 批量删除协作单位设备详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsDetailsByIds(String ids);

    /**
     * 删除协作单位设备详情信息
     * 
     * @param id 协作单位设备详情ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsDetailsById(Long id);
}
