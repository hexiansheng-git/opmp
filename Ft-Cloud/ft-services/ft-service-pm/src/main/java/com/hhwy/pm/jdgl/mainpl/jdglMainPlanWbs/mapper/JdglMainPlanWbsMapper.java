package com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.domain.JdglMainPlanWbs;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:35
 * @remark
 */
public interface JdglMainPlanWbsMapper {

    JdglMainPlanWbs getJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs);

    List<JdglMainPlanWbs> getJdglMainPlanWbsList(JdglMainPlanWbs jdglMainPlanWbs);

    int insertJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs);

    int insertJdglMainPlanWbsList(@Param("jdglMainPlanWbsList") List<JdglMainPlanWbs> jdglMainPlanWbsList);

    int updateJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs);

    int updateJdglMainPlanWbsList(@Param("jdglMainPlanWbsList") List<JdglMainPlanWbs> jdglMainPlanWbsList);

    int deleteJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs);

    int deleteJdglMainPlanWbsByPks(@Param("jdglMainPlanWbsPkList") List<Long> jdglMainPlanWbsPkList);
}
