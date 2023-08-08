package com.hhwy.pm.qqch.preparation.safe.organ.mapper;

import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchSafeOrganDutyPlan;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-07 13:49:43
 * @remark 8.1.1 安全组织职责策划
 */
public interface QqchSafeOrganDutyPlanMapper {

    QqchSafeOrganDutyPlan getQqchSafeOrganDutyPlan(QqchSafeOrganDutyPlan qqchSafeOrganDutyPlan);

    List<QqchSafeOrganDutyPlan> getQqchSafeOrganDutyPlanList(QqchSafeOrganDutyPlan qqchSafeOrganDutyPlan);

    int insertQqchSafeOrganDutyPlan(QqchSafeOrganDutyPlan qqchSafeOrganDutyPlan);

    int insertQqchSafeOrganDutyPlanList(
        @Param("qqchSafeOrganDutyPlanList") List<QqchSafeOrganDutyPlan> qqchSafeOrganDutyPlanList);

    int updateQqchSafeOrganDutyPlan(QqchSafeOrganDutyPlan qqchSafeOrganDutyPlan);

    int updateQqchSafeOrganDutyPlanList(@Param("list") List<QqchSafeOrganDutyPlan> qqchSafeOrganDutyPlanList);

    int deleteQqchSafeOrganDutyPlan(QqchSafeOrganDutyPlan qqchSafeOrganDutyPlan);

    int deleteQqchSafeOrganDutyPlanByPks(@Param("qqchSafeOrganDutyPlanPkList") List<Long> qqchSafeOrganDutyPlanPkList);
}
