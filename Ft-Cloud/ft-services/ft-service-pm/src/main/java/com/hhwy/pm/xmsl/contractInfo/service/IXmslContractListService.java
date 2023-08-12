package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListDto;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListVo;

import java.util.List;
import java.util.Set;

/**
 * @author ldd
 * @date 2023-07-10 14:17:42
 * @remark
 */
public interface IXmslContractListService {

    List<XmslContractList> getXmslContractList(XmslContractList xmslContractList);

    List<XmslContractList> getXmslContractList2(XmslContractList xmslContractListParam);

    List<XmslContractList> getXmslContractListList(XmslContractList xmslContractList);

    List<XmslContractList> getByIds(Long[] ids);

    public List<XmslContractList> getByCodes(Set<String> codeSet);

    int insertXmslContractList(XmslContractList xmslContractList);

    int insertXmslContractListList(List<XmslContractListVo> xmslContractListList);

    void updateXmslContractList(XmslContractListDto dto);


    int deleteXmslContractList(XmslContractList xmslContractList);

    int deleteXmslContractListByPks(List<Long> xmslContractListPkList);


    List<XmslContractList> getEffectList(XmslContractList xmslContractListParam);

    void handlerAncestors();
}
