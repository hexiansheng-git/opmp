package com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.QqchSafetyTrain;
import lombok.Data;

import java.util.List;

@Data
public class QqchSafetyTrainVo extends PreparationEntity {

    private List<QqchSafetyTrain> qqchSafetyTrainList;
}
