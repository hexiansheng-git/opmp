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


    Map listData(XmslWbs xmslWbs);

    List<XmslWbs> getXmslWbsList(XmslWbs xmslWbs);

    /**
     * 判断是否有生效的wbs
     * @return 0:没有，1>：有
     */
    int hasEffectWbs();

    void save(XmslWbsDto dto);

    int insertXmslWbs(XmslWbs xmslWbs);

    int insertXmslWbsList(List<XmslWbs> xmslWbsList);

    int updateXmslWbs(XmslWbs xmslWbs);

    int updateXmslWbsList(List<XmslWbs> xmslWbsList);

    int deleteXmslWbs(XmslWbs xmslWbs);

    int deleteXmslWbsByPks(List<Long> xmslWbsPkList);
}
