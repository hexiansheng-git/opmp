package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.mapper;

import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyPersonPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-25 11:09:33
 * @remark  2.1.3 勘察设计队伍配置--人员策划
 */
public interface QqchSurveyPersonPlanMapper {
                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchSurveyPersonPlan getQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    List<QqchSurveyPersonPlan> getQqchSurveyPersonPlanList(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    int insertQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    int insertQqchSurveyPersonPlanList(@Param("qqchSurveyPersonPlanList") List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList);

    int updateQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    int updateQqchSurveyPersonPlanList(@Param("qqchSurveyPersonPlanList") List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList);
    
    int deleteQqchSurveyPersonPlan(QqchSurveyPersonPlan qqchSurveyPersonPlan);

    int deleteQqchSurveyPersonPlanByPks(@Param("qqchSurveyPersonPlanPkList") List<Long> qqchSurveyPersonPlanPkList);
    }
