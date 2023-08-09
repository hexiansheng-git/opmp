package com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.service;

import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.domain.QqchNaturalDisasterRiskPlan;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.domain.vo.QqchNaturalDisasterRiskPlanVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 09:30:37
 * @remark
 */
public interface IQqchNaturalDisasterRiskPlanService {

    QqchNaturalDisasterRiskPlan getQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

    QqchNaturalDisasterRiskPlanVo getQqchNaturalDisasterRiskPlanList(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

    int insertQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

    int updateQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

    int updateQqchNaturalDisasterRiskPlanList(List<QqchNaturalDisasterRiskPlan> qqchNaturalDisasterRiskPlanList);

    int deleteQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan);

    int deleteQqchNaturalDisasterRiskPlanByPks(List<Long> qqchNaturalDisasterRiskPlanPkList);

    void save(QqchNaturalDisasterRiskPlanVo vo);
}
