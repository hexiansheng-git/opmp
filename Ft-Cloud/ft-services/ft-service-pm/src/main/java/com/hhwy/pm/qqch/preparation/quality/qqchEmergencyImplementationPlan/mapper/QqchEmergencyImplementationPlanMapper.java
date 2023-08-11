package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.domain.QqchEmergencyImplementationPlan;

/**
 * @author ldd
 * @date 2023-08-10 18:39:15
 * @remark 
 */
public interface QqchEmergencyImplementationPlanMapper {
                                                                                                                                                                                                                                                                                                                                                                
    QqchEmergencyImplementationPlan getQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);

    List<QqchEmergencyImplementationPlan> getQqchEmergencyImplementationPlanList(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);

    int insertQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);

    int insertQqchEmergencyImplementationPlanList(@Param("qqchEmergencyImplementationPlanList") List<QqchEmergencyImplementationPlan> qqchEmergencyImplementationPlanList);

    int updateQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);

            int updateQqchEmergencyImplementationPlanList(@Param("qqchEmergencyImplementationPlanList") List<QqchEmergencyImplementationPlan> qqchEmergencyImplementationPlanList);
    
    int deleteQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);

            int deleteQqchEmergencyImplementationPlanByPks(@Param("qqchEmergencyImplementationPlanPkList") List<Long> qqchEmergencyImplementationPlanPkList);
    }
