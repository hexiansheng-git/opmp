package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:46
 * @remark
 */
public interface IXmslContractPayinfoService {

    XmslContractPayinfo getXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo);

    List<XmslContractPayinfo> getXmslContractPayinfoList(XmslContractPayinfo xmslContractPayinfo);

    int insertXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo);

    int insertXmslContractPayinfoList(List<XmslContractPayinfo> xmslContractPayinfoList);

    int updateXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo);

    int updateXmslContractPayinfoList(List<XmslContractPayinfo> xmslContractPayinfoList);

    int deleteXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo);

    int deleteXmslContractPayinfoByPks(List<Long> xmslContractPayinfoPkList);
}
