package com.hhwy.pm.xmslClimateCondition.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmslClimateCondition.domain.XmslClimateCondition;

/**
 * @author zgx
 * @date 2023-07-03 18:09:54
 * @remark 
 */
public interface XmslClimateConditionMapper {
                                                                                                                                                                                                                                                                                                                                
    XmslClimateCondition getXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    List<XmslClimateCondition> getXmslClimateConditionList(XmslClimateCondition xmslClimateCondition);

    int insertXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    int insertXmslClimateConditionList(@Param("xmslClimateConditionList") List<XmslClimateCondition> xmslClimateConditionList);

    int updateXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    
    int deleteXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    }
