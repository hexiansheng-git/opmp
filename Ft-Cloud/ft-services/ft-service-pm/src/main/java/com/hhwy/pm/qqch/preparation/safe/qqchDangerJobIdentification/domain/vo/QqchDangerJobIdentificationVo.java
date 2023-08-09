package com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.domain.QqchDangerJobIdentification;
import lombok.Data;

import java.util.List;

@Data
public class QqchDangerJobIdentificationVo extends PreparationEntity {

    private List<QqchDangerJobIdentification> qqchDangerJobIdentificationList;
}
