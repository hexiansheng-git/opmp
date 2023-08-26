package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;

import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalBulk;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalBulkMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalBulkService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-散货运输方案Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Service
public class SbchEquipmentAllotTransnationalBulkServiceImpl implements ISbchEquipmentAllotTransnationalBulkService {
    @Autowired
    private SbchEquipmentAllotTransnationalBulkMapper sbchEquipmentAllotTransnationalBulkMapper;

    /**
     * 查询跨国别设备调拨详情-散货运输方案
     * 
     * @param id 跨国别设备调拨详情-散货运输方案ID
     * @return 跨国别设备调拨详情-散货运输方案
     */
    @Override
    public SbchEquipmentAllotTransnationalBulk selectSbchEquipmentAllotTransnationalBulkById(Long id) {
        return sbchEquipmentAllotTransnationalBulkMapper.selectSbchEquipmentAllotTransnationalBulkById(id);
    }

    /**
     * 查询跨国别设备调拨详情-散货运输方案列表
     * 
     * @param sbchEquipmentAllotTransnationalBulk 跨国别设备调拨详情-散货运输方案
     * @return 跨国别设备调拨详情-散货运输方案
     */
    @Override
    public List<SbchEquipmentAllotTransnationalBulk> selectSbchEquipmentAllotTransnationalBulkList(SbchEquipmentAllotTransnationalBulk sbchEquipmentAllotTransnationalBulk) {
        return sbchEquipmentAllotTransnationalBulkMapper.selectSbchEquipmentAllotTransnationalBulkList(sbchEquipmentAllotTransnationalBulk);
    }

    /**
     * 新增跨国别设备调拨详情-散货运输方案
     * 
     * @param sbchEquipmentAllotTransnationalBulk 跨国别设备调拨详情-散货运输方案
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalBulk(SbchEquipmentAllotTransnationalBulk sbchEquipmentAllotTransnationalBulk) {

    sbchEquipmentAllotTransnationalBulk.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalBulk.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalBulkMapper.insertSbchEquipmentAllotTransnationalBulk(sbchEquipmentAllotTransnationalBulk);
    }

    /**
     * 修改跨国别设备调拨详情-散货运输方案
     * 
     * @param sbchEquipmentAllotTransnationalBulk 跨国别设备调拨详情-散货运输方案
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalBulk(SbchEquipmentAllotTransnationalBulk sbchEquipmentAllotTransnationalBulk) {
        sbchEquipmentAllotTransnationalBulk.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalBulkMapper.updateSbchEquipmentAllotTransnationalBulk(sbchEquipmentAllotTransnationalBulk);
    }

    /**
     * 删除跨国别设备调拨详情-散货运输方案对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalBulkByIds(String ids) {
        return sbchEquipmentAllotTransnationalBulkMapper.deleteSbchEquipmentAllotTransnationalBulkByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-散货运输方案信息
     * 
     * @param id 跨国别设备调拨详情-散货运输方案ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalBulkById(Long id) {
        return sbchEquipmentAllotTransnationalBulkMapper.deleteSbchEquipmentAllotTransnationalBulkById(id);
    }
}
