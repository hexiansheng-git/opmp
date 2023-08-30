package com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.service;

import java.util.List;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.domain.JdglMainPlanWbs;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:35
 * @remark
 */
public interface IJdglMainPlanWbsService {

    JdglMainPlanWbs getJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs);

    List<JdglMainPlanWbs> getJdglMainPlanWbsList(JdglMainPlanWbs jdglMainPlanWbs);

    int insertJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs);

    int insertJdglMainPlanWbsList(List<JdglMainPlanWbs> jdglMainPlanWbsList);

    int updateJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs);

    int updateJdglMainPlanWbsList(List<JdglMainPlanWbs> jdglMainPlanWbsList);

    int deleteJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs);

    int deleteJdglMainPlanWbsByPks(List<Long> jdglMainPlanWbsPkList);
}
