package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.pm.xmsl.implement.domain.XmslBadGeologySurvey;
import com.hhwy.pm.xmsl.implement.mapper.XmslBadGeologySurveyMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslBadGeologySurveyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 11:51:43
 * @remark 地质条件-不良地质调查
 */
@Service
public class XmslBadGeologySurveyServiceImpl implements IXmslBadGeologySurveyService {

    @Autowired
    private XmslBadGeologySurveyMapper xmslBadGeologySurveyMapper;

    @Override
    public List<XmslBadGeologySurvey> getXmslBadGeologySurvey(XmslBadGeologySurvey xmslBadGeologySurvey) {
        return xmslBadGeologySurveyMapper.getXmslBadGeologySurveyList(xmslBadGeologySurvey);
    }

    @Transactional
    public int deleteXmslBadGeologySurveyByPks(List<Long> xmslBadGeologySurveyPkList) {
        return xmslBadGeologySurveyMapper.deleteXmslBadGeologySurveyByPks(xmslBadGeologySurveyPkList);
    }
}
