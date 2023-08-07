package com.hhwy.pm.qqch.preparation.safe.organ.mapper;

import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchSpecialPersonControlPlan;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-07 13:50:54
 * @remark 8.1.3 特种作业人员管控策划
 */
public interface QqchSpecialPersonControlPlanMapper {

    QqchSpecialPersonControlPlan getQqchSpecialPersonControlPlan(
        QqchSpecialPersonControlPlan qqchSpecialPersonControlPlan);

    List<QqchSpecialPersonControlPlan> getQqchSpecialPersonControlPlanList(
        QqchSpecialPersonControlPlan qqchSpecialPersonControlPlan);

    int insertQqchSpecialPersonControlPlan(QqchSpecialPersonControlPlan qqchSpecialPersonControlPlan);

    int insertQqchSpecialPersonControlPlanList(
        @Param("qqchSpecialPersonControlPlanList") List<QqchSpecialPersonControlPlan> qqchSpecialPersonControlPlanList);

    int updateQqchSpecialPersonControlPlan(QqchSpecialPersonControlPlan qqchSpecialPersonControlPlan);

    int updateQqchSpecialPersonControlPlanList(
        @Param("list") List<QqchSpecialPersonControlPlan> qqchSpecialPersonControlPlanList);

    int deleteQqchSpecialPersonControlPlan(QqchSpecialPersonControlPlan qqchSpecialPersonControlPlan);

    int deleteQqchSpecialPersonControlPlanByPks(
        @Param("qqchSpecialPersonControlPlanPkList") List<Long> qqchSpecialPersonControlPlanPkList);
}
