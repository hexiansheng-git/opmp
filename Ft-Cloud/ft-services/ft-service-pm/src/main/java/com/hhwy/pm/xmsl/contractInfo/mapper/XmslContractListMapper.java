package com.hhwy.pm.xmsl.contractInfo.mapper;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:42
 * @remark
 */
public interface XmslContractListMapper {

    XmslContractList getXmslContractList(XmslContractList xmslContractList);

    List<XmslContractList> getXmslContractListList(XmslContractList xmslContractList);

    int insertXmslContractList(XmslContractList xmslContractList);

    int insertXmslContractListList(@Param("xmslContractListList") List<XmslContractList> xmslContractListList);

    int updateXmslContractList(XmslContractList xmslContractList);

    int updateXmslContractListList(@Param("xmslContractListList") List<XmslContractList> xmslContractListList);

    int deleteXmslContractList(XmslContractList xmslContractList);

    int deleteXmslContractListByPks(@Param("xmslContractListPkList") List<Long> xmslContractListPkList);
}
