package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslClimateCondition;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:07
 * @remark 气候条件
 */
public interface XmslClimateConditionMapper {

    XmslClimateCondition getXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    List<XmslClimateCondition> getXmslClimateConditionList(XmslClimateCondition xmslClimateCondition);

    int insertXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    int insertXmslClimateConditionList(
        @Param("xmslClimateConditionList") List<XmslClimateCondition> xmslClimateConditionList);

    int updateXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    int updateXmslClimateConditionList(
        @Param("xmslClimateConditionList") List<XmslClimateCondition> xmslClimateConditionList);

    int deleteXmslClimateCondition(XmslClimateCondition xmslClimateCondition);

    int deleteXmslClimateConditionByPks(@Param("xmslClimateConditionPkList") List<Long> xmslClimateConditionPkList);
}
