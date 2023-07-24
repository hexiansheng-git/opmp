package com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.mapper;

import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.domain.QqchSurveyResultPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:45:04
 * @remark 2.3.1 勘测成果清单及计划
 */
public interface QqchSurveyResultPlanMapper {

    QqchSurveyResultPlan getQqchSurveyResultPlan(QqchSurveyResultPlan qqchSurveyResultPlan);

    List<QqchSurveyResultPlan> getQqchSurveyResultPlanList(QqchSurveyResultPlan qqchSurveyResultPlan);

    int insertQqchSurveyResultPlan(QqchSurveyResultPlan qqchSurveyResultPlan);

    int insertQqchSurveyResultPlanList(@Param("qqchSurveyResultPlanList") List<QqchSurveyResultPlan> qqchSurveyResultPlanList);

    int updateQqchSurveyResultPlan(QqchSurveyResultPlan qqchSurveyResultPlan);

    int updateQqchSurveyResultPlanList(@Param("list") List<QqchSurveyResultPlan> qqchSurveyResultPlanList);

    int deleteQqchSurveyResultPlan(QqchSurveyResultPlan qqchSurveyResultPlan);

    int deleteQqchSurveyResultPlanByPks(@Param("qqchSurveyResultPlanPkList") List<Long> qqchSurveyResultPlanPkList);
}
