package com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.QqchAdjustAnalyse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:45:37
 * @remark qqch_adjust_analyse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchAdjustAnalyseVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchAdjustAnalyse> qqchAdjustAnalyseList;
}
