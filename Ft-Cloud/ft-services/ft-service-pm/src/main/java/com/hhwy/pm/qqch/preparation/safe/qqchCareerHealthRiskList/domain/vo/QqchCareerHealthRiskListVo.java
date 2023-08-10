package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.domain.QqchCareerHealthRiskList;
import lombok.Data;

import java.util.List;

@Data
public class QqchCareerHealthRiskListVo extends PreparationEntity {

    private List<QqchCareerHealthRiskList> qqchCareerHealthRiskListList;
}
