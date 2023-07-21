package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.GeologicalCondition;
import com.hhwy.pm.xmsl.implement.domain.XmslBadGeologySurvey;
import com.hhwy.pm.xmsl.implement.domain.XmslMainTypicalGeologySurvey;
import com.hhwy.pm.xmsl.implement.mapper.XmslBadGeologySurveyMapper;
import com.hhwy.pm.xmsl.implement.mapper.XmslMainTypicalGeologySurveyMapper;
import com.hhwy.pm.xmsl.implement.service.IGeologicalConditionService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:07
 * @remark 地质条件
 */
@Service
public class GeologicalConditionServiceImpl implements IGeologicalConditionService {

    @Autowired
    private XmslMainTypicalGeologySurveyMapper xmslMainTypicalGeologySurveyMapper;

    @Autowired
    private XmslBadGeologySurveyMapper xmslBadGeologySurveyMapper;

    public GeologicalCondition getList(GeologicalCondition geologicalCondition) {

        GeologicalCondition result = new GeologicalCondition();

        XmslMainTypicalGeologySurvey xmslMainTypicalGeologySurvey = new XmslMainTypicalGeologySurvey();
        List<XmslMainTypicalGeologySurvey> mainTypicalGeologyList = xmslMainTypicalGeologySurveyMapper
            .getXmslMainTypicalGeologySurveyList(xmslMainTypicalGeologySurvey);

        XmslBadGeologySurvey xmslBadGeologySurvey = new XmslBadGeologySurvey();
        List<XmslBadGeologySurvey> badGeologyList = xmslBadGeologySurveyMapper
            .getXmslBadGeologySurveyList(xmslBadGeologySurvey);

        result.setMainTypicalGeologySurveyList(mainTypicalGeologyList);
        result.setBadGeologySurveyList(badGeologyList);
        return result;
    }

    @Transactional
    public void save(GeologicalCondition geologicalCondition) {
        // 先清空旧数据,主线典型地质勘察
        XmslMainTypicalGeologySurvey mainDeleteParam = new XmslMainTypicalGeologySurvey();
        mainDeleteParam.setDelFlag("1");
        xmslMainTypicalGeologySurveyMapper.updateXmslMainTypicalGeologySurvey(mainDeleteParam);

        // 先清空旧数据,不良地质调查
        XmslBadGeologySurvey badDeleteParam = new XmslBadGeologySurvey();
        badDeleteParam.setDelFlag("1");
        xmslBadGeologySurveyMapper.updateXmslBadGeologySurvey(badDeleteParam);

        // 主线典型地质勘察
        if (!CollectionUtils.isEmpty(geologicalCondition.getMainTypicalGeologySurveyList())) {
            for (XmslMainTypicalGeologySurvey main : geologicalCondition.getMainTypicalGeologySurveyList()) {
                main.setId(IdWorker.createId());
                main.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                main.setCreateUserName(SecurityUtils.getUserName());
                main.setCreateTime(DateUtils.getNowDate());
            }
            xmslMainTypicalGeologySurveyMapper
                .insertXmslMainTypicalGeologySurveyList(geologicalCondition.getMainTypicalGeologySurveyList());
        }

        // 不良地质调查集合
        if (!CollectionUtils.isEmpty(geologicalCondition.getBadGeologySurveyList())) {
            for (XmslBadGeologySurvey bad : geologicalCondition.getBadGeologySurveyList()) {
                bad.setId(IdWorker.createId());
                bad.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                bad.setCreateUserName(SecurityUtils.getUserName());
                bad.setCreateTime(DateUtils.getNowDate());
            }
            xmslBadGeologySurveyMapper.insertXmslBadGeologySurveyList(geologicalCondition.getBadGeologySurveyList());
        }
    }

}
