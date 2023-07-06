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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // 主线典型地质勘察
        if (geologicalCondition.getMainTypicalGeologySurveyList() != null
            && geologicalCondition.getMainTypicalGeologySurveyList().size() != 0) {
            List<XmslMainTypicalGeologySurvey> insertMainList = new ArrayList<>();
            List<XmslMainTypicalGeologySurvey> updateMainList = new ArrayList<>();
            for (XmslMainTypicalGeologySurvey main : geologicalCondition.getMainTypicalGeologySurveyList()) {
                if (main.getId() == null) {
                    main.setId(IdWorker.createId());
                    main.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    main.setCreateUserName(SecurityUtils.getUserName());
                    main.setCreateTime(DateUtils.getNowDate());
                    insertMainList.add(main);
                } else {
                    main.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    main.setUpdateTime(DateUtils.getNowDate());
                    updateMainList.add(main);
                }
            }
            if (insertMainList.size() > 0) {
                xmslMainTypicalGeologySurveyMapper.insertXmslMainTypicalGeologySurveyList(insertMainList);
            }
            if (updateMainList.size() > 0) {
                xmslMainTypicalGeologySurveyMapper.updateXmslMainTypicalGeologySurveyList(updateMainList);
            }

        }

        // 不良地质调查集合
        if (geologicalCondition.getBadGeologySurveyList() != null
            && geologicalCondition.getBadGeologySurveyList().size() != 0) {
            List<XmslBadGeologySurvey> insertBadList = new ArrayList<>();
            List<XmslBadGeologySurvey> updateBadList = new ArrayList<>();
            for (XmslBadGeologySurvey bad : geologicalCondition.getBadGeologySurveyList()) {
                if (bad.getId() == null) {
                    bad.setId(IdWorker.createId());
                    bad.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    bad.setCreateUserName(SecurityUtils.getUserName());
                    bad.setCreateTime(DateUtils.getNowDate());
                    insertBadList.add(bad);
                } else {
                    bad.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    bad.setUpdateTime(DateUtils.getNowDate());
                    updateBadList.add(bad);
                }
            }

            if (insertBadList.size() > 0) {
                xmslBadGeologySurveyMapper.insertXmslBadGeologySurveyList(insertBadList);
            }
            if (updateBadList.size() > 0) {
                xmslBadGeologySurveyMapper.updateXmslBadGeologySurveyList(updateBadList);
            }
        }
    }

}
