package com.hhwy.pm.xmsl.bid.mapper;

import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-06 15:18:31
 * @remark
 */
public interface XmslBidWinHandoverFileMapper {

    XmslBidWinHandoverFile getXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile);

    List<XmslBidWinHandoverFile> getXmslBidWinHandoverFileList(
        XmslBidWinHandoverFile xmslBidWinHandoverFile);

    int insertXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile);

    int insertXmslBidWinHandoverFileList(
        @Param("xmslBidWinHandoverFileList") List<XmslBidWinHandoverFile> xmslBidWinHandoverFileList);

    int updateXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile);

    int updateXmslBidWinHandoverFileList(
        @Param("list") List<XmslBidWinHandoverFile> xmslBidWinHandoverFileList);

    int deleteXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile);

    int deleteXmslBidWinHandoverFileByPks(
        @Param("xmslBidWinHandoverFilePkList") List<Long> xmslBidWinHandoverFilePkList);
}
