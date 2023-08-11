package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.domain.QqchEmergencyImplementationPlan;
import lombok.Data;

import java.util.List;

@Data
public class QqchEmergencyImplementationPlanVo extends PreparationEntity {

    private List<QqchEmergencyImplementationPlan> qqchEmergencyImplementationPlanList;
}
