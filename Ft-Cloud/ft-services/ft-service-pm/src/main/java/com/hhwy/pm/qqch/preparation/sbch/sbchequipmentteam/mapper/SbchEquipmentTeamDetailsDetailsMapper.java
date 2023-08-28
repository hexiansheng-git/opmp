package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.mapper;


import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetailsDetails;

import java.util.List;

/**
 * 协作单位设备详情Mapper接口
 * 
 * @author hwj
 * @date 2022-11-30
 */
public interface SbchEquipmentTeamDetailsDetailsMapper {
    /**
     * 查询协作单位设备详情
     * 
     * @param id 协作单位设备详情ID
     * @return 协作单位设备详情
     */
    SbchEquipmentTeamDetailsDetails selectSbchEquipmentTeamDetailsDetailsById(Long id);
    SbchEquipmentTeamDetailsDetails selectSbchEquipmentTeamDetailsDetailsByMainId(Long id);

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
     * 删除协作单位设备详情
     * 
     * @param id 协作单位设备详情ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsDetailsById(Long id);

    /**
     * 批量删除协作单位设备详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsDetailsByIds(String[] ids);

    /**
     * 根据main_id 删除设备申购管理详情
     *
     * @param mainId 设备申购管理详情ID
     * @return 结果
     */
    int deleteSbchEquipmentTeamDetailsDetailsByMainId(Long mainId);
    int batchInsert(List<SbchEquipmentTeamDetailsDetails> dataList);
}
