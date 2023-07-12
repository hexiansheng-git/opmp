package com.hhwy.pm.xmsl.contractInfo.mapper;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSpecial;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author ldd
 * @date 2023-07-10 14:17:51
 * @remark
 */
public interface XmslContractSpecialMapper {

    List<XmslContractSpecial> getXmslContractSpecial(XmslContractSpecial xmslContractSpecial);

    List<XmslContractSpecial> getXmslContractSpecialList(XmslContractSpecial xmslContractSpecial);

    int insertXmslContractSpecial(XmslContractSpecial xmslContractSpecial);

    int insertXmslContractSpecialList(@Param("xmslContractSpecialList") List<XmslContractSpecial> xmslContractSpecialList);

    int updateXmslContractSpecial(XmslContractSpecial xmslContractSpecial);

    int updateXmslContractSpecialList(@Param("xmslContractSpecialList") List<XmslContractSpecial> xmslContractSpecialList);

    int deleteXmslContractSpecial(XmslContractSpecial xmslContractSpecial);

    int deleteXmslContractSpecialByPks(@Param("xmslContractSpecialPkList") List<Long> xmslContractSpecialPkList);
}
