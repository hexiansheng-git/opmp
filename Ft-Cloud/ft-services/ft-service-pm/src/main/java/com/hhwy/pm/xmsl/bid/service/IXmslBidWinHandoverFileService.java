package com.hhwy.pm.xmsl.bid.service;

import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverFile;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-06 15:18:31
 * @remark 中标项目移交文件
 */
public interface IXmslBidWinHandoverFileService {

    XmslBidWinHandoverFile getXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile);

    List<XmslBidWinHandoverFile> getXmslBidWinHandoverFileList(
        XmslBidWinHandoverFile xmslBidWinHandoverFile);

    int insertXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile);

    int insertXmslBidWinHandoverFileList(List<XmslBidWinHandoverFile> xmslBidWinHandoverFileList);

    int updateXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile);

    int updateXmslBidWinHandoverFileList(List<XmslBidWinHandoverFile> xmslBidWinHandoverFileList);

    int deleteXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile);

    int deleteXmslBidWinHandoverFileByPks(List<Long> xmslBidWinHandoverFilePkList);
}
