package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.QqchQualityRiskList;
import lombok.Data;

import java.util.List;

@Data
public class QqchQualityRiskListVo extends PreparationEntity {

    private List<QqchQualityRiskList> qqchQualityRiskListList;
}
