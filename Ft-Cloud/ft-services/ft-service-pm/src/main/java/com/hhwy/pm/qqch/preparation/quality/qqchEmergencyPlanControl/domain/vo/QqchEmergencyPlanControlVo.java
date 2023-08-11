package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.domain.QqchEmergencyPlanControl;
import lombok.Data;

import java.util.List;

@Data
public class QqchEmergencyPlanControlVo extends PreparationEntity {

    private List<QqchEmergencyPlanControl> qqchEmergencyPlanControlList;
}
