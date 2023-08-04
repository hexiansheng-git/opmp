package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.QqchQualityRiskControlMeasures;
import lombok.Data;

import java.util.List;

@Data
public class QqchQualityRiskControlMeasuresVo extends PreparationEntity {

    private List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresList;
}
