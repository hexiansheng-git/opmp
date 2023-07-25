package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.mapper;

import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:45:57
 * @remark
 */
@Repository
public interface QqchBimTechPlanMapper {

    QqchBimTechPlan getQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    List<QqchBimTechPlan> getQqchBimTechPlanList(QqchBimTechPlan qqchBimTechPlan);

    int insertQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    int insertQqchBimTechPlanList(@Param("qqchBimTechPlanList") List<QqchBimTechPlan> qqchBimTechPlanList);

    int updateQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    int updateQqchBimTechPlanList(@Param("list") List<QqchBimTechPlan> qqchBimTechPlanList);

    int deleteQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    int deleteQqchBimTechPlanByPks(@Param("qqchBimTechPlanPkList") List<Long> qqchBimTechPlanPkList);
}
