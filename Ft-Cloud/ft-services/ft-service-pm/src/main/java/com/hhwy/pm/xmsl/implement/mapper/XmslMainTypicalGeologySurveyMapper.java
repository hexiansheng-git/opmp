package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslMainTypicalGeologySurvey;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:07
 * @remark 地质条件-主线典型地质勘察
 */
public interface XmslMainTypicalGeologySurveyMapper {

    XmslMainTypicalGeologySurvey getXmslMainTypicalGeologySurvey(
        XmslMainTypicalGeologySurvey xmslMainTypicalGeologySurvey);

    List<XmslMainTypicalGeologySurvey> getXmslMainTypicalGeologySurveyList(
        XmslMainTypicalGeologySurvey xmslMainTypicalGeologySurvey);

    int insertXmslMainTypicalGeologySurvey(XmslMainTypicalGeologySurvey xmslMainTypicalGeologySurvey);

    int insertXmslMainTypicalGeologySurveyList(
        @Param("xmslMainTypicalGeologySurveyList") List<XmslMainTypicalGeologySurvey> xmslMainTypicalGeologySurveyList);

    int updateXmslMainTypicalGeologySurvey(XmslMainTypicalGeologySurvey xmslMainTypicalGeologySurvey);

    int updateXmslMainTypicalGeologySurveyList(
        @Param("xmslMainTypicalGeologySurveyList") List<XmslMainTypicalGeologySurvey> xmslMainTypicalGeologySurveyList);

    int deleteXmslMainTypicalGeologySurvey(XmslMainTypicalGeologySurvey xmslMainTypicalGeologySurvey);

    int deleteXmslMainTypicalGeologySurveyByPks(
        @Param("xmslMainTypicalGeologySurveyPkList") List<Long> xmslMainTypicalGeologySurveyPkList);
}
