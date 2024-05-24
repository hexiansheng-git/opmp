package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.service;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.vo.QqchSafeEnvriRiskManageVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRiskPlan;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo.QqchSocietySafeRiskPlanVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo.QqchSocietySafeRiskVo;

/**
 * @author hwj
 * @date 2024-05-23 17:36:41
 * @remark
 */
public interface IQqchSocietySafeRiskPlanService {

    QqchSocietySafeRiskPlan getQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    List<QqchSocietySafeRiskPlan> getQqchSocietySafeRiskPlanList(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    int insertQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    int insertQqchSocietySafeRiskPlanList(List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanList);

    int updateQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    int updateQqchSocietySafeRiskPlanList(List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanList);

    int deleteQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan);

    int deleteQqchSocietySafeRiskPlanByPks(List<Long> qqchSocietySafeRiskPlanPkList);
    void save(QqchSocietySafeRiskPlanVo vo);
    QqchSocietySafeRiskPlanVo getList(BigDecimal version);
}
