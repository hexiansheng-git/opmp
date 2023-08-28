package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;

import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetailsDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.mapper.SbchEquipmentTeamDetailsDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service.ISbchEquipmentTeamDetailsDetailsService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 协作单位设备详情Service业务层处理
 * 
 * @author hwj
 * @date 2022-11-30
 */
@Service
public class SbchEquipmentTeamDetailsDetailsServiceImpl implements ISbchEquipmentTeamDetailsDetailsService {
    @Autowired
    private SbchEquipmentTeamDetailsDetailsMapper sbchEquipmentTeamDetailsDetailsMapper;



    /**
     * 查询协作单位设备详情列表
     * 
     * @param sbchEquipmentTeamDetailsDetails 协作单位设备详情
     * @return 协作单位设备详情
     */
    @Override
    public List<SbchEquipmentTeamDetailsDetails> selectSbchEquipmentTeamDetailsDetailsList(SbchEquipmentTeamDetailsDetails sbchEquipmentTeamDetailsDetails) {
        return sbchEquipmentTeamDetailsDetailsMapper.selectSbchEquipmentTeamDetailsDetailsList(sbchEquipmentTeamDetailsDetails);
    }

    /**
     * 新增协作单位设备详情
     * 
     * @param sbchEquipmentTeamDetailsDetails 协作单位设备详情
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentTeamDetailsDetails(SbchEquipmentTeamDetailsDetails sbchEquipmentTeamDetailsDetails) {

    sbchEquipmentTeamDetailsDetails.setId(IdWorker.createId());

        sbchEquipmentTeamDetailsDetails.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentTeamDetailsDetailsMapper.insertSbchEquipmentTeamDetailsDetails(sbchEquipmentTeamDetailsDetails);
    }

    /**
     * 修改协作单位设备详情
     * 
     * @param sbchEquipmentTeamDetailsDetails 协作单位设备详情
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentTeamDetailsDetails(SbchEquipmentTeamDetailsDetails sbchEquipmentTeamDetailsDetails) {
        sbchEquipmentTeamDetailsDetails.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentTeamDetailsDetailsMapper.updateSbchEquipmentTeamDetailsDetails(sbchEquipmentTeamDetailsDetails);
    }

    /**
     * 删除协作单位设备详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentTeamDetailsDetailsByIds(String ids) {
        return sbchEquipmentTeamDetailsDetailsMapper.deleteSbchEquipmentTeamDetailsDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除协作单位设备详情信息
     * 
     * @param id 协作单位设备详情ID
     * @return 结果
     */
    public int deleteSbchEquipmentTeamDetailsDetailsById(Long id) {
        return sbchEquipmentTeamDetailsDetailsMapper.deleteSbchEquipmentTeamDetailsDetailsById(id);
    }
}
