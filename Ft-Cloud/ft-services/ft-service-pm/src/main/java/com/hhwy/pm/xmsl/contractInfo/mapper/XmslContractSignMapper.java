package com.hhwy.pm.xmsl.contractInfo.mapper;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSign;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author ldd
 * @date 2023-07-10 14:17:49
 * @remark
 */
public interface XmslContractSignMapper {

    XmslContractSign getXmslContractSign(XmslContractSign xmslContractSign);

    List<XmslContractSign> getXmslContractSignList(XmslContractSign xmslContractSign);

    int insertXmslContractSign(XmslContractSign xmslContractSign);

    int insertXmslContractSignList(@Param("xmslContractSignList") List<XmslContractSign> xmslContractSignList);

    int updateXmslContractSign(XmslContractSign xmslContractSign);

    int updateXmslContractSignList(@Param("xmslContractSignList") List<XmslContractSign> xmslContractSignList);

    int deleteXmslContractSign(XmslContractSign xmslContractSign);

    int deleteXmslContractSignByPks(@Param("xmslContractSignPkList") List<Long> xmslContractSignPkList);
}
