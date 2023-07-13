package com.hhwy.pm.xmsl.bid.mapper;

import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-06 15:13:41
 * @remark
 */
public interface XmslBidWinHandoverInfoMapper {

    XmslBidWinHandoverInfo getXmslBidWinHandoverInfo(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    List<XmslBidWinHandoverInfo> getXmslBidWinHandoverInfoList(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    int insertXmslBidWinHandoverInfo(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    int insertXmslBidWinHandoverInfoList(
        @Param("xmslBidWinHandoverInfoList") List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoList);

    int updateXmslBidWinHandoverInfo(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    int updateXmslBidWinHandoverInfoList(@Param("list") List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoList);

    int deleteXmslBidWinHandoverInfo(XmslBidWinHandoverInfo xmslBidWinHandoverInfo);

    int deleteXmslBidWinHandoverInfoByPks(
        @Param("xmslBidWinHandoverInfoPkList") List<Long> xmslBidWinHandoverInfoPkList);
}
