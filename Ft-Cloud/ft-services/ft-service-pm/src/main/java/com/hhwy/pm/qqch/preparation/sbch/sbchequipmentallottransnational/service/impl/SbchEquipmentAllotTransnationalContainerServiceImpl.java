package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;

import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalContainer;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalContainerMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalContainerService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-集装箱运输方案Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-22
 */
@Service
public class SbchEquipmentAllotTransnationalContainerServiceImpl implements ISbchEquipmentAllotTransnationalContainerService {
    @Autowired
    private SbchEquipmentAllotTransnationalContainerMapper sbchEquipmentAllotTransnationalContainerMapper;

    /**
     * 查询跨国别设备调拨详情-集装箱运输方案
     *
     * @param id 跨国别设备调拨详情-集装箱运输方案ID
     * @return 跨国别设备调拨详情-集装箱运输方案
     */
    @Override
    public SbchEquipmentAllotTransnationalContainer selectSbchEquipmentAllotTransnationalContainerById(Long id) {
        return sbchEquipmentAllotTransnationalContainerMapper.selectSbchEquipmentAllotTransnationalContainerById(id);
    }

    /**
     * 查询跨国别设备调拨详情-集装箱运输方案列表
     * 
     * @param sbchEquipmentAllotTransnationalContainer 跨国别设备调拨详情-集装箱运输方案
     * @return 跨国别设备调拨详情-集装箱运输方案
     */
    @Override
    public List<SbchEquipmentAllotTransnationalContainer> selectSbchEquipmentAllotTransnationalContainerList(SbchEquipmentAllotTransnationalContainer sbchEquipmentAllotTransnationalContainer) {
        return sbchEquipmentAllotTransnationalContainerMapper.selectSbchEquipmentAllotTransnationalContainerList(sbchEquipmentAllotTransnationalContainer);
    }

    /**
     * 新增跨国别设备调拨详情-集装箱运输方案
     * 
     * @param sbchEquipmentAllotTransnationalContainer 跨国别设备调拨详情-集装箱运输方案
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalContainer(SbchEquipmentAllotTransnationalContainer sbchEquipmentAllotTransnationalContainer) {

    sbchEquipmentAllotTransnationalContainer.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalContainer.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalContainerMapper.insertSbchEquipmentAllotTransnationalContainer(sbchEquipmentAllotTransnationalContainer);
    }

    /**
     * 修改跨国别设备调拨详情-集装箱运输方案
     * 
     * @param sbchEquipmentAllotTransnationalContainer 跨国别设备调拨详情-集装箱运输方案
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalContainer(SbchEquipmentAllotTransnationalContainer sbchEquipmentAllotTransnationalContainer) {
        sbchEquipmentAllotTransnationalContainer.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalContainerMapper.updateSbchEquipmentAllotTransnationalContainer(sbchEquipmentAllotTransnationalContainer);
    }

    /**
     * 删除跨国别设备调拨详情-集装箱运输方案对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalContainerByIds(String ids) {
        return sbchEquipmentAllotTransnationalContainerMapper.deleteSbchEquipmentAllotTransnationalContainerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-集装箱运输方案信息
     * 
     * @param id 跨国别设备调拨详情-集装箱运输方案ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalContainerById(Long id) {
        return sbchEquipmentAllotTransnationalContainerMapper.deleteSbchEquipmentAllotTransnationalContainerById(id);
    }
}
