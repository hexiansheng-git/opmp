package com.hhwy.pm.qqch.preparation.safe.danger.mapper;

import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerProcessControlPlan;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-07 14:24:24
 * @remark 8.3.3 危大工程过程管控策划
 */
public interface QqchDangerProcessControlPlanMapper {

    QqchDangerProcessControlPlan getQqchDangerProcessControlPlan(
        QqchDangerProcessControlPlan qqchDangerProcessControlPlan);

    List<QqchDangerProcessControlPlan> getQqchDangerProcessControlPlanList(
        QqchDangerProcessControlPlan qqchDangerProcessControlPlan);

    int insertQqchDangerProcessControlPlan(QqchDangerProcessControlPlan qqchDangerProcessControlPlan);

    int insertQqchDangerProcessControlPlanList(
        @Param("qqchDangerProcessControlPlanList") List<QqchDangerProcessControlPlan> qqchDangerProcessControlPlanList);

    int updateQqchDangerProcessControlPlan(QqchDangerProcessControlPlan qqchDangerProcessControlPlan);

    int updateQqchDangerProcessControlPlanList(
        @Param("list") List<QqchDangerProcessControlPlan> qqchDangerProcessControlPlanList);

    int deleteQqchDangerProcessControlPlan(QqchDangerProcessControlPlan qqchDangerProcessControlPlan);

    int deleteQqchDangerProcessControlPlanByPks(
        @Param("qqchDangerProcessControlPlanPkList") List<Long> qqchDangerProcessControlPlanPkList);
}
