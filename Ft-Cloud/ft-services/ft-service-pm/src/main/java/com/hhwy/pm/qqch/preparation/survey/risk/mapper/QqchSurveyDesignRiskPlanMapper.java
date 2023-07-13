package com.hhwy.pm.qqch.preparation.survey.risk.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchSurveyDesignRiskPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-13 11:39:34
 * @remark 勘察设计风险策划
 */
@Repository
public interface QqchSurveyDesignRiskPlanMapper {

    QqchSurveyDesignRiskPlan getQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    List<QqchSurveyDesignRiskPlan> getQqchSurveyDesignRiskPlanList(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    int insertQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    int insertQqchSurveyDesignRiskPlanList(@Param("qqchSurveyDesignRiskPlanList") List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList);

    int updateQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    int updateQqchSurveyDesignRiskPlanList(@Param("list") List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList);

    int deleteQqchSurveyDesignRiskPlan(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan);

    int deleteQqchSurveyDesignRiskPlanByPks(@Param("qqchSurveyDesignRiskPlanPkList") List<Long> qqchSurveyDesignRiskPlanPkList);
}
