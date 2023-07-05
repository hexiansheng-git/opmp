package com.hhwy.pm.xmsl.implement.service;

import com.hhwy.pm.xmsl.implement.domain.XmslBadGeologySurvey;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-03 11:51:43
 * @remark 地质条件-不良地质调查
 */
public interface IXmslBadGeologySurveyService {

    List<XmslBadGeologySurvey> getXmslBadGeologySurvey(
        XmslBadGeologySurvey xmslBadGeologySurvey);

    int deleteXmslBadGeologySurveyByPks(List<Long> xmslBadGeologySurveyPkList);
}
