package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalExit;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalExitMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalExitService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-再出口调查Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-22
 */
@Service
public class SbchEquipmentAllotTransnationalExitServiceImpl implements ISbchEquipmentAllotTransnationalExitService {
    @Autowired
    private SbchEquipmentAllotTransnationalExitMapper sbchEquipmentAllotTransnationalExitMapper;

    /**
     * 查询跨国别设备调拨详情-再出口调查
     *
     * @param id 跨国别设备调拨详情-再出口调查ID
     * @return 跨国别设备调拨详情-再出口调查
     */
    @Override
    public SbchEquipmentAllotTransnationalExit selectSbchEquipmentAllotTransnationalExitById(Long id) {
        return sbchEquipmentAllotTransnationalExitMapper.selectSbchEquipmentAllotTransnationalExitById(id);
    }

    /**
     * 查询跨国别设备调拨详情-再出口调查列表
     * 
     * @param sbchEquipmentAllotTransnationalExit 跨国别设备调拨详情-再出口调查
     * @return 跨国别设备调拨详情-再出口调查
     */
    @Override
    public List<SbchEquipmentAllotTransnationalExit> selectSbchEquipmentAllotTransnationalExitList(SbchEquipmentAllotTransnationalExit sbchEquipmentAllotTransnationalExit) {
        return sbchEquipmentAllotTransnationalExitMapper.selectSbchEquipmentAllotTransnationalExitList(sbchEquipmentAllotTransnationalExit);
    }

    /**
     * 新增跨国别设备调拨详情-再出口调查
     * 
     * @param sbchEquipmentAllotTransnationalExit 跨国别设备调拨详情-再出口调查
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalExit(SbchEquipmentAllotTransnationalExit sbchEquipmentAllotTransnationalExit) {

    sbchEquipmentAllotTransnationalExit.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalExit.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalExitMapper.insertSbchEquipmentAllotTransnationalExit(sbchEquipmentAllotTransnationalExit);
    }

    /**
     * 修改跨国别设备调拨详情-再出口调查
     * 
     * @param sbchEquipmentAllotTransnationalExit 跨国别设备调拨详情-再出口调查
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalExit(SbchEquipmentAllotTransnationalExit sbchEquipmentAllotTransnationalExit) {
        sbchEquipmentAllotTransnationalExit.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalExitMapper.updateSbchEquipmentAllotTransnationalExit(sbchEquipmentAllotTransnationalExit);
    }

    /**
     * 删除跨国别设备调拨详情-再出口调查对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalExitByIds(String ids) {
        return sbchEquipmentAllotTransnationalExitMapper.deleteSbchEquipmentAllotTransnationalExitByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-再出口调查信息
     * 
     * @param id 跨国别设备调拨详情-再出口调查ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalExitById(Long id) {
        return sbchEquipmentAllotTransnationalExitMapper.deleteSbchEquipmentAllotTransnationalExitById(id);
    }
}
