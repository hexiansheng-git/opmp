package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.domain.QqchCareerHealthRiskManagement;
import lombok.Data;
import java.util.List;

@Data
public class QqchCareerHealthRiskManagementVo extends PreparationEntity {

    private List<QqchCareerHealthRiskManagement> qqchCareerHealthRiskManagementList;
}
