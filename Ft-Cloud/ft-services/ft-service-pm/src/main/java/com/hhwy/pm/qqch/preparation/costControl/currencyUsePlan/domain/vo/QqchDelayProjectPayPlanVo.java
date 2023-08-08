package com.hhwy.pm.qqch.preparation.costControl.currencyUsePlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.costControl.currencyUsePlan.domain.QqchDelayProjectPayPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:09:45
 * @remark qqch_delay_project_pay_plan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchDelayProjectPayPlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList;
}
