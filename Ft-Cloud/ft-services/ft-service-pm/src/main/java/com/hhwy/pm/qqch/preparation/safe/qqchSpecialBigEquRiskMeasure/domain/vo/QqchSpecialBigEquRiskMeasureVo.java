package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.QqchSpecialBigEquRiskMeasure;
import lombok.Data;

import java.util.List;

@Data
public class QqchSpecialBigEquRiskMeasureVo extends PreparationEntity {

    private List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureList;
}
