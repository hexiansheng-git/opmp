package com.hhwy.pm.xmslClimateCondition.service;

import java.util.List;
import com.hhwy.pm.xmslClimateCondition.domain.XmslClimateCondition;

/**
 * @author zgx
 * @date 2023-07-03 18:09:54
 * @remark 
 */
public interface IXmslClimateConditionService {
                                                                                                                                                                                                                                                                                                                                
    XmslClimateCondition getXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    List<XmslClimateCondition> getXmslClimateConditionList(XmslClimateCondition xmslClimateCondition);

    int insertXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    int insertXmslClimateConditionList(List<XmslClimateCondition> xmslClimateConditionList);

    int updateXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    
    int deleteXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    }
