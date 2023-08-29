package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.mapper;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetails;

import java.util.List;

/**
 * 协作单位详情Mapper接口
 * 
 * @author hwj
 * @date 2022-11-30
 */
public interface SbchEquipmentTeamDetailsMapper {
    /**
     * 查询协作单位详情
     * 
     * @param id 协作单位详情ID
     * @return 协作单位详情
     */
    SbchEquipmentTeamDetails selectSbchEquipmentTeamDetailsById(Long id);
    List selectSbchEquipmentTeamDetailsByMainId(Long id);

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
     * 删除协作单位详情
     * 
     * @param id 协作单位详情ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsById(Long id);

    /**
     * 批量删除协作单位详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsByIds(String[] ids);

    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsByMainId(Long mainId);

    int batchInsert(List<SbchEquipmentTeamDetails> dataList);
}
