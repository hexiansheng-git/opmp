package com.hhwy.sp.experiment.sgjsExperimentTotalPlan.service;

import java.util.List;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.domain.SgjsExperimentTotalPlan;

/**
 * @author lcf--试验总体计划
 * @date 2023-12-11 10:00:11
 * @remark
 */
public interface ISgjsExperimentTotalPlanService {

    SgjsExperimentTotalPlan getSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

    List<SgjsExperimentTotalPlan> getSgjsExperimentTotalPlanList(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

    int insertSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

    int insertSgjsExperimentTotalPlanList(List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList);

    int updateSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

    int updateSgjsExperimentTotalPlanList(List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList);

    int deleteSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

    int deleteSgjsExperimentTotalPlanByPks(List<Long> sgjsExperimentTotalPlanPkList);

    /**
     * 查询详情
     *
     * @return
     */
    SgjsExperimentTotalPlan selectDetailInfo();
}
