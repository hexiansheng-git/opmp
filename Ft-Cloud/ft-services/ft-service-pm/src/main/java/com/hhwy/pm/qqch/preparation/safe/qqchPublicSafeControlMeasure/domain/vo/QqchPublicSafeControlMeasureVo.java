package com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.domain.QqchPublicSafeControlMeasure;
import lombok.Data;

import java.util.List;

@Data
public class QqchPublicSafeControlMeasureVo extends PreparationEntity {

    private List<QqchPublicSafeControlMeasure> qqchPublicSafeControlMeasureList;

}
