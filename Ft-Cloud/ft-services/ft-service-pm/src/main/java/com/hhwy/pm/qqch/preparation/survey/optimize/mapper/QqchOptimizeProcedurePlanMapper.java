package com.hhwy.pm.qqch.preparation.survey.optimize.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeProcedurePlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
@Repository
public interface QqchOptimizeProcedurePlanMapper {

    QqchOptimizeProcedurePlan getQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan);

    /**
     * 获取优化程序策划集合
     * @return
     */
    List<QqchOptimizeProcedurePlan> getQqchOptimizeProcedurePlanList();

    int insertQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan);

    int insertQqchOptimizeProcedurePlanList(@Param("qqchOptimizeProcedurePlanList") List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList);

    int updateQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan);

    int updateQqchOptimizeProcedurePlanList(@Param("list") List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList);

    int deleteQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan);

    int deleteQqchOptimizeProcedurePlanByPks(@Param("qqchOptimizeProcedurePlanPkList") List<Long> qqchOptimizeProcedurePlanPkList);
}
