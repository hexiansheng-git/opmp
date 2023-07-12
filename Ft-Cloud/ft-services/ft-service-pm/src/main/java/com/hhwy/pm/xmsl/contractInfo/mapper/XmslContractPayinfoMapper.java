package com.hhwy.pm.xmsl.contractInfo.mapper;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ldd
 * @date 2023-07-10 14:17:46
 * @remark
 */
public interface XmslContractPayinfoMapper {

    XmslContractPayinfo getXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo);

    List<XmslContractPayinfo> getXmslContractPayinfoList(XmslContractPayinfo xmslContractPayinfo);

    int insertXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo);

    int insertXmslContractPayinfoList(@Param("xmslContractPayinfoList") List<XmslContractPayinfo> xmslContractPayinfoList);

    int updateXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo);

    int updateXmslContractPayinfoList(@Param("xmslContractPayinfoList") List<XmslContractPayinfo> xmslContractPayinfoList);

    int deleteXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo);

    int deleteXmslContractPayinfoByPks(@Param("xmslContractPayinfoPkList") List<Long> xmslContractPayinfoPkList);

    List<Map> selDictDate(String type);
}
