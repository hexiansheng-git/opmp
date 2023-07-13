package com.hhwy.pm.qqch.preparation.survey.risk.domain.vo;

import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchDailyControlPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:39:57
 * @remark 日常管控策划Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchDailyControlPlanVo {
    private static final long serialVersionUID = 1L;
    /**
     * 字段描述：模块确认情况
     */
    private QqchModuleConfirmCase qqchModuleConfirmCase;
    /**
     * 字段描述：日常管控策划集合
     */
    private List<QqchDailyControlPlan> qqchDailyControlPlanList;
}
