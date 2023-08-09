package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRisk;
import lombok.Data;

import java.util.List;

@Data
public class QqchSocietySafeRiskVo extends PreparationEntity {

    private List<QqchSocietySafeRisk> qqchSocietySafeRiskList;
}
