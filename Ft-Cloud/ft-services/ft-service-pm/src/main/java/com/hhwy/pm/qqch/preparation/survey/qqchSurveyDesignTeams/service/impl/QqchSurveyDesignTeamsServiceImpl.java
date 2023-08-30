package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.impl;

import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyDesignTeams;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyEquPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyPersonPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.vo.QqchSurveyDesignTeamsVo;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.mapper.QqchSurveyDesignTeamsMapper;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.mapper.QqchSurveyEquPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.mapper.QqchSurveyPersonPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.IQqchSurveyDesignTeamsService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-25 11:08:53
 * @remark 2.1.3 勘察设计队伍配置
 */
@Service
public class QqchSurveyDesignTeamsServiceImpl implements IQqchSurveyDesignTeamsService{

    @Autowired
    private QqchSurveyDesignTeamsMapper qqchSurveyDesignTeamsMapper;
    @Autowired
    private QqchSurveyPersonPlanMapper qqchSurveyPersonPlanMapper;
    @Autowired
    private QqchSurveyEquPlanMapper qqchSurveyEquPlanMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;

    /**
     *  列表接口
     * @param qqchSurveyDesignTeams
     * @return
     */
    public QqchSurveyDesignTeamsVo getQqchSurveyDesignTeamsList(QqchSurveyDesignTeams qqchSurveyDesignTeams) {
        BigDecimal version = VersionUtil.getVersion("qqch_survey_design_teams",qqchSurveyDesignTeams.getVersion());
        qqchSurveyDesignTeams.setVersion(version);
        List<QqchSurveyDesignTeams> qqchSurveyDesignTeamsList = qqchSurveyDesignTeamsMapper.getQqchSurveyDesignTeamsList(qqchSurveyDesignTeams);
        for (QqchSurveyDesignTeams surveyDesignTeams : qqchSurveyDesignTeamsList) {
            QqchSurveyPersonPlan qqchSurveyPersonPlan = new QqchSurveyPersonPlan();
            qqchSurveyPersonPlan.setMasterId(surveyDesignTeams.getId());
            List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList = qqchSurveyPersonPlanMapper.getQqchSurveyPersonPlanList(qqchSurveyPersonPlan);
            QqchSurveyEquPlan qqchSurveyEquPlan = new QqchSurveyEquPlan();
            qqchSurveyEquPlan.setMasterId(surveyDesignTeams.getId());
            List<QqchSurveyEquPlan> qqchSurveyEquPlanList = qqchSurveyEquPlanMapper.getQqchSurveyEquPlanList(qqchSurveyEquPlan);
            surveyDesignTeams.setQqchSurveyPersonPlanList(qqchSurveyPersonPlanList);
            surveyDesignTeams.setQqchSurveyEquPlanList(qqchSurveyEquPlanList);
        }
        QqchSurveyDesignTeamsVo vo = new QqchSurveyDesignTeamsVo();
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSurveyDesignTeamsList(qqchSurveyDesignTeamsList);
        return vo;
    }


    /**
     *  新增
     *
     * @param qqchSurveyDesignTeamsVo
     */
    @Override
    @Transactional
    public void save(QqchSurveyDesignTeamsVo qqchSurveyDesignTeamsVo) {
        //删除旧数据
        QqchSurveyDesignTeams qqchSurveyDesignTeams = new QqchSurveyDesignTeams();
        qqchSurveyDesignTeams.setVersion(qqchSurveyDesignTeamsVo.getVersion());
        //1
        List<QqchSurveyDesignTeams> designTeamsList = qqchSurveyDesignTeamsMapper.getQqchSurveyDesignTeamsList(qqchSurveyDesignTeams);
        for (QqchSurveyDesignTeams surveyDesignTeams : designTeamsList) {
            QqchSurveyPersonPlan qqchSurveyPersonPlan = new QqchSurveyPersonPlan();
            qqchSurveyPersonPlan.setMasterId(surveyDesignTeams.getId());
            qqchSurveyPersonPlanMapper.deleteQqchSurveyPersonPlan(qqchSurveyPersonPlan);

            QqchSurveyEquPlan qqchSurveyEquPlan=new QqchSurveyEquPlan();
            qqchSurveyEquPlan.setMasterId(surveyDesignTeams.getId());
            qqchSurveyEquPlanMapper.deleteQqchSurveyEquPlan(qqchSurveyEquPlan);
        }
        //2
        qqchSurveyDesignTeamsMapper.deleteQqchSurveyDesignTeams(qqchSurveyDesignTeams);

        List<QqchSurveyDesignTeams> paramList = qqchSurveyDesignTeamsVo.getQqchSurveyDesignTeamsList();
        if (CollectionUtils.isEmpty(paramList)) {
            return;
        }
        //插入新数据
        this.insertQqchSurveyDesignTeamsList(paramList, qqchSurveyDesignTeamsVo.getVersion());
    }

    @Override
    public void confirm(QqchSurveyDesignTeamsVo qqchSurveyDesignTeamsVo) {
        this.save(qqchSurveyDesignTeamsVo);
        String buttonMark = qqchSurveyDesignTeamsVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchSurveyDesignTeamsVo.getMenuId();
            String stageIdentity = qqchSurveyDesignTeamsVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    public void insertQqchSurveyDesignTeamsList(List<QqchSurveyDesignTeams> qqchSurveyDesignTeamsList, BigDecimal version) {
        List<QqchSurveyPersonPlan> personPlanList = new ArrayList<>();
        List<QqchSurveyEquPlan> equPlanList = new ArrayList<>();
        for (QqchSurveyDesignTeams qqchSurveyDesignTeams : qqchSurveyDesignTeamsList) {
            qqchSurveyDesignTeams.setId(IdWorker.createId());
            qqchSurveyDesignTeams.setVersion(version);
            if(version.compareTo(BigDecimal.valueOf(1)) == 0){
                qqchSurveyDesignTeams.setValid(Valid.YES);
            }
            //人员策划
            EntityUtils.setCreateUpdateInfo(qqchSurveyDesignTeams);
            List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList = qqchSurveyDesignTeams.getQqchSurveyPersonPlanList();
            if(CollectionUtils.isNotEmpty(qqchSurveyPersonPlanList)){
                for (QqchSurveyPersonPlan qqchSurveyPersonPlan : qqchSurveyPersonPlanList) {
                    qqchSurveyPersonPlan.setId(IdWorker.createId());
                    qqchSurveyPersonPlan.setMasterId(qqchSurveyDesignTeams.getId());
                    EntityUtils.setCreateUpdateInfo(qqchSurveyPersonPlan);
                    personPlanList.add(qqchSurveyPersonPlan);
                }
            }
            //设备策划
            List<QqchSurveyEquPlan> qqchSurveyEquPlanList = qqchSurveyDesignTeams.getQqchSurveyEquPlanList();
            if(CollectionUtils.isNotEmpty(qqchSurveyEquPlanList)){
                for (QqchSurveyEquPlan qqchSurveyEquPlan : qqchSurveyEquPlanList) {
                    qqchSurveyEquPlan.setId(IdWorker.createId());
                    qqchSurveyEquPlan.setMasterId(qqchSurveyDesignTeams.getId());
                    EntityUtils.setCreateUpdateInfo(qqchSurveyEquPlan);
                    equPlanList.add(qqchSurveyEquPlan);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(personPlanList)){
            qqchSurveyPersonPlanMapper.insertQqchSurveyPersonPlanList(personPlanList);
        }
        if(CollectionUtils.isNotEmpty(equPlanList)){
            qqchSurveyEquPlanMapper.insertQqchSurveyEquPlanList(equPlanList);
        }
        qqchSurveyDesignTeamsMapper.insertQqchSurveyDesignTeamsList(qqchSurveyDesignTeamsList);
    }


}
