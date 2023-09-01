package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.domain.SbchEquipmentSpecialControlPlanDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.mapper.SbchEquipmentSpecialControlPlanDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.service.ISbchEquipmentSpecialControlPlanDetailsService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 特种设备过程管控策划详情Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-07
 */
@Service
public class SbchEquipmentSpecialControlPlanDetailsServiceImpl implements ISbchEquipmentSpecialControlPlanDetailsService {
    @Autowired
    private SbchEquipmentSpecialControlPlanDetailsMapper sbchEquipmentSpecialControlPlanDetailsMapper;

    /**
     * 查询特种设备过程管控策划详情
     * 
     * @param id 特种设备过程管控策划详情ID
     * @return 特种设备过程管控策划详情
     */
    @Override
    public SbchEquipmentSpecialControlPlanDetails selectSbchEquipmentSpecialControlPlanDetailsById(Long id) {
        return sbchEquipmentSpecialControlPlanDetailsMapper.selectSbchEquipmentSpecialControlPlanDetailsById(id);
    }

    /**
     * 查询特种设备过程管控策划详情列表
     * 
     * @param sbchEquipmentSpecialControlPlanDetails 特种设备过程管控策划详情
     * @return 特种设备过程管控策划详情
     */
    @Override
    public List<SbchEquipmentSpecialControlPlanDetails> selectSbchEquipmentSpecialControlPlanDetailsList(SbchEquipmentSpecialControlPlanDetails sbchEquipmentSpecialControlPlanDetails) {
        return sbchEquipmentSpecialControlPlanDetailsMapper.selectSbchEquipmentSpecialControlPlanDetailsList(sbchEquipmentSpecialControlPlanDetails);
    }

    /**
     * 新增特种设备过程管控策划详情
     * 
     * @param sbchEquipmentSpecialControlPlanDetails 特种设备过程管控策划详情
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentSpecialControlPlanDetails(SbchEquipmentSpecialControlPlanDetails sbchEquipmentSpecialControlPlanDetails) {

    sbchEquipmentSpecialControlPlanDetails.setId(IdWorker.createId());

        sbchEquipmentSpecialControlPlanDetails.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentSpecialControlPlanDetailsMapper.insertSbchEquipmentSpecialControlPlanDetails(sbchEquipmentSpecialControlPlanDetails);
    }

    /**
     * 修改特种设备过程管控策划详情
     * 
     * @param sbchEquipmentSpecialControlPlanDetails 特种设备过程管控策划详情
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentSpecialControlPlanDetails(SbchEquipmentSpecialControlPlanDetails sbchEquipmentSpecialControlPlanDetails) {
        sbchEquipmentSpecialControlPlanDetails.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentSpecialControlPlanDetailsMapper.updateSbchEquipmentSpecialControlPlanDetails(sbchEquipmentSpecialControlPlanDetails);
    }

    /**
     * 删除特种设备过程管控策划详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentSpecialControlPlanDetailsByIds(String ids) {
        return sbchEquipmentSpecialControlPlanDetailsMapper.deleteSbchEquipmentSpecialControlPlanDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除特种设备过程管控策划详情信息
     * 
     * @param id 特种设备过程管控策划详情ID
     * @return 结果
     */
    public int deleteSbchEquipmentSpecialControlPlanDetailsById(Long id) {
        return sbchEquipmentSpecialControlPlanDetailsMapper.deleteSbchEquipmentSpecialControlPlanDetailsById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentSpecialControlPlanDetails> detailList, Long mainId, Boolean isAdjust) {
        sbchEquipmentSpecialControlPlanDetailsMapper.deleteSbchEquipmentSpecialControlPlanDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentSpecialControlPlanDetails> insertOrUpdateData = detailList.stream().map(item -> {
//                Long id = item.getId() == null ? IdWorker.createId() : item.getId();
                item.setId(IdWorker.createId());
                item.setMainId(mainId);
                EntityUtils.setCreateUpdateInfo(item);
                return item;

            }).collect(Collectors.toList());
            return sbchEquipmentSpecialControlPlanDetailsMapper.batchInsert(insertOrUpdateData);
        }
        return 1;
    }
}
