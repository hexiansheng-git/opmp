package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalPort;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalPortMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalPortService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-港口调查Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-22
 */
@Service
public class SbchEquipmentAllotTransnationalPortServiceImpl implements ISbchEquipmentAllotTransnationalPortService {
    @Autowired
    private SbchEquipmentAllotTransnationalPortMapper sbchEquipmentAllotTransnationalPortMapper;

    /**
     * 查询跨国别设备调拨详情-港口调查
     *
     * @param id 跨国别设备调拨详情-港口调查ID
     * @return 跨国别设备调拨详情-港口调查
     */
    @Override
    public SbchEquipmentAllotTransnationalPort selectSbchEquipmentAllotTransnationalPortById(Long id) {
        return sbchEquipmentAllotTransnationalPortMapper.selectSbchEquipmentAllotTransnationalPortById(id);
    }

    /**
     * 查询跨国别设备调拨详情-港口调查列表
     * 
     * @param sbchEquipmentAllotTransnationalPort 跨国别设备调拨详情-港口调查
     * @return 跨国别设备调拨详情-港口调查
     */
    @Override
    public List<SbchEquipmentAllotTransnationalPort> selectSbchEquipmentAllotTransnationalPortList(SbchEquipmentAllotTransnationalPort sbchEquipmentAllotTransnationalPort) {
        return sbchEquipmentAllotTransnationalPortMapper.selectSbchEquipmentAllotTransnationalPortList(sbchEquipmentAllotTransnationalPort);
    }

    /**
     * 新增跨国别设备调拨详情-港口调查
     * 
     * @param sbchEquipmentAllotTransnationalPort 跨国别设备调拨详情-港口调查
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalPort(SbchEquipmentAllotTransnationalPort sbchEquipmentAllotTransnationalPort) {

    sbchEquipmentAllotTransnationalPort.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalPort.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalPortMapper.insertSbchEquipmentAllotTransnationalPort(sbchEquipmentAllotTransnationalPort);
    }

    /**
     * 修改跨国别设备调拨详情-港口调查
     * 
     * @param sbchEquipmentAllotTransnationalPort 跨国别设备调拨详情-港口调查
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalPort(SbchEquipmentAllotTransnationalPort sbchEquipmentAllotTransnationalPort) {
        sbchEquipmentAllotTransnationalPort.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalPortMapper.updateSbchEquipmentAllotTransnationalPort(sbchEquipmentAllotTransnationalPort);
    }

    /**
     * 删除跨国别设备调拨详情-港口调查对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalPortByIds(String ids) {
        return sbchEquipmentAllotTransnationalPortMapper.deleteSbchEquipmentAllotTransnationalPortByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-港口调查信息
     * 
     * @param id 跨国别设备调拨详情-港口调查ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalPortById(Long id) {
        return sbchEquipmentAllotTransnationalPortMapper.deleteSbchEquipmentAllotTransnationalPortById(id);
    }
}
