package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.utils.tree.TreeVO;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:30
 * @remark
 */
public interface IXmslContractGeneralService {

    List<? extends TreeVO> getXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    List<XmslContractGeneral> getXmslContractGeneralList(XmslContractGeneral xmslContractGeneral);

    int insertXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    int insertXmslContractGeneralList(List<XmslContractGeneral> xmslContractGeneralList);

    int updateXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    int updateXmslContractGeneralList(List<XmslContractGeneral> xmslContractGeneralList);

    int deleteXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    int deleteXmslContractGeneralByPks(List<Long> xmslContractGeneralPkList);
}
