package com.hhwy.pm.xmsl.bid.service;

import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverFile;
import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverInfo;

import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-06 15:13:41
 * @remark
 */
public interface IXmslBidWinHandoverInfoService {

    XmslBidWinHandoverInfo getXmslBidWinHandoverInfo(String fileName);

    void save(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    /**
     * 获取中标资料移交列表数据
     * @return
     */
    List<XmslBidWinHandoverFile> getBidWinHandoverFileList();
}
