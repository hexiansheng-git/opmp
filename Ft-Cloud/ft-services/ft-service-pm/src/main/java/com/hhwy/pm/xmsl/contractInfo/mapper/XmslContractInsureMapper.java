package com.hhwy.pm.xmsl.contractInfo.mapper;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInsure;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author ldd
 * @date 2023-07-10 14:17:36
 * @remark
 */
public interface XmslContractInsureMapper {

    XmslContractInsure getXmslContractInsure(XmslContractInsure xmslContractInsure);

    List<XmslContractInsure> getXmslContractInsureList(XmslContractInsure xmslContractInsure);

    int insertXmslContractInsure(XmslContractInsure xmslContractInsure);

    int insertXmslContractInsureList(@Param("xmslContractInsureList") List<XmslContractInsure> xmslContractInsureList);

    int updateXmslContractInsure(XmslContractInsure xmslContractInsure);

    int updateXmslContractInsureList(@Param("xmslContractInsureList") List<XmslContractInsure> xmslContractInsureList);

    int deleteXmslContractInsure(XmslContractInsure xmslContractInsure);

    int deleteXmslContractInsureByPks(@Param("xmslContractInsurePkList") List<Long> xmslContractInsurePkList);
}
