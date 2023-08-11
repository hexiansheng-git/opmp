package com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.QqchProjectBreakEvenPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:39:59
 * @remark qqch_project_break_even_point
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchProjectBreakEvenPointVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchProjectBreakEvenPoint> list;
}
