package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlanDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.mapper.SbchEquipmentSpecialPlanDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service.ISbchEquipmentSpecialPlanDetailsService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 特种设备风险识别和措施策划详情Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-06
 */
@Service
public class SbchEquipmentSpecialPlanDetailsServiceImpl implements ISbchEquipmentSpecialPlanDetailsService {
    @Autowired
    private SbchEquipmentSpecialPlanDetailsMapper sbchEquipmentSpecialPlanDetailsMapper;

    /**
     * 查询特种设备风险识别和措施策划详情
     * 
     * @param id 特种设备风险识别和措施策划详情ID
     * @return 特种设备风险识别和措施策划详情
     */
    @Override
    public SbchEquipmentSpecialPlanDetails selectSbchEquipmentSpecialPlanDetailsById(Long id) {
        return sbchEquipmentSpecialPlanDetailsMapper.selectSbchEquipmentSpecialPlanDetailsById(id);
    }

    /**
     * 查询特种设备风险识别和措施策划详情列表
     * 
     * @param sbchEquipmentSpecialPlanDetails 特种设备风险识别和措施策划详情
     * @return 特种设备风险识别和措施策划详情
     */
    @Override
    public List<SbchEquipmentSpecialPlanDetails> selectSbchEquipmentSpecialPlanDetailsList(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails) {
        return sbchEquipmentSpecialPlanDetailsMapper.selectSbchEquipmentSpecialPlanDetailsList(sbchEquipmentSpecialPlanDetails);
    }
    @Override
    public List<SbchEquipmentSpecialPlanDetails> selectSbchEquipmentSpecialPlanDetailshistoryList(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails) {
        return sbchEquipmentSpecialPlanDetailsMapper.selectSbchEquipmentSpecialPlanDetailshistoryList(sbchEquipmentSpecialPlanDetails);
    }

    /**
     * 新增特种设备风险识别和措施策划详情
     * 
     * @param sbchEquipmentSpecialPlanDetails 特种设备风险识别和措施策划详情
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentSpecialPlanDetails(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails) {

    sbchEquipmentSpecialPlanDetails.setId(IdWorker.createId());

        sbchEquipmentSpecialPlanDetails.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentSpecialPlanDetailsMapper.insertSbchEquipmentSpecialPlanDetails(sbchEquipmentSpecialPlanDetails);
    }

    /**
     * 修改特种设备风险识别和措施策划详情
     * 
     * @param sbchEquipmentSpecialPlanDetails 特种设备风险识别和措施策划详情
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentSpecialPlanDetails(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails) {
        sbchEquipmentSpecialPlanDetails.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentSpecialPlanDetailsMapper.updateSbchEquipmentSpecialPlanDetails(sbchEquipmentSpecialPlanDetails);
    }

    /**
     * 删除特种设备风险识别和措施策划详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentSpecialPlanDetailsByIds(String ids) {
        return sbchEquipmentSpecialPlanDetailsMapper.deleteSbchEquipmentSpecialPlanDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除特种设备风险识别和措施策划详情信息
     * 
     * @param id 特种设备风险识别和措施策划详情ID
     * @return 结果
     */
    public int deleteSbchEquipmentSpecialPlanDetailsById(Long id) {
        return sbchEquipmentSpecialPlanDetailsMapper.deleteSbchEquipmentSpecialPlanDetailsById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentSpecialPlanDetails> detailList, Long mainId, Boolean isAdjust) {
        sbchEquipmentSpecialPlanDetailsMapper.deleteSbchEquipmentSpecialPlanDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentSpecialPlanDetails> insertOrUpdateData = detailList.stream().map(item -> {
//                Long id = item.getId() == null ? IdWorker.createId() : item.getId();
                item.setId(IdWorker.createId());
                item.setMainId(mainId);
                EntityUtils.setCreateUpdateInfo(item);
                return item;

            }).collect(Collectors.toList());
            return sbchEquipmentSpecialPlanDetailsMapper.batchInsert(insertOrUpdateData);
        }
        return 1;
    }

    @Override
    public List<SbchEquipmentSpecialPlanDetails> getListByDeviceCode(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails) {
        return sbchEquipmentSpecialPlanDetailsMapper.getListByDeviceCode(sbchEquipmentSpecialPlanDetails);
    }
}
