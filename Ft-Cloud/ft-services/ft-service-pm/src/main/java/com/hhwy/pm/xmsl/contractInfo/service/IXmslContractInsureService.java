package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInsure;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:36
 * @remark
 */
public interface IXmslContractInsureService {

    XmslContractInsure getXmslContractInsure(XmslContractInsure xmslContractInsure);

    List<XmslContractInsure> getXmslContractInsureList(XmslContractInsure xmslContractInsure);

    int insertXmslContractInsure(XmslContractInsure xmslContractInsure);

    int insertXmslContractInsureList(List<XmslContractInsure> xmslContractInsureList);

    int updateXmslContractInsure(XmslContractInsure xmslContractInsure);

    int updateXmslContractInsureList(List<XmslContractInsure> xmslContractInsureList);

    int deleteXmslContractInsure(XmslContractInsure xmslContractInsure);

    int deleteXmslContractInsureByPks(List<Long> xmslContractInsurePkList);
}
