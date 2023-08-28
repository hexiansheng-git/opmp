package com.hhwy.pm.qqch.preparation.sbch.sblease.service.impl;

import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentSupplierDetails;
import com.hhwy.pm.qqch.preparation.sbch.sblease.mapper.SbchEquipmentSupplierDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sblease.service.ISbchEquipmentSupplierDetailsService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zqq
 * @create 2023-08-26 17:38
 */
@Service
public class SbchEquipmentSupplierDetailsServiceImpl implements ISbchEquipmentSupplierDetailsService {
    @Autowired
    private SbchEquipmentSupplierDetailsMapper sbchEquipmentSupplierDetailsMapper;

    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentSupplierDetails> detailList, Long mainId, Boolean isAdjust) {
        sbchEquipmentSupplierDetailsMapper.deleteSbchEquipmentSupplierDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentSupplierDetails> insertOrUpdateData = detailList.stream().map(item -> {
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
            return sbchEquipmentSupplierDetailsMapper.batchInsert(insertOrUpdateData);
        }
        return 1;
    }
}
