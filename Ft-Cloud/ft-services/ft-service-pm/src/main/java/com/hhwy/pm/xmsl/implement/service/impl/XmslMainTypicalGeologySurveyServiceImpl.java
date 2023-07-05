package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.pm.xmsl.implement.domain.XmslMainTypicalGeologySurvey;
import com.hhwy.pm.xmsl.implement.mapper.XmslMainTypicalGeologySurveyMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslMainTypicalGeologySurveyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:07
 * @remark 地质条件-主线典型地质勘察
 */
@Service
public class XmslMainTypicalGeologySurveyServiceImpl implements IXmslMainTypicalGeologySurveyService {

    @Autowired
    private XmslMainTypicalGeologySurveyMapper xmslMainTypicalGeologySurveyMapper;

    @Override
    public List<XmslMainTypicalGeologySurvey> getXmslMainTypicalGeologySurvey(
        XmslMainTypicalGeologySurvey xmslMainTypicalGeologySurvey) {
        return xmslMainTypicalGeologySurveyMapper.getXmslMainTypicalGeologySurveyList(xmslMainTypicalGeologySurvey);
    }

    @Transactional
    public int deleteXmslMainTypicalGeologySurveyByPks(List<Long> xmslMainTypicalGeologySurveyPkList) {
        return xmslMainTypicalGeologySurveyMapper
            .deleteXmslMainTypicalGeologySurveyByPks(xmslMainTypicalGeologySurveyPkList);
    }
}
