package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractGeneralVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:30
 * @remark
 */
public interface IXmslContractGeneralService {

    List<XmslContractGeneral> getXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    List<XmslContractGeneral> getXmslContractGeneralList(XmslContractGeneral xmslContractGeneral);

    int insertXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    void insertXmslContractGeneralList(XmslContractGeneralVo param);

    int updateXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    int updateXmslContractGeneralList(List<XmslContractGeneral> xmslContractGeneralList);

    int deleteXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    int deleteXmslContractGeneralByPks(List<Long> xmslContractGeneralPkList, Long masterId);

    List<XmslContractGeneral> provideList(XmslContractGeneral xmslContractGeneralParam);

    List<XmslContractGeneral> dataHandler(XmslContractGeneralVo xmslContractGeneralVo);

}
