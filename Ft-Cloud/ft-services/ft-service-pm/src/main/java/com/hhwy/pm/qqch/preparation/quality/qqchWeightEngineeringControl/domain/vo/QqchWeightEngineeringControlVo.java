package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.QqchWeightEngineeringControl;
import lombok.Data;

import java.util.List;

@Data
public class QqchWeightEngineeringControlVo extends PreparationEntity {

    private List<QqchWeightEngineeringControl> qqchWeightEngineeringControlList;
}
