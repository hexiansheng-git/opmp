package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;

import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalClearance;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalClearanceMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalClearanceService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-清关档案核查Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Service
public class SbchEquipmentAllotTransnationalClearanceServiceImpl implements ISbchEquipmentAllotTransnationalClearanceService {
    @Autowired
    private SbchEquipmentAllotTransnationalClearanceMapper sbchEquipmentAllotTransnationalClearanceMapper;

    /**
     * 查询跨国别设备调拨详情-清关档案核查
     * 
     * @param id 跨国别设备调拨详情-清关档案核查ID
     * @return 跨国别设备调拨详情-清关档案核查
     */
    @Override
    public SbchEquipmentAllotTransnationalClearance selectSbchEquipmentAllotTransnationalClearanceById(Long id) {
        return sbchEquipmentAllotTransnationalClearanceMapper.selectSbchEquipmentAllotTransnationalClearanceById(id);
    }

    /**
     * 查询跨国别设备调拨详情-清关档案核查列表
     * 
     * @param sbchEquipmentAllotTransnationalClearance 跨国别设备调拨详情-清关档案核查
     * @return 跨国别设备调拨详情-清关档案核查
     */
    @Override
    public List<SbchEquipmentAllotTransnationalClearance> selectSbchEquipmentAllotTransnationalClearanceList(SbchEquipmentAllotTransnationalClearance sbchEquipmentAllotTransnationalClearance) {
        return sbchEquipmentAllotTransnationalClearanceMapper.selectSbchEquipmentAllotTransnationalClearanceList(sbchEquipmentAllotTransnationalClearance);
    }

    /**
     * 新增跨国别设备调拨详情-清关档案核查
     * 
     * @param sbchEquipmentAllotTransnationalClearance 跨国别设备调拨详情-清关档案核查
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalClearance(SbchEquipmentAllotTransnationalClearance sbchEquipmentAllotTransnationalClearance) {

    sbchEquipmentAllotTransnationalClearance.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalClearance.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalClearanceMapper.insertSbchEquipmentAllotTransnationalClearance(sbchEquipmentAllotTransnationalClearance);
    }

    /**
     * 修改跨国别设备调拨详情-清关档案核查
     * 
     * @param sbchEquipmentAllotTransnationalClearance 跨国别设备调拨详情-清关档案核查
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalClearance(SbchEquipmentAllotTransnationalClearance sbchEquipmentAllotTransnationalClearance) {
        sbchEquipmentAllotTransnationalClearance.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalClearanceMapper.updateSbchEquipmentAllotTransnationalClearance(sbchEquipmentAllotTransnationalClearance);
    }

    /**
     * 删除跨国别设备调拨详情-清关档案核查对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalClearanceByIds(String ids) {
        return sbchEquipmentAllotTransnationalClearanceMapper.deleteSbchEquipmentAllotTransnationalClearanceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-清关档案核查信息
     * 
     * @param id 跨国别设备调拨详情-清关档案核查ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalClearanceById(Long id) {
        return sbchEquipmentAllotTransnationalClearanceMapper.deleteSbchEquipmentAllotTransnationalClearanceById(id);
    }
}
