package com.hhwy.pm.xmsl.contractInfo.mapper;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author ldd
 * @date 2023-07-10 14:17:42
 * @remark
 */
public interface XmslContractListMapper {

    List<XmslContractList> getXmslContractList(XmslContractList xmslContractList);

    List<XmslContractList> getXmslContractListList(XmslContractList xmslContractList);

    List<XmslContractList> getByIds(@Param("ids") Long[] ids);
    List<XmslContractList> getByCodes(@Param("codes") Set<String> codes);
    
    int insertXmslContractList(XmslContractList xmslContractList);

    int insertXmslContractListList(@Param("xmslContractListList") List<XmslContractListVo> xmslContractListList);

    int updateXmslContractList(XmslContractList xmslContractList);

    int updateXmslContractListList(@Param("list") List<XmslContractListVo> xmslContractListList);

    int deleteXmslContractList(XmslContractList xmslContractList);

    int deleteXmslContractListByPks(@Param("xmslContractListPkList") List<Long> xmslContractListPkList);

    void deleteByIds(@Param("list") Collection<Long> list);
}
