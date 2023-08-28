package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllotDetails;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.mapper.SbchEquipmentAllotDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.service.ISbchEquipmentAllotDetailsService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 同国别设备详情Service业务层处理
 *
 * @author hwj
 * @date 2022-11-25
 */
@Service
public class SbchEquipmentAllotDetailsServiceImpl implements ISbchEquipmentAllotDetailsService {
    @Autowired
    private SbchEquipmentAllotDetailsMapper sbchEquipmentAllotDetailsMapper;

    /**
     * 查询同国别设备详情
     *
     * @param id 同国别设备详情ID
     * @return 同国别设备详情
     */
    @Override
    public SbchEquipmentAllotDetails selectSbchEquipmentAllotDetailsById(Long id) {
        return sbchEquipmentAllotDetailsMapper.selectSbchEquipmentAllotDetailsById(id);
    }

    /**
     * 查询同国别设备详情列表
     *
     * @param sbchEquipmentAllotDetails 同国别设备详情
     * @return 同国别设备详情
     */
    @Override
    public List<SbchEquipmentAllotDetails> selectSbchEquipmentAllotDetailsList(SbchEquipmentAllotDetails sbchEquipmentAllotDetails) {
        return sbchEquipmentAllotDetailsMapper.selectSbchEquipmentAllotDetailsList(sbchEquipmentAllotDetails);
    }

    /**
     * 新增同国别设备详情
     *
     * @param sbchEquipmentAllotDetails 同国别设备详情
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotDetails(SbchEquipmentAllotDetails sbchEquipmentAllotDetails) {

    sbchEquipmentAllotDetails.setId(IdWorker.createId());

        sbchEquipmentAllotDetails.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotDetailsMapper.insertSbchEquipmentAllotDetails(sbchEquipmentAllotDetails);
    }

    /**
     * 修改同国别设备详情
     *
     * @param sbchEquipmentAllotDetails 同国别设备详情
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotDetails(SbchEquipmentAllotDetails sbchEquipmentAllotDetails) {
        sbchEquipmentAllotDetails.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotDetailsMapper.updateSbchEquipmentAllotDetails(sbchEquipmentAllotDetails);
    }

    /**
     * 删除同国别设备详情对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotDetailsByIds(String ids) {
        return sbchEquipmentAllotDetailsMapper.deleteSbchEquipmentAllotDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除同国别设备详情信息
     *
     * @param id 同国别设备详情ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotDetailsById(Long id) {
        return sbchEquipmentAllotDetailsMapper.deleteSbchEquipmentAllotDetailsById(id);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentAllotDetails> detailList, Long mainId,Boolean isAdjust) {
//        删除原数据
        sbchEquipmentAllotDetailsMapper.deleteSbchEquipmentAllotDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentAllotDetails> insertOrUpdateData = detailList.stream().map(item -> {
                Long id = IdWorker.createId();
                /*不是调整*/
//                if (!isAdjust) {
//                    id = item.getId() == null ? IdWorker.createId() : item.getId();
//                }
                item.setId(id);
                item.setMainId(mainId);
                EntityUtils.setCreateUpdateInfo(item);
                return item;

            }).collect(Collectors.toList());
            return sbchEquipmentAllotDetailsMapper.batchInsert(insertOrUpdateData);
        }
        return 1;
    }
}
