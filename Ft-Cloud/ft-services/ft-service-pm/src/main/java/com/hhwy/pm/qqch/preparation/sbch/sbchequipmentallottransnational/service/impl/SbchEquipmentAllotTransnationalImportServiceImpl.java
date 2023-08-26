package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalImport;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalImportMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalImportService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨国别设备调拨详情-进口调查Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-22
 */
@Service
public class SbchEquipmentAllotTransnationalImportServiceImpl implements ISbchEquipmentAllotTransnationalImportService {
    @Autowired
    private SbchEquipmentAllotTransnationalImportMapper sbchEquipmentAllotTransnationalImportMapper;

    /**
     * 查询跨国别设备调拨详情-进口调查
     *
     * @param id 跨国别设备调拨详情-进口调查ID
     * @return 跨国别设备调拨详情-进口调查
     */
    @Override
    public SbchEquipmentAllotTransnationalImport selectSbchEquipmentAllotTransnationalImportById(Long id) {
        return sbchEquipmentAllotTransnationalImportMapper.selectSbchEquipmentAllotTransnationalImportById(id);
    }

    /**
     * 查询跨国别设备调拨详情-进口调查列表
     * 
     * @param sbchEquipmentAllotTransnationalImport 跨国别设备调拨详情-进口调查
     * @return 跨国别设备调拨详情-进口调查
     */
    @Override
    public List<SbchEquipmentAllotTransnationalImport> selectSbchEquipmentAllotTransnationalImportList(SbchEquipmentAllotTransnationalImport sbchEquipmentAllotTransnationalImport) {
        return sbchEquipmentAllotTransnationalImportMapper.selectSbchEquipmentAllotTransnationalImportList(sbchEquipmentAllotTransnationalImport);
    }

    /**
     * 新增跨国别设备调拨详情-进口调查
     * 
     * @param sbchEquipmentAllotTransnationalImport 跨国别设备调拨详情-进口调查
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalImport(SbchEquipmentAllotTransnationalImport sbchEquipmentAllotTransnationalImport) {

    sbchEquipmentAllotTransnationalImport.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalImport.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalImportMapper.insertSbchEquipmentAllotTransnationalImport(sbchEquipmentAllotTransnationalImport);
    }

    /**
     * 修改跨国别设备调拨详情-进口调查
     * 
     * @param sbchEquipmentAllotTransnationalImport 跨国别设备调拨详情-进口调查
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalImport(SbchEquipmentAllotTransnationalImport sbchEquipmentAllotTransnationalImport) {
        sbchEquipmentAllotTransnationalImport.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalImportMapper.updateSbchEquipmentAllotTransnationalImport(sbchEquipmentAllotTransnationalImport);
    }

    /**
     * 删除跨国别设备调拨详情-进口调查对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalImportByIds(String ids) {
        return sbchEquipmentAllotTransnationalImportMapper.deleteSbchEquipmentAllotTransnationalImportByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情-进口调查信息
     * 
     * @param id 跨国别设备调拨详情-进口调查ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalImportById(Long id) {
        return sbchEquipmentAllotTransnationalImportMapper.deleteSbchEquipmentAllotTransnationalImportById(id);
    }
}
