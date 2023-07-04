package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslBasicFacilitiesConditions;

/**
 * @author zhenglili
 * @date 2023-07-03 11:59:44
 * @remark 水、电、交通、通讯条件
 */
public interface XmslBasicFacilitiesConditionsMapper {

    XmslBasicFacilitiesConditions getXmslBasicFacilitiesConditions(
        XmslBasicFacilitiesConditions xmslBasicFacilitiesConditions);

    List<XmslBasicFacilitiesConditions> getXmslBasicFacilitiesConditionsList(
        XmslBasicFacilitiesConditions xmslBasicFacilitiesConditions);

    int insertXmslBasicFacilitiesConditions(
        XmslBasicFacilitiesConditions xmslBasicFacilitiesConditions);

    int insertXmslBasicFacilitiesConditionsList(
        @Param("xmslBasicFacilitiesConditionsList") List<XmslBasicFacilitiesConditions> xmslBasicFacilitiesConditionsList);

    int updateXmslBasicFacilitiesConditions(
        XmslBasicFacilitiesConditions xmslBasicFacilitiesConditions);

    int updateXmslBasicFacilitiesConditionsList(
        @Param("xmslBasicFacilitiesConditionsList") List<XmslBasicFacilitiesConditions> xmslBasicFacilitiesConditionsList);

    int deleteXmslBasicFacilitiesConditions(
        XmslBasicFacilitiesConditions xmslBasicFacilitiesConditions);

    int deleteXmslBasicFacilitiesConditionsByPks(
        @Param("xmslBasicFacilitiesConditionsPkList") List<Long> xmslBasicFacilitiesConditionsPkList);
}
