package com.hhwy.pm.xmsl.wbs.service;

import java.util.List;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark 
 */
public interface IXmslWbsMainService {
                                                                                                                                                    
    XmslWbsMain getXmslWbsMain(XmslWbsMain xmslWbsMain);

    XmslWbsMain getById(Long mainId);

    List<XmslWbsMain> getXmslWbsMainList(XmslWbsMain xmslWbsMain);

    XmslWbsMain getEffect();

    Long getXmslWbsMainCount(XmslWbsMain xmslWbsMain);

    XmslWbsMain getLast();

    int insertXmslWbsMain(XmslWbsMain xmslWbsMain);

    int insertXmslWbsMainList(List<XmslWbsMain> xmslWbsMainList);

    int updateXmslWbsMain(XmslWbsMain xmslWbsMain);

    int updateXmslWbsMainList(List<XmslWbsMain> xmslWbsMainList);
    
    int deleteXmslWbsMain(XmslWbsMain xmslWbsMain);

    int deleteXmslWbsMainByPks(List<Long> xmslWbsMainPkList);
}
