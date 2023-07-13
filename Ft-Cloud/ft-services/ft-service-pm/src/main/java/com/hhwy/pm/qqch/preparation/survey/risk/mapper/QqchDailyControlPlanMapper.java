package com.hhwy.pm.qqch.preparation.survey.risk.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchDailyControlPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-13 11:39:57
 * @remark 日常管控策划
 */
@Repository
public interface QqchDailyControlPlanMapper {

    QqchDailyControlPlan getQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan);

    List<QqchDailyControlPlan> getQqchDailyControlPlanList(QqchDailyControlPlan qqchDailyControlPlan);

    int insertQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan);

    int insertQqchDailyControlPlanList(@Param("qqchDailyControlPlanList") List<QqchDailyControlPlan> qqchDailyControlPlanList);

    int updateQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan);

    int updateQqchDailyControlPlanList(@Param("list") List<QqchDailyControlPlan> qqchDailyControlPlanList);

    int deleteQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan);

    int deleteQqchDailyControlPlanByPks(@Param("qqchDailyControlPlanPkList") List<Long> qqchDailyControlPlanPkList);
}
