package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:42
 * @remark
 */
public interface IXmslContractListService {

    XmslContractList getXmslContractList(XmslContractList xmslContractList);

    List<XmslContractList> getXmslContractListList(XmslContractList xmslContractList);

    int insertXmslContractList(XmslContractList xmslContractList);

    int insertXmslContractListList(List<XmslContractList> xmslContractListList);

    int updateXmslContractList(XmslContractList xmslContractList);

    int updateXmslContractListList(List<XmslContractList> xmslContractListList);

    int deleteXmslContractList(XmslContractList xmslContractList);

    int deleteXmslContractListByPks(List<Long> xmslContractListPkList);
}
