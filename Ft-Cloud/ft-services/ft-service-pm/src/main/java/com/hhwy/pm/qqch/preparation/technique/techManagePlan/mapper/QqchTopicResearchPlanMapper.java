package com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchTopicResearchPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:25
 * @remark 课题研究计划
 */
@Repository
public interface QqchTopicResearchPlanMapper {

    QqchTopicResearchPlan getQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    List<QqchTopicResearchPlan> getQqchTopicResearchPlanList(QqchTopicResearchPlan qqchTopicResearchPlan);

    int insertQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    int insertQqchTopicResearchPlanList(@Param("qqchTopicResearchPlanList") List<QqchTopicResearchPlan> qqchTopicResearchPlanList);

    int updateQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    int updateQqchTopicResearchPlanList(@Param("list") List<QqchTopicResearchPlan> qqchTopicResearchPlanList);

    int deleteQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    int deleteQqchTopicResearchPlanByPks(@Param("qqchTopicResearchPlanPkList") List<Long> qqchTopicResearchPlanPkList);
}
