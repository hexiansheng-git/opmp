package com.hhwy.pm.qqch.preparation.technique.techTrainPlan.mapper;

import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.QqchTechTrainPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:57:39
 * @remark 技术培训策划
 */
@Repository
public interface QqchTechTrainPlanMapper {

    QqchTechTrainPlan getQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    List<QqchTechTrainPlan> getQqchTechTrainPlanList(QqchTechTrainPlan qqchTechTrainPlan);

    int insertQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    int insertQqchTechTrainPlanList(@Param("qqchTechTrainPlanList") List<QqchTechTrainPlan> qqchTechTrainPlanList);

    int updateQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    int updateQqchTechTrainPlanList(@Param("list") List<QqchTechTrainPlan> qqchTechTrainPlanList);

    int deleteQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    int deleteQqchTechTrainPlanByPks(@Param("qqchTechTrainPlanPkList") List<Long> qqchTechTrainPlanPkList);
}
