package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRisk;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRiskPlan;
import lombok.Data;

import java.util.List;

@Data
public class QqchSocietySafeRiskPlanVo extends PreparationEntity {

    private List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskList;
}
