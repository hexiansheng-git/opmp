package com.hhwy.pm.xmsl.implement.service;

import java.util.List;
import com.hhwy.pm.xmsl.implement.domain.XmslConstructionInterference;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:29
 * @remark 施工干扰
 */
public interface IXmslConstructionInterferenceService {

    List<XmslConstructionInterference> getXmslConstructionInterferenceList(
        XmslConstructionInterference xmslConstructionInterference);

    void save(List<XmslConstructionInterference> xmslConstructionInterferenceList);

    int deleteXmslConstructionInterferenceByPks(List<Long> xmslConstructionInterferencePkList);
}
