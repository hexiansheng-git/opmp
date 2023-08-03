package com.hhwy.pm.qqch.preparation.quality.duty.mapper;

import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchPersonControlPlan;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-03 14:29:43
 * @remark 9.1.2 人员管控策划
 */
public interface QqchPersonControlPlanMapper {

    QqchPersonControlPlan getQqchPersonControlPlan(QqchPersonControlPlan qqchPersonControlPlan);

    List<QqchPersonControlPlan> getQqchPersonControlPlanList(QqchPersonControlPlan qqchPersonControlPlan);

    int insertQqchPersonControlPlan(QqchPersonControlPlan qqchPersonControlPlan);

    int insertQqchPersonControlPlanList(
        @Param("qqchPersonControlPlanList") List<QqchPersonControlPlan> qqchPersonControlPlanList);

    int updateQqchPersonControlPlan(QqchPersonControlPlan qqchPersonControlPlan);

    int updateQqchPersonControlPlanList(@Param("list") List<QqchPersonControlPlan> qqchPersonControlPlanList);

    int deleteQqchPersonControlPlan(QqchPersonControlPlan qqchPersonControlPlan);

    int deleteQqchPersonControlPlanByPks(@Param("qqchPersonControlPlanPkList") List<Long> qqchPersonControlPlanPkList);
}
