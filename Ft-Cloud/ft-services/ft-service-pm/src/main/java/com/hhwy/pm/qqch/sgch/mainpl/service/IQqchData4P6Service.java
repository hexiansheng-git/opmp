package com.hhwy.pm.qqch.sgch.mainpl.service;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.ProjectInfo;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;

import java.math.BigDecimal;
import java.util.List;

public interface IQqchData4P6Service {

    List<QqchMainPlanItem> initQqchData4P6(String tenantKey, BigDecimal version);

}
