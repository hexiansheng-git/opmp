package com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.QqchMeasureSettlePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:45:52
 * @remark qqch_measure_settle_plan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchMeasureSettlePlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchMeasureSettlePlan> list;
}
