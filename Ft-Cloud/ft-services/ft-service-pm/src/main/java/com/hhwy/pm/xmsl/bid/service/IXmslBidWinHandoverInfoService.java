package com.hhwy.pm.xmsl.bid.service;

import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverInfo;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-06 15:13:41
 * @remark
 */
public interface IXmslBidWinHandoverInfoService {

    XmslBidWinHandoverInfo getXmslBidWinHandoverInfo();

    List<XmslBidWinHandoverInfo> getXmslBidWinHandoverInfoList(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    void save(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    int insertXmslBidWinHandoverInfoList(List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoList);

    int updateXmslBidWinHandoverInfo(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    int updateXmslBidWinHandoverInfoList(List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoList);

    int deleteXmslBidWinHandoverInfo(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    int deleteXmslBidWinHandoverInfoByPks(List<Long> xmslBidWinHandoverInfoPkList);
}
