package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalTechnology;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalTechnologyMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalTechnologyService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-技术评估Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Service
public class SbchEquipmentAllotTransnationalTechnologyServiceImpl implements ISbchEquipmentAllotTransnationalTechnologyService {
    @Autowired
    private SbchEquipmentAllotTransnationalTechnologyMapper sbchEquipmentAllotTransnationalTechnologyMapper;

    /**
     * 查询跨国别设备调拨详情-技术评估
     * 
     * @param id 跨国别设备调拨详情-技术评估ID
     * @return 跨国别设备调拨详情-技术评估
     */
    @Override
    public SbchEquipmentAllotTransnationalTechnology selectSbchEquipmentAllotTransnationalTechnologyById(Long id) {
        return sbchEquipmentAllotTransnationalTechnologyMapper.selectSbchEquipmentAllotTransnationalTechnologyById(id);
    }

    /**
     * 查询跨国别设备调拨详情-技术评估列表
     * 
     * @param sbchEquipmentAllotTransnationalTechnology 跨国别设备调拨详情-技术评估
     * @return 跨国别设备调拨详情-技术评估
     */
    @Override
    public List<SbchEquipmentAllotTransnationalTechnology> selectSbchEquipmentAllotTransnationalTechnologyList(SbchEquipmentAllotTransnationalTechnology sbchEquipmentAllotTransnationalTechnology) {
        return sbchEquipmentAllotTransnationalTechnologyMapper.selectSbchEquipmentAllotTransnationalTechnologyList(sbchEquipmentAllotTransnationalTechnology);
    }

    /**
     * 新增跨国别设备调拨详情-技术评估
     * 
     * @param sbchEquipmentAllotTransnationalTechnology 跨国别设备调拨详情-技术评估
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalTechnology(SbchEquipmentAllotTransnationalTechnology sbchEquipmentAllotTransnationalTechnology) {

    sbchEquipmentAllotTransnationalTechnology.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalTechnology.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalTechnologyMapper.insertSbchEquipmentAllotTransnationalTechnology(sbchEquipmentAllotTransnationalTechnology);
    }

    /**
     * 修改跨国别设备调拨详情-技术评估
     * 
     * @param sbchEquipmentAllotTransnationalTechnology 跨国别设备调拨详情-技术评估
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalTechnology(SbchEquipmentAllotTransnationalTechnology sbchEquipmentAllotTransnationalTechnology) {
        sbchEquipmentAllotTransnationalTechnology.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalTechnologyMapper.updateSbchEquipmentAllotTransnationalTechnology(sbchEquipmentAllotTransnationalTechnology);
    }

    /**
     * 删除跨国别设备调拨详情-技术评估对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalTechnologyByIds(String ids) {
        return sbchEquipmentAllotTransnationalTechnologyMapper.deleteSbchEquipmentAllotTransnationalTechnologyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-技术评估信息
     * 
     * @param id 跨国别设备调拨详情-技术评估ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalTechnologyById(Long id) {
        return sbchEquipmentAllotTransnationalTechnologyMapper.deleteSbchEquipmentAllotTransnationalTechnologyById(id);
    }
}
