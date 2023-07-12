package com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo;

import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchComparisonSchemeVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：模块确认情况
     */
    private QqchModuleConfirmCase qqchModuleConfirmCase;
    /**
     * 字段描述：优重大设计方案比选-方案集合
     */
    private List<QqchComparisonScheme> qqchComparisonSchemeList;
}
