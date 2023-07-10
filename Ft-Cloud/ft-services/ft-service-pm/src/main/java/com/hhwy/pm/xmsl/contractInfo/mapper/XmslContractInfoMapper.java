package com.hhwy.pm.xmsl.contractInfo.mapper;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 13:57:39
 * @remark
 */
public interface XmslContractInfoMapper {

    XmslContractInfo getXmslContractInfo(XmslContractInfo xmslContractInfo);

    List<XmslContractInfo> getXmslContractInfoList(XmslContractInfo xmslContractInfo);

    int insertXmslContractInfo(XmslContractInfo xmslContractInfo);

    int insertXmslContractInfoList(@Param("xmslContractInfoList") List<XmslContractInfo> xmslContractInfoList);

    int updateXmslContractInfo(XmslContractInfo xmslContractInfo);

    int updateXmslContractInfoList(@Param("xmslContractInfoList") List<XmslContractInfo> xmslContractInfoList);

    int deleteXmslContractInfo(XmslContractInfo xmslContractInfo);

    int deleteXmslContractInfoByPks(@Param("xmslContractInfoPkList") List<Long> xmslContractInfoPkList);
}
