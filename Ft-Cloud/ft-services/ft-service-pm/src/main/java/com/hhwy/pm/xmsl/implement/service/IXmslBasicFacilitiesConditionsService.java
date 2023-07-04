package com.hhwy.pm.xmsl.implement.service;

import java.util.List;
import com.hhwy.pm.xmsl.implement.domain.XmslBasicFacilitiesConditions;

/**
 * @author zhenglili
 * @date 2023-07-03 11:59:44
 * @remark 水、电、交通、通讯条件
 */
public interface IXmslBasicFacilitiesConditionsService {

    List<XmslBasicFacilitiesConditions> getXmslBasicFacilitiesConditionsList(
        XmslBasicFacilitiesConditions xmslBasicFacilitiesConditions);

    void save(List<XmslBasicFacilitiesConditions> xmslBasicFacilitiesConditionsList);

    int deleteXmslBasicFacilitiesConditionsByPks(List<Long> xmslBasicFacilitiesConditionsPkList);
}
