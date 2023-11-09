package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecialDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.mapper.SbchEquipmentSpecialDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.ISbchEquipmentSpecialDetailsService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 特种设备管理详情Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-05
 */
@Service
public class SbchEquipmentSpecialDetailsServiceImpl implements ISbchEquipmentSpecialDetailsService {
    @Autowired
    private SbchEquipmentSpecialDetailsMapper sbchEquipmentSpecialDetailsMapper;

    /**
     * 查询特种设备管理详情
     * 
     * @param id 特种设备管理详情ID
     * @return 特种设备管理详情
     */
    @Override
    public SbchEquipmentSpecialDetails selectSbchEquipmentSpecialDetailsById(Long id) {
        return sbchEquipmentSpecialDetailsMapper.selectSbchEquipmentSpecialDetailsById(id);
    }

    /**
     * 查询特种设备管理详情列表
     * 
     * @param sbchEquipmentSpecialDetails 特种设备管理详情
     * @return 特种设备管理详情
     */
    @Override
    public List<SbchEquipmentSpecialDetails> selectSbchEquipmentSpecialDetailsList(SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails) {
        return sbchEquipmentSpecialDetailsMapper.selectSbchEquipmentSpecialDetailsList(sbchEquipmentSpecialDetails);
    }

    /**
     * 新增特种设备管理详情
     * 
     * @param sbchEquipmentSpecialDetails 特种设备管理详情
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentSpecialDetails(SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails) {

    sbchEquipmentSpecialDetails.setId(IdWorker.createId());

        sbchEquipmentSpecialDetails.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentSpecialDetailsMapper.insertSbchEquipmentSpecialDetails(sbchEquipmentSpecialDetails);
    }

    /**
     * 修改特种设备管理详情
     * 
     * @param sbchEquipmentSpecialDetails 特种设备管理详情
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentSpecialDetails(SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails) {
        sbchEquipmentSpecialDetails.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentSpecialDetailsMapper.updateSbchEquipmentSpecialDetails(sbchEquipmentSpecialDetails);
    }

    /**
     * 删除特种设备管理详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentSpecialDetailsByIds(String ids) {
        return sbchEquipmentSpecialDetailsMapper.deleteSbchEquipmentSpecialDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除特种设备管理详情信息
     * 
     * @param id 特种设备管理详情ID
     * @return 结果
     */
    public int deleteSbchEquipmentSpecialDetailsById(Long id) {
        return sbchEquipmentSpecialDetailsMapper.deleteSbchEquipmentSpecialDetailsById(id);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentSpecialDetails> detailList, Long mainId, Boolean isAdjust) {
        sbchEquipmentSpecialDetailsMapper.deleteSbchEquipmentSpecialDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentSpecialDetails> insertOrUpdateData = detailList.stream().map(item -> {
//                Long id = item.getId() == null ? IdWorker.createId() : item.getId();
                item.setId(IdWorker.createId());
                item.setMainId(mainId);
                item.setMaterialName(item.getMaterialName().trim());
                EntityUtils.setCreateUpdateInfo(item);
                return item;

            }).collect(Collectors.toList());
            return sbchEquipmentSpecialDetailsMapper.batchInsert(insertOrUpdateData);
        }
        return 1;
    }
}
