package com.hhwy.sp.experiment.sgjsExperimentTotalPlan.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.domain.SgjsExperimentTotalPlan;

/**
 * @author lcf--试验总体计划
 * @date 2023-12-11 10:00:11
 * @remark 
 */
public interface SgjsExperimentTotalPlanMapper {
                                                                                                                                                                                                                                                                                                                                        
    SgjsExperimentTotalPlan getSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

    List<SgjsExperimentTotalPlan> getSgjsExperimentTotalPlanList(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

    int insertSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

    int insertSgjsExperimentTotalPlanList(@Param("sgjsExperimentTotalPlanList") List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList);

    int updateSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

            int updateSgjsExperimentTotalPlanList(@Param("sgjsExperimentTotalPlanList") List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList);
    
    int deleteSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan);

            int deleteSgjsExperimentTotalPlanByPks(@Param("sgjsExperimentTotalPlanPkList") List<Long> sgjsExperimentTotalPlanPkList);
    }
