package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSpecial;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:51
 * @remark
 */
public interface IXmslContractSpecialService {

    List<XmslContractSpecial>   getXmslContractSpecial(XmslContractSpecial xmslContractSpecial);

    List<XmslContractSpecial> getXmslContractSpecialList(XmslContractSpecial xmslContractSpecial);

    int insertXmslContractSpecial(XmslContractSpecial xmslContractSpecial);

    int insertXmslContractSpecialList(List<XmslContractSpecial> xmslContractSpecialList);

    int updateXmslContractSpecial(XmslContractSpecial xmslContractSpecial);

    int updateXmslContractSpecialList(List<XmslContractSpecial> xmslContractSpecialList);

    int deleteXmslContractSpecial(XmslContractSpecial xmslContractSpecial);

    int deleteXmslContractSpecialByPks(List<Long> xmslContractSpecialPkList);

    List<XmslContractSpecial> provideList(XmslContractSpecial xmslContractSpecialParam);
}
