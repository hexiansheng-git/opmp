package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlan;
import lombok.Data;

import java.util.List;

@Data
public class MergeDataVo {

    private List<QqchAdvancedVindicatePlan> importDataList;

    private List<QqchAdvancedVindicatePlan> pageDataList;
}
