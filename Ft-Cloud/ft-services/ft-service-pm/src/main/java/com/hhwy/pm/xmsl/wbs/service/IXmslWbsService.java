package com.hhwy.pm.xmsl.wbs.service;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;

import java.util.List;

/**
 * wbs
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
public interface IXmslWbsService {

    XmslWbs getXmslWbs(XmslWbs xmslWbs);

    List<XmslWbs> getXmslWbsList(XmslWbs xmslWbs);

    int insertXmslWbs(XmslWbs xmslWbs);

    int insertXmslWbsList(List<XmslWbs> xmslWbsList);

    int updateXmslWbs(XmslWbs xmslWbs);

    int updateXmslWbsList(List<XmslWbs> xmslWbsList);

    int deleteXmslWbs(XmslWbs xmslWbs);

    int deleteXmslWbsByPks(List<Long> xmslWbsPkList);
}
