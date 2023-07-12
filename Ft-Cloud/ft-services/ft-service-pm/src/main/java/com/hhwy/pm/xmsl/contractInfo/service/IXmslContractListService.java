package com.hhwy.pm.xmsl.contractInfo.service;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListVo;
import com.hhwy.utils.tree.TreeVO;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:42
 * @remark
 */
public interface IXmslContractListService {

    List<? extends TreeVO> getXmslContractList(XmslContractList xmslContractList);

    List<XmslContractList> getXmslContractListList(XmslContractList xmslContractList);

    int insertXmslContractList(XmslContractList xmslContractList);

    int insertXmslContractListList(List<XmslContractListVo> xmslContractListList);

    int updateXmslContractList(XmslContractList xmslContractList);


    int deleteXmslContractList(XmslContractList xmslContractList);

    int deleteXmslContractListByPks(List<Long> xmslContractListPkList);
}
