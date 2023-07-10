package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSign;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:49
 * @remark
 */
public interface IXmslContractSignService {

    XmslContractSign getXmslContractSign(XmslContractSign xmslContractSign);

    List<XmslContractSign> getXmslContractSignList(XmslContractSign xmslContractSign);

    int insertXmslContractSign(XmslContractSign xmslContractSign);

    int insertXmslContractSignList(List<XmslContractSign> xmslContractSignList);

    int updateXmslContractSign(XmslContractSign xmslContractSign);

    int updateXmslContractSignList(List<XmslContractSign> xmslContractSignList);

    int deleteXmslContractSign(XmslContractSign xmslContractSign);

    int deleteXmslContractSignByPks(List<Long> xmslContractSignPkList);
}
