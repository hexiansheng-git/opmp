package com.hhwy.pm.xmsl.implement.service;

import java.util.List;
import com.hhwy.pm.xmsl.implement.domain.XmslClimateCondition;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:07
 * @remark 气候条件
 */
public interface IXmslClimateConditionService {

    List<XmslClimateCondition> getXmslClimateConditionList(XmslClimateCondition xmslClimateCondition);

    void save(List<XmslClimateCondition> xmslClimateConditionList);

    int deleteXmslClimateConditionByPks(List<Long> xmslClimateConditionPkList);}
