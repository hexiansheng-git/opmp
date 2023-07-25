package com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlanVo;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.mapper.QqchSurveyWorkPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service.IQqchSurveyWorkPlanService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-20 11:49:55
 * @remark  2.2 勘察设计工作计划
 */
@Service
public class QqchSurveyWorkPlanServiceImpl implements IQqchSurveyWorkPlanService{

    @Autowired
    private QqchSurveyWorkPlanMapper qqchSurveyWorkPlanMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;



    public List<QqchSurveyWorkPlan> getQqchSurveyWorkPlanList(QqchSurveyWorkPlan qqchSurveyWorkPlan) {
        BigDecimal version = VersionUtil.getVersion("qqch_survey_work_plan",qqchSurveyWorkPlan.getVersion());
        qqchSurveyWorkPlan.setVersion(version);
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList = qqchSurveyWorkPlanMapper.getQqchSurveyWorkPlanList(qqchSurveyWorkPlan);
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlans = TreeUtil.build(qqchSurveyWorkPlanList, 0l);
        return  qqchSurveyWorkPlans;
    }

    @Override
    public void save(QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        //删除旧数据
        QqchSurveyWorkPlan qqchSurveyWorkPlan = new QqchSurveyWorkPlan();
        qqchSurveyWorkPlan.setVersion(qqchSurveyWorkPlan.getVersion());
        qqchSurveyWorkPlanMapper.deleteQqchSurveyWorkPlan(qqchSurveyWorkPlan);
        //插入新数据
        this.insertQqchSurveyWorkPlanList(qqchSurveyWorkPlanVo.getQqchSurveyWorkPlanList(), qqchSurveyWorkPlanVo.getVersion());
    }

    @Override
    public void confirm(QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        this.save(qqchSurveyWorkPlanVo);
        String buttonMark = qqchSurveyWorkPlanVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchSurveyWorkPlanVo.getMenuId();
            String stageIdentity = qqchSurveyWorkPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    private void insertQqchSurveyWorkPlanList(List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList, BigDecimal version) {
        List<QqchSurveyWorkPlan> insertList = TreeUtil.treeToList(qqchSurveyWorkPlanList);
        for (QqchSurveyWorkPlan    qqchSurveyWorkPlan : insertList) {
            qqchSurveyWorkPlan.setVersion(version);
            if(version.compareTo(BigDecimal.valueOf(1)) == 0){
                qqchSurveyWorkPlan.setValid(Valid.YES);
            }
            qqchSurveyWorkPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyWorkPlan.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            qqchSurveyWorkPlan.setCreateTime(DateUtils.getNowDate());
            if(qqchSurveyWorkPlan.getPid()==null){
                qqchSurveyWorkPlan.setPid(0l);
            }
        }
        qqchSurveyWorkPlanMapper.insertQqchSurveyWorkPlanList(insertList);
    }

}
