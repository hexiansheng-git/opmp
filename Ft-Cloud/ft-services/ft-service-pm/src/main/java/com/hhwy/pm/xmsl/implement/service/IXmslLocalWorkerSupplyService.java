package com.hhwy.pm.xmsl.implement.service;

import com.hhwy.pm.xmsl.implement.domain.XmslLocalWorkerSupply;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:57
 * @remark 属地工人供应情况
 */
public interface IXmslLocalWorkerSupplyService {

    List<XmslLocalWorkerSupply> getXmslLocalWorkerSupplyList(XmslLocalWorkerSupply xmslLocalWorkerSupply);

    int deleteXmslLocalWorkerSupplyByPks(List<Long> xmslLocalWorkerSupplyPkList);
}
