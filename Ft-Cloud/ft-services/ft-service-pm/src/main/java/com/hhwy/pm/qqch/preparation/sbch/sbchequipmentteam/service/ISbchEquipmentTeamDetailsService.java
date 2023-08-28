package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetails;

import java.util.List;

/**
 * 协作单位详情Service接口
 * 
 * @author hwj
 * @date 2022-11-30
 */
public interface ISbchEquipmentTeamDetailsService {
    /**
     * 查询协作单位详情
     * 
     * @param id 协作单位详情ID
     * @return 协作单位详情
     */
    SbchEquipmentTeamDetails selectSbchEquipmentTeamDetailsById(Long id);

    /**
     * 查询协作单位详情列表
     * 
     * @param sbchEquipmentTeamDetails 协作单位详情
     * @return 协作单位详情集合
     */
    List<SbchEquipmentTeamDetails> selectSbchEquipmentTeamDetailsList(SbchEquipmentTeamDetails sbchEquipmentTeamDetails);

    /**
     * 新增协作单位详情
     * 
     * @param sbchEquipmentTeamDetails 协作单位详情
     * @return 结果
     */
    int insertSbchEquipmentTeamDetails(SbchEquipmentTeamDetails sbchEquipmentTeamDetails);

    /**
     * 修改协作单位详情
     * 
     * @param sbchEquipmentTeamDetails 协作单位详情
     * @return 结果
     */
    int updateSbchEquipmentTeamDetails(SbchEquipmentTeamDetails sbchEquipmentTeamDetails);

    /**
     * 批量删除协作单位详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsByIds(String ids);

    /**
     * 删除协作单位详情信息
     * 
     * @param id 协作单位详情ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsById(Long id);

    /**
     * 新增和编辑 数据保存
     * @return 结果
     */
    int insertOrEditBatchByMainId(List<SbchEquipmentTeamDetails> list, Long mainId, Boolean isAdjust);
}