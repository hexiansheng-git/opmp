package com.hhwy.pm.qqch.preparation.sbch.sblease.service.impl;

import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentLeaseDetails;
import com.hhwy.pm.qqch.preparation.sbch.sblease.mapper.SbchEquipmentLeaseDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sblease.service.ISbchEquipmentLeaseDetailsService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zqq
 * @create 2023-08-26 17:47
 */
@Service
public class ISbchEquipmentLeaseDetailsServiceImpl implements ISbchEquipmentLeaseDetailsService {
    @Autowired
    private SbchEquipmentLeaseDetailsMapper sbchEquipmentLeaseDetailsMapper;
    /**
     * 更新详情信息
     *
     * @param isAdjust  是否调整
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentLeaseDetails> detailList, Long mainId, Boolean isAdjust) {
        sbchEquipmentLeaseDetailsMapper.deleteSbchEquipmentLeaseDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentLeaseDetails> insertOrUpdateData = detailList.stream().map(item -> {
                Long id = IdWorker.createId();
                /*不是调整*/
//                if (!isAdjust) {
//                    id = item.getId() == null ? IdWorker.createId() : item.getId();
//                }
                item.setIsSpecialEqu("0");
                item.setId(id);
                item.setMainId(mainId);
                EntityUtils.setCreateUpdateInfo(item);
                return item;

            }).collect(Collectors.toList());
            return sbchEquipmentLeaseDetailsMapper.batchInsert(insertOrUpdateData);
        }
        return 1;
    }
}
