package com.hhwy.pm.xmsl.wbs.service;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.dto.XmslWbsDto;

import java.util.List;
import java.util.Map;

/**
 * wbs
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
public interface IXmslWbsService {

    XmslWbs getXmslWbs(XmslWbs xmslWbs);

    List<XmslWbs> getByMainId(Long mainId);

    Map listData(XmslWbs xmslWbs);

    /**
     * 查询wbs，根据params.tname决定查询历史还是wbs
     * @param xmslWbs
     * @return
     */
    List<XmslWbs> getXmslWbsListByTname(XmslWbs xmslWbs);

    /**
     * 强制查询wbs
     * @param xmslWbs
     * @return
     */
    List<XmslWbs> getXmslWbsList(XmslWbs xmslWbs);

    Long countByWbs(XmslWbs wbs);

    /**
     * 判断是否有生效的wbs
     * @return { hasEffect 是否有生效,hasChange :是否有调整  }
     */
    Map hasEffectWbs();

    void save(XmslWbsDto dto);

    int insertXmslWbs(XmslWbs xmslWbs);

    int insertXmslWbsList(List<XmslWbs> xmslWbsList);

    int updateXmslWbs(XmslWbs xmslWbs);

    int updateXmslWbsList(List<XmslWbs> xmslWbsList);

    int deleteXmslWbs(XmslWbs xmslWbs);

    int deleteXmslWbsByPks(List<Long> xmslWbsPkList);
}
