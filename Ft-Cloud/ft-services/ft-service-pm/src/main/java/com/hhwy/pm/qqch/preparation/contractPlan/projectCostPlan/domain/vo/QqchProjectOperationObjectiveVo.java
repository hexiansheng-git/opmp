package com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.QqchProjectOperationObjective;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:39:48
 * @remark qqch_project_operation_objective
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchProjectOperationObjectiveVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchProjectOperationObjective> list;
}
