package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalClearanceDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalClearanceDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalClearanceDetailsService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-清关档案核查-清关档案核查明细Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Service
public class SbchEquipmentAllotTransnationalClearanceDetailsServiceImpl implements ISbchEquipmentAllotTransnationalClearanceDetailsService {
    @Autowired
    private SbchEquipmentAllotTransnationalClearanceDetailsMapper sbchEquipmentAllotTransnationalClearanceDetailsMapper;

    /**
     * 查询跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * 
     * @param id 跨国别设备调拨详情-清关档案核查-清关档案核查明细ID
     * @return 跨国别设备调拨详情-清关档案核查-清关档案核查明细
     */
    @Override
    public SbchEquipmentAllotTransnationalClearanceDetails selectSbchEquipmentAllotTransnationalClearanceDetailsById(Long id) {
        return sbchEquipmentAllotTransnationalClearanceDetailsMapper.selectSbchEquipmentAllotTransnationalClearanceDetailsById(id);
    }

    /**
     * 查询跨国别设备调拨详情-清关档案核查-清关档案核查明细列表
     * 
     * @param sbchEquipmentAllotTransnationalClearanceDetails 跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * @return 跨国别设备调拨详情-清关档案核查-清关档案核查明细
     */
    @Override
    public List<SbchEquipmentAllotTransnationalClearanceDetails> selectSbchEquipmentAllotTransnationalClearanceDetailsList(SbchEquipmentAllotTransnationalClearanceDetails sbchEquipmentAllotTransnationalClearanceDetails) {
        return sbchEquipmentAllotTransnationalClearanceDetailsMapper.selectSbchEquipmentAllotTransnationalClearanceDetailsList(sbchEquipmentAllotTransnationalClearanceDetails);
    }

    /**
     * 新增跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * 
     * @param sbchEquipmentAllotTransnationalClearanceDetails 跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalClearanceDetails(SbchEquipmentAllotTransnationalClearanceDetails sbchEquipmentAllotTransnationalClearanceDetails) {

    sbchEquipmentAllotTransnationalClearanceDetails.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalClearanceDetails.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalClearanceDetailsMapper.insertSbchEquipmentAllotTransnationalClearanceDetails(sbchEquipmentAllotTransnationalClearanceDetails);
    }

    /**
     * 修改跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * 
     * @param sbchEquipmentAllotTransnationalClearanceDetails 跨国别设备调拨详情-清关档案核查-清关档案核查明细
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalClearanceDetails(SbchEquipmentAllotTransnationalClearanceDetails sbchEquipmentAllotTransnationalClearanceDetails) {
        sbchEquipmentAllotTransnationalClearanceDetails.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalClearanceDetailsMapper.updateSbchEquipmentAllotTransnationalClearanceDetails(sbchEquipmentAllotTransnationalClearanceDetails);
    }

    /**
     * 删除跨国别设备调拨详情-清关档案核查-清关档案核查明细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalClearanceDetailsByIds(String ids) {
        return sbchEquipmentAllotTransnationalClearanceDetailsMapper.deleteSbchEquipmentAllotTransnationalClearanceDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-清关档案核查-清关档案核查明细信息
     * 
     * @param id 跨国别设备调拨详情-清关档案核查-清关档案核查明细ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalClearanceDetailsById(Long id) {
        return sbchEquipmentAllotTransnationalClearanceDetailsMapper.deleteSbchEquipmentAllotTransnationalClearanceDetailsById(id);
    }
}
