package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.ProjectInfo;

import java.util.List;

public interface IJdglData4P6Service {

    List<JdglMainPlanItem> initJdglData4P6();

    List<JdglMainPlanItem> initJdglData4P6ByOne(String tenantKey);

    List<JdglMainPlanItem> initAllJdglData4P6();

    List<JdglMainPlanItem> initOneJdglData4P6ByTenent(String projectId);

    ProjectInfo getProjectInfo(String projectCode);

}
