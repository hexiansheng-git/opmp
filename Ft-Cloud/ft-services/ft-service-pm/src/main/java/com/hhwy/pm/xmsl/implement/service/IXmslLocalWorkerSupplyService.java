package com.hhwy.pm.xmsl.implement.service;

import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:57
 * @remark 属地工人供应情况
 */
public interface IXmslLocalWorkerSupplyService {
    int deleteXmslLocalWorkerSupplyByPks(List<Long> xmslLocalWorkerSupplyPkList);
}
