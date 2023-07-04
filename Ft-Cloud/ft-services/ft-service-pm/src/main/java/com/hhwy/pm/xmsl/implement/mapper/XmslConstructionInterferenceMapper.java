package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslConstructionInterference;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:29
 * @remark 施工干扰
 */
public interface XmslConstructionInterferenceMapper {

    XmslConstructionInterference getXmslConstructionInterference(
        XmslConstructionInterference xmslConstructionInterference);

    List<XmslConstructionInterference> getXmslConstructionInterferenceList(
        XmslConstructionInterference xmslConstructionInterference);

    int insertXmslConstructionInterference(
        XmslConstructionInterference xmslConstructionInterference);

    int insertXmslConstructionInterferenceList(
        @Param("xmslConstructionInterferenceList") List<XmslConstructionInterference> xmslConstructionInterferenceList);

    int updateXmslConstructionInterference(
        XmslConstructionInterference xmslConstructionInterference);

    int updateXmslConstructionInterferenceList(@Param("list") List<XmslConstructionInterference> xmslConstructionInterferenceList);

    int deleteXmslConstructionInterference(
        XmslConstructionInterference xmslConstructionInterference);

    int deleteXmslConstructionInterferenceByPks(
        @Param("xmslConstructionInterferencePkList") List<Long> xmslConstructionInterferencePkList);
}
