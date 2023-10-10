package com.hhwy.pm.xmsl.contractInfo.mapper;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author ldd
 * @date 2023-07-10 14:17:30
 * @remark
 */
public interface XmslContractGeneralMapper {

    List<XmslContractGeneral> getXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    List<XmslContractGeneral> getXmslContractGeneralList(XmslContractGeneral xmslContractGeneral);

    int insertXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    int insertXmslContractGeneralList(@Param("xmslContractGeneralList") List<XmslContractGeneral> xmslContractGeneralList);

    int updateXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    int updateXmslContractGeneralList(@Param("list") List<XmslContractGeneral> xmslContractGeneralList);

    int deleteXmslContractGeneral(XmslContractGeneral xmslContractGeneral);

    int deleteXmslContractGeneralByPks(@Param("xmslContractGeneralPkList") List<Long> xmslContractGeneralPkList, @Param("masterId") Long masterId);
}
