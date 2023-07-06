package com.hhwy.pm.xmsl.implement.domain;

import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:07
 * @remark 地质条件
 */
@Data
public class GeologicalCondition {

    /**
     * 主线典型地质勘察集合
     */
    private List<XmslMainTypicalGeologySurvey> mainTypicalGeologySurveyList;

    /**
     * 不良地质调查集合
     */
    private List<XmslBadGeologySurvey> badGeologySurveyList;
}
