package com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.QqchTechRiskSolutions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:55:16
 * @remark 技术风险及应对措施
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchTechRiskSolutionsVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：技术风险及应对措施集合
     */
    private List<QqchTechRiskSolutions> list;
}
