package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 13:57:39
 * @remark
 */
public interface IXmslContractInfoService {

    /**
     * 获取最新有效版本的合同信息
     * @return
     */
    XmslContractInfo getValidMaxVersionContractInfo();

    XmslContractInfo getXmslContractInfo(XmslContractInfo xmslContractInfo);

    List<XmslContractInfo> getXmslContractInfoList(XmslContractInfo xmslContractInfo);

    /**
     *  主合同信息新增
     *
     * @param xmslContractInfo
     * @return
     */
    Long insertXmslContractInfo(XmslContractInfo xmslContractInfo);

    int insertXmslContractInfoList(List<XmslContractInfo> xmslContractInfoList);

    int updateXmslContractInfo(XmslContractInfo xmslContractInfo);

    int updateXmslContractInfoList(List<XmslContractInfo> xmslContractInfoList);

    int deleteXmslContractInfo(XmslContractInfo xmslContractInfo);

    int deleteXmslContractInfoByPks(List<Long> xmslContractInfoPkList);

    int updateXmslContractInfo1(XmslContractInfo xmslContractInfo);
}
