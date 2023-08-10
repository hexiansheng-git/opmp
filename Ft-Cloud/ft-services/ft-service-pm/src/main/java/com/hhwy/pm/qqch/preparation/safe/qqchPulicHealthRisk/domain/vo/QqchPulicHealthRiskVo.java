package com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.domain.QqchPulicHealthRisk;
import lombok.Data;

import java.util.List;

@Data
public class QqchPulicHealthRiskVo extends PreparationEntity {

    private List<QqchPulicHealthRisk> qqchPulicHealthRiskList;
}
