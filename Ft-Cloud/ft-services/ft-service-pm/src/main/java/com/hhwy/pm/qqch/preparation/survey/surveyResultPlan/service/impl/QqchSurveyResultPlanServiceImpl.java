package com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.domain.QqchSurveyResultPlan;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.domain.QqchSurveyResultPlanVo;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.mapper.QqchSurveyResultPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.service.IQqchSurveyResultPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:45:04
 * @remark 2.3.1 勘测成果清单及计划
 */
@Service
public class QqchSurveyResultPlanServiceImpl implements IQqchSurveyResultPlanService {

    @Autowired
    private QqchSurveyResultPlanMapper qqchSurveyResultPlanMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    /**
     *  列表查询
     *
     * @param qqchSurveyResultPlan
     * @return
     */
    public QqchSurveyResultPlanVo getQqchSurveyResultPlanList(QqchSurveyResultPlan qqchSurveyResultPlan) {
        BigDecimal version=new BigDecimal(1);
        if (qqchSurveyResultPlan.getVersion() == null) {
            // 获取最大版本号
             version = commonMapper.selectMaxVersion("qqch_survey_result_plan");
        }
         qqchSurveyResultPlan.setVersion(version);
        List<QqchSurveyResultPlan> qqchSurveyResultPlanList = qqchSurveyResultPlanMapper.getQqchSurveyResultPlanList(qqchSurveyResultPlan);
        QqchSurveyResultPlanVo vo = new QqchSurveyResultPlanVo();
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSurveyResultPlanList(qqchSurveyResultPlanList);
        return vo;
    }

    /**
     *  批增
     *
     * @param qqchSurveyResultPlanVo
     */
    @Override
    public void save(QqchSurveyResultPlanVo qqchSurveyResultPlanVo) {
        //删除旧数据
        QqchSurveyResultPlan qqchSurveyResultPlan = new QqchSurveyResultPlan();
        qqchSurveyResultPlan.setVersion(qqchSurveyResultPlan.getVersion());
        qqchSurveyResultPlanMapper.deleteQqchSurveyResultPlan(qqchSurveyResultPlan);
        //插入新数据
        this.insertQqchSurveyResultPlanList(qqchSurveyResultPlanVo.getQqchSurveyResultPlanList(), qqchSurveyResultPlanVo.getVersion());
    }

    /**
     *  确认
     *
     * @param qqchSurveyResultPlanVo
     */
    @Override
    public void confirm(QqchSurveyResultPlanVo qqchSurveyResultPlanVo) {
        this.save(qqchSurveyResultPlanVo);
        String buttonMark = qqchSurveyResultPlanVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchSurveyResultPlanVo.getMenuId();
            String stageIdentity = qqchSurveyResultPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    private void insertQqchSurveyResultPlanList(List<QqchSurveyResultPlan> qqchSurveyResultPlanList, BigDecimal version) {
        for (QqchSurveyResultPlan qqchSurveyResultPlan : qqchSurveyResultPlanList) {
            qqchSurveyResultPlan.setId(IdWorker.createId());
            qqchSurveyResultPlan.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchSurveyResultPlan.setValid(Valid.YES);
            }
            qqchSurveyResultPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyResultPlan.setCreateUserName(SecurityUtils.getUserName());
            qqchSurveyResultPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchSurveyResultPlanMapper.insertQqchSurveyResultPlanList(qqchSurveyResultPlanList);
    }


}
