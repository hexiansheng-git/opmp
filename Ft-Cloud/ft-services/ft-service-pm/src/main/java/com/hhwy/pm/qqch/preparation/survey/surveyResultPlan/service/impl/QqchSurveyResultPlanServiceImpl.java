package com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.domain.QqchSurveyResultPlan;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.mapper.QqchSurveyResultPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.service.IQqchSurveyResultPlanService;
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
 * @date 2023-07-21 16:45:04
 * @remark 2.3.1 勘测成果清单及计划
 */
@Service
public class QqchSurveyResultPlanServiceImpl implements IQqchSurveyResultPlanService {

    @Autowired
    private QqchSurveyResultPlanMapper qqchSurveyResultPlanMapper;
    @Autowired
    private CommonMapper commonMapper;


    public QqchSurveyResultPlan getQqchSurveyResultPlan(QqchSurveyResultPlan qqchSurveyResultPlan) {
        return qqchSurveyResultPlanMapper.getQqchSurveyResultPlan(qqchSurveyResultPlan);
    }

    /**
     *  列表查询
     *
     * @param qqchSurveyResultPlan
     * @return
     */
    public List<QqchSurveyResultPlan> getQqchSurveyResultPlanList(QqchSurveyResultPlan qqchSurveyResultPlan) {
        BigDecimal version=new BigDecimal(1);
        if (qqchSurveyResultPlan.getVersion() == null) {
            // 获取最大版本号
             version = commonMapper.selectMaxVersion("qqch_survey_result_plan");
        }
         qqchSurveyResultPlan.setVersion(version);
        return qqchSurveyResultPlanMapper.getQqchSurveyResultPlanList(qqchSurveyResultPlan);
    }

    @Transactional
    public int insertQqchSurveyResultPlan(QqchSurveyResultPlan qqchSurveyResultPlan) {
        qqchSurveyResultPlan.setId(IdWorker.createId());
        qqchSurveyResultPlan.setCreateUser(SecurityUtils.getUserName());
        qqchSurveyResultPlan.setCreateTime(DateUtils.getNowDate());
        return qqchSurveyResultPlanMapper.insertQqchSurveyResultPlan(qqchSurveyResultPlan);
    }

    /**
     *  批量新增修改
     *
     * @param qqchSurveyResultPlanList
     * @return
     */
    @Transactional
    public int insertQqchSurveyResultPlanList(List<QqchSurveyResultPlan> qqchSurveyResultPlanList) {
       List<QqchSurveyResultPlan> insertList = new ArrayList<>();
       List<QqchSurveyResultPlan> updateList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(qqchSurveyResultPlanList)){
            for (QqchSurveyResultPlan qqchSurveyResultPlan : qqchSurveyResultPlanList) {
                if(qqchSurveyResultPlan.getId()==null){
                    qqchSurveyResultPlan.setId(IdWorker.createId());
                    EntityUtils.setCreateUpdateInfo(qqchSurveyResultPlan);
                    insertList.add(qqchSurveyResultPlan);
                }else {
                    EntityUtils.setUpdateInfo(qqchSurveyResultPlan);
                    updateList.add(qqchSurveyResultPlan);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(insertList)){
            qqchSurveyResultPlanMapper.insertQqchSurveyResultPlanList(insertList);
        }
         if(CollectionUtils.isNotEmpty(updateList)){
             qqchSurveyResultPlanMapper.updateQqchSurveyResultPlanList(updateList);
         }
        return 1;
    }

    @Transactional
    public int updateQqchSurveyResultPlan(QqchSurveyResultPlan qqchSurveyResultPlan) {
        qqchSurveyResultPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyResultPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyResultPlanMapper.updateQqchSurveyResultPlan(qqchSurveyResultPlan);
    }

    @Transactional
    public int updateQqchSurveyResultPlanList(List<QqchSurveyResultPlan> qqchSurveyResultPlanList) {
        for (QqchSurveyResultPlan qqchSurveyResultPlan : qqchSurveyResultPlanList) {
            qqchSurveyResultPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchSurveyResultPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSurveyResultPlanMapper.updateQqchSurveyResultPlanList(qqchSurveyResultPlanList);
    }

    @Transactional
    public int deleteQqchSurveyResultPlan(QqchSurveyResultPlan qqchSurveyResultPlan) {
        qqchSurveyResultPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyResultPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyResultPlanMapper.deleteQqchSurveyResultPlan(qqchSurveyResultPlan);
    }

    @Transactional
    public int deleteQqchSurveyResultPlanByPks(List<Long> qqchSurveyResultPlanPkList) {
        return qqchSurveyResultPlanMapper.deleteQqchSurveyResultPlanByPks(qqchSurveyResultPlanPkList);
    }
}
