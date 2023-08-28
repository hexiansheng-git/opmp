package com.hhwy.pm.qqch.preparation.sbch.sblease.service;

import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentSupplierDetails;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-26 17:39
 */
public interface ISbchEquipmentSupplierDetailsService {

    /**
     * 新增和编辑 数据保存
     * @return 结果
     */
    int insertOrEditBatchByMainId(List<SbchEquipmentSupplierDetails> list, Long mainId, Boolean isAdjust);
}
