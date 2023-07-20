package com.hhwy.pm.xmsl.bid.service;

import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverInfo;

/**
 * @author zhenglili
 * @date 2023-07-06 15:13:41
 * @remark
 */
public interface IXmslBidWinHandoverInfoService {

    XmslBidWinHandoverInfo getXmslBidWinHandoverInfo();

    void save(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);
}
