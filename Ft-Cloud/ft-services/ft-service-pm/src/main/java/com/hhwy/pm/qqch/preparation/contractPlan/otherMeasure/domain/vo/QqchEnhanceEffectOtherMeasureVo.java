package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchEnhanceEffectOtherMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:08:56
 * @remark qqch_enhance_effect_other_measure
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchEnhanceEffectOtherMeasureVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchEnhanceEffectOtherMeasure> list;
}
