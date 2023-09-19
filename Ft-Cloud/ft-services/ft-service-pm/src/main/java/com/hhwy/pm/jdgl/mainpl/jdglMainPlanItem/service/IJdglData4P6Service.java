package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;

import java.util.List;

public interface IJdglData4P6Service {

    List<JdglMainPlanItem> initJdglData4P6();

    List<JdglMainPlanItem> initJdglData4P6ByOne(String tenantKey);

    List<JdglMainPlanItem> initAllJdglData4P6();


}
