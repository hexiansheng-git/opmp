package com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo;

import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeProcedurePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchOptimizeProcedurePlanVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：模块确认情况
     */
    private QqchModuleConfirmCase qqchModuleConfirmCase;
    /**
     * 字段描述：优化程序策划集合
     */
    private List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList;
}
