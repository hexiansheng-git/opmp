package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import lombok.Data;

import java.util.List;

@Data
public class QqchWeightEngineeringListVo extends PreparationEntity {

    private List<QqchWeightEngineeringList> qqchWeightEngineeringListList;
}
