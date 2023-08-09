package com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.domain.QqchNaturalDisasterRiskPlan;
import lombok.Data;
import java.util.List;

@Data
public class QqchNaturalDisasterRiskPlanVo extends PreparationEntity {

    private List<QqchNaturalDisasterRiskPlan> qqchNaturalDisasterRiskPlanList;
}
