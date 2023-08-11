package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain.QqchEmergencyExerciseControl;
import lombok.Data;

import java.util.List;

@Data
public class QqchEmergencyExerciseControlVo extends PreparationEntity {

    private List<QqchEmergencyExerciseControl> qqchEmergencyExerciseControlList;
}
