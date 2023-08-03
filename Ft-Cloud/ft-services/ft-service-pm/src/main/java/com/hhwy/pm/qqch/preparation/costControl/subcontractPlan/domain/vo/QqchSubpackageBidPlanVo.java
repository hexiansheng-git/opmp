package com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.domain.QqchSubpackageBidPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:44
 * @remark qqch_subpackage_bid_plan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchSubpackageBidPlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList;
}
