package com.hhwy.pm.qqch.preparation.sbch.sblease.service;

import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.LeaseVo;

import java.math.BigDecimal;

/**
 * @author zqq
 * @create 2023-08-26 16:46
 */
public interface ISbchEquipmentService {
    LeaseVo getList(BigDecimal version, Long[] ids);

    void batchAdd(LeaseVo leaseVo);
}
