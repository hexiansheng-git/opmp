package com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.domain.QqchNaturalDisasterRiskPlan;

/**
 * @author ldd
 * @date 2023-08-09 09:30:37
 * @remark 
 */
public interface QqchNaturalDisasterRiskPlanMapper {
                                                                                                                                                                                                                                                                                                                                                                            
    QqchNaturalDisasterRiskPlan getQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

    List<QqchNaturalDisasterRiskPlan> getQqchNaturalDisasterRiskPlanList(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

    int insertQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

    int insertQqchNaturalDisasterRiskPlanList(@Param("qqchNaturalDisasterRiskPlanList") List<QqchNaturalDisasterRiskPlan> qqchNaturalDisasterRiskPlanList);

    int updateQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

            int updateQqchNaturalDisasterRiskPlanList(@Param("qqchNaturalDisasterRiskPlanList") List<QqchNaturalDisasterRiskPlan> qqchNaturalDisasterRiskPlanList);
    
    int deleteQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

            int deleteQqchNaturalDisasterRiskPlanByPks(@Param("qqchNaturalDisasterRiskPlanPkList") List<Long> qqchNaturalDisasterRiskPlanPkList);
    }
