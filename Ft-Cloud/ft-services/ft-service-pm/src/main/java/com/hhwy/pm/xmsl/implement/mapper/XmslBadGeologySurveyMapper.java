package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslBadGeologySurvey;

/**
 * @author zhenglili
 * @date 2023-07-03 11:51:43
 * @remark 地质条件-不良地质调查
 */
public interface XmslBadGeologySurveyMapper {

    XmslBadGeologySurvey getXmslBadGeologySurvey(XmslBadGeologySurvey xmslBadGeologySurvey);

    List<XmslBadGeologySurvey> getXmslBadGeologySurveyList(
        XmslBadGeologySurvey xmslBadGeologySurvey);

    int insertXmslBadGeologySurvey(XmslBadGeologySurvey xmslBadGeologySurvey);

    int insertXmslBadGeologySurveyList(
        @Param("xmslBadGeologySurveyList") List<XmslBadGeologySurvey> xmslBadGeologySurveyList);

    int updateXmslBadGeologySurvey(XmslBadGeologySurvey xmslBadGeologySurvey);

    int updateXmslBadGeologySurveyList(@Param("list") List<XmslBadGeologySurvey> xmslBadGeologySurveyList);

    int deleteXmslBadGeologySurvey(XmslBadGeologySurvey xmslBadGeologySurvey);

    int deleteXmslBadGeologySurveyByPks(
        @Param("xmslBadGeologySurveyPkList") List<Long> xmslBadGeologySurveyPkList);
}
