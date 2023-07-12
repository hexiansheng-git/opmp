package com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo;

import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchChangeProcedurePlanVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：模块确认情况
     */
    private QqchModuleConfirmCase qqchModuleConfirmCase;
    /**
     * 字段描述：变更程序策划集合
     */
    private List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList;
}
