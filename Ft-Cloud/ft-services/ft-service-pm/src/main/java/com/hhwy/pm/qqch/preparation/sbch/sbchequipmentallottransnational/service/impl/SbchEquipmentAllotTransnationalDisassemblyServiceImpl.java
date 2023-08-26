package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalDisassembly;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalDisassemblyMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalDisassemblyService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-拆卸吊装方案Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Service
public class SbchEquipmentAllotTransnationalDisassemblyServiceImpl implements ISbchEquipmentAllotTransnationalDisassemblyService {
    @Autowired
    private SbchEquipmentAllotTransnationalDisassemblyMapper sbchEquipmentAllotTransnationalDisassemblyMapper;

    /**
     * 查询跨国别设备调拨详情-拆卸吊装方案
     * 
     * @param id 跨国别设备调拨详情-拆卸吊装方案ID
     * @return 跨国别设备调拨详情-拆卸吊装方案
     */
    @Override
    public SbchEquipmentAllotTransnationalDisassembly selectSbchEquipmentAllotTransnationalDisassemblyById(Long id) {
        return sbchEquipmentAllotTransnationalDisassemblyMapper.selectSbchEquipmentAllotTransnationalDisassemblyById(id);
    }

    /**
     * 查询跨国别设备调拨详情-拆卸吊装方案列表
     * 
     * @param sbchEquipmentAllotTransnationalDisassembly 跨国别设备调拨详情-拆卸吊装方案
     * @return 跨国别设备调拨详情-拆卸吊装方案
     */
    @Override
    public List<SbchEquipmentAllotTransnationalDisassembly> selectSbchEquipmentAllotTransnationalDisassemblyList(SbchEquipmentAllotTransnationalDisassembly sbchEquipmentAllotTransnationalDisassembly) {
        return sbchEquipmentAllotTransnationalDisassemblyMapper.selectSbchEquipmentAllotTransnationalDisassemblyList(sbchEquipmentAllotTransnationalDisassembly);
    }

    /**
     * 新增跨国别设备调拨详情-拆卸吊装方案
     * 
     * @param sbchEquipmentAllotTransnationalDisassembly 跨国别设备调拨详情-拆卸吊装方案
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalDisassembly(SbchEquipmentAllotTransnationalDisassembly sbchEquipmentAllotTransnationalDisassembly) {

    sbchEquipmentAllotTransnationalDisassembly.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalDisassembly.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalDisassemblyMapper.insertSbchEquipmentAllotTransnationalDisassembly(sbchEquipmentAllotTransnationalDisassembly);
    }

    /**
     * 修改跨国别设备调拨详情-拆卸吊装方案
     * 
     * @param sbchEquipmentAllotTransnationalDisassembly 跨国别设备调拨详情-拆卸吊装方案
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalDisassembly(SbchEquipmentAllotTransnationalDisassembly sbchEquipmentAllotTransnationalDisassembly) {
        sbchEquipmentAllotTransnationalDisassembly.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalDisassemblyMapper.updateSbchEquipmentAllotTransnationalDisassembly(sbchEquipmentAllotTransnationalDisassembly);
    }

    /**
     * 删除跨国别设备调拨详情-拆卸吊装方案对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalDisassemblyByIds(String ids) {
        return sbchEquipmentAllotTransnationalDisassemblyMapper.deleteSbchEquipmentAllotTransnationalDisassemblyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-拆卸吊装方案信息
     * 
     * @param id 跨国别设备调拨详情-拆卸吊装方案ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalDisassemblyById(Long id) {
        return sbchEquipmentAllotTransnationalDisassemblyMapper.deleteSbchEquipmentAllotTransnationalDisassemblyById(id);
    }
}
