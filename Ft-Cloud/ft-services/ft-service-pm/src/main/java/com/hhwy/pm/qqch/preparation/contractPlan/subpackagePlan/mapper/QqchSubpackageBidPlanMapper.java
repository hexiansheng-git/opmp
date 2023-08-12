package com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.mapper;

import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.QqchSubpackageBidPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:44
 * @remark
 */
@Repository
public interface QqchSubpackageBidPlanMapper {

    QqchSubpackageBidPlan getQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    List<QqchSubpackageBidPlan> getQqchSubpackageBidPlanList(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    int insertQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    int insertQqchSubpackageBidPlanList(@Param("qqchSubpackageBidPlanList") List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList);

    int updateQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    int updateQqchSubpackageBidPlanList(@Param("list") List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList);

    int deleteQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan);

    int deleteQqchSubpackageBidPlanByPks(@Param("qqchSubpackageBidPlanPkList") List<Long> qqchSubpackageBidPlanPkList);
}
