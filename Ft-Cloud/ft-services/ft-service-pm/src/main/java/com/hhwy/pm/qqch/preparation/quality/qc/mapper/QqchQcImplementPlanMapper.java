package com.hhwy.pm.qqch.preparation.quality.qc.mapper;

import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcImplementPlan;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:39
 * @remark 9.6.2 QC实施计划
 */
public interface QqchQcImplementPlanMapper {

    QqchQcImplementPlan getQqchQcImplementPlan(QqchQcImplementPlan qqchQcImplementPlan);

    List<QqchQcImplementPlan> getQqchQcImplementPlanList(QqchQcImplementPlan qqchQcImplementPlan);

    int insertQqchQcImplementPlan(QqchQcImplementPlan qqchQcImplementPlan);

    int insertQqchQcImplementPlanList(
        @Param("qqchQcImplementPlanList") List<QqchQcImplementPlan> qqchQcImplementPlanList);

    int updateQqchQcImplementPlan(QqchQcImplementPlan qqchQcImplementPlan);

    int updateQqchQcImplementPlanList(@Param("list") List<QqchQcImplementPlan> qqchQcImplementPlanList);

    int deleteQqchQcImplementPlan(QqchQcImplementPlan qqchQcImplementPlan);

    int deleteQqchQcImplementPlanByPks(@Param("qqchQcImplementPlanPkList") List<Long> qqchQcImplementPlanPkList);
}
