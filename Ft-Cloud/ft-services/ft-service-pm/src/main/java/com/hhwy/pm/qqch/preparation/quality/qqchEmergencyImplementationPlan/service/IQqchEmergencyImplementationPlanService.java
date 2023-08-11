package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.service;

import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.domain.QqchEmergencyImplementationPlan;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.domain.vo.QqchEmergencyImplementationPlanVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-10 18:39:15
 * @remark
 */
public interface IQqchEmergencyImplementationPlanService {

    QqchEmergencyImplementationPlan getQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);

    QqchEmergencyImplementationPlanVo getQqchEmergencyImplementationPlanList(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);

    int insertQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);


    int updateQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);

    int updateQqchEmergencyImplementationPlanList(List<QqchEmergencyImplementationPlan> qqchEmergencyImplementationPlanList);

    int deleteQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan);

    int deleteQqchEmergencyImplementationPlanByPks(List<Long> qqchEmergencyImplementationPlanPkList);

    void save(QqchEmergencyImplementationPlanVo vo);
}
