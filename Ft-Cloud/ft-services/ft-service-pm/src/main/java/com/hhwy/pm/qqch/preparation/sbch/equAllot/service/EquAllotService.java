package com.hhwy.pm.qqch.preparation.sbch.equAllot.service;

import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.ActiveEquVo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.EquAllotVo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.XcsbMonthSelfEquInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zqq
 * @create 2023-08-25 16:10
 */
public interface EquAllotService {
    EquAllotVo getList(BigDecimal version);

    void batchAdd(EquAllotVo equAllotVo);

    List<XcsbMonthSelfEquInfo> xzxcsb(ActiveEquVo activeEquVo);
}
