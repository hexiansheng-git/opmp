package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalCost;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalCostMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalCostService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-费用估算Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-22
 */
@Service
public class SbchEquipmentAllotTransnationalCostServiceImpl implements ISbchEquipmentAllotTransnationalCostService {
    @Autowired
    private SbchEquipmentAllotTransnationalCostMapper sbchEquipmentAllotTransnationalCostMapper;

    /**
     * 查询跨国别设备调拨详情-费用估算
     *
     * @param id 跨国别设备调拨详情-费用估算ID
     * @return 跨国别设备调拨详情-费用估算
     */
    @Override
    public SbchEquipmentAllotTransnationalCost selectSbchEquipmentAllotTransnationalCostById(Long id) {
        return sbchEquipmentAllotTransnationalCostMapper.selectSbchEquipmentAllotTransnationalCostById(id);
    }

    /**
     * 查询跨国别设备调拨详情-费用估算列表
     * 
     * @param sbchEquipmentAllotTransnationalCost 跨国别设备调拨详情-费用估算
     * @return 跨国别设备调拨详情-费用估算
     */
    @Override
    public List<SbchEquipmentAllotTransnationalCost> selectSbchEquipmentAllotTransnationalCostList(SbchEquipmentAllotTransnationalCost sbchEquipmentAllotTransnationalCost) {
        return sbchEquipmentAllotTransnationalCostMapper.selectSbchEquipmentAllotTransnationalCostList(sbchEquipmentAllotTransnationalCost);
    }

    /**
     * 新增跨国别设备调拨详情-费用估算
     * 
     * @param sbchEquipmentAllotTransnationalCost 跨国别设备调拨详情-费用估算
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalCost(SbchEquipmentAllotTransnationalCost sbchEquipmentAllotTransnationalCost) {

    sbchEquipmentAllotTransnationalCost.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalCost.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalCostMapper.insertSbchEquipmentAllotTransnationalCost(sbchEquipmentAllotTransnationalCost);
    }

    /**
     * 修改跨国别设备调拨详情-费用估算
     * 
     * @param sbchEquipmentAllotTransnationalCost 跨国别设备调拨详情-费用估算
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalCost(SbchEquipmentAllotTransnationalCost sbchEquipmentAllotTransnationalCost) {
        sbchEquipmentAllotTransnationalCost.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalCostMapper.updateSbchEquipmentAllotTransnationalCost(sbchEquipmentAllotTransnationalCost);
    }

    /**
     * 删除跨国别设备调拨详情-费用估算对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalCostByIds(String ids) {
        return sbchEquipmentAllotTransnationalCostMapper.deleteSbchEquipmentAllotTransnationalCostByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-费用估算信息
     * 
     * @param id 跨国别设备调拨详情-费用估算ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalCostById(Long id) {
        return sbchEquipmentAllotTransnationalCostMapper.deleteSbchEquipmentAllotTransnationalCostById(id);
    }
}
