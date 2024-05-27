package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.mapper;

import java.util.Date;
import java.util.List;

import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;

/**
 * @author cjh
 * @date 2023-08-24 14:05:56
 * @remark
 */
public interface JdglDayScheduleWbsMapper {

    JdglDayScheduleWbs getJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs);

    List<JdglDayScheduleWbs> getJdglDayScheduleWbsList(JdglDayScheduleWbs jdglDayScheduleWbs);

    int insertJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs);

    int insertJdglDayScheduleWbsList(@Param("jdglDayScheduleWbsList") List<JdglDayScheduleWbs> jdglDayScheduleWbsList);

    int updateJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs);

    int updateJdglDayScheduleWbsList(@Param("list") List<JdglDayScheduleWbs> jdglDayScheduleWbsList);

    int deleteJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs);

    int deleteJdglDayScheduleWbsByPks(@Param("jdglDayScheduleWbsPkList") List<Long> jdglDayScheduleWbsPkList);

    int deleteJdglDayScheduleWbsByDayScheduleId(Long dayScheduleId);

    List<JdglDayScheduleWbs4Value> getWbsListByDateRange4Value(@Param("startDate") Date startDate,@Param("endDate")  Date endDate);

    List<JdglDayScheduleWbs4Value> getWbsListByEndDate(@Param("endDate")  Date endDate);

    List<JdglDayScheduleWbs4Value> getTotalWbsListByDateRange4Value(@Param("endDate") Date endDate);

    List<JdglDayScheduleWbs> getLeafWbsList(JdglDayScheduleWbs jdglDayScheduleWbsParam);

    List<JdglDayScheduleWbs4Value> getTotalWbsListByDateRange4OnlyWbs(@Param("endDate") Date endDate);

    List<JdglDayScheduleWbs> getJdglDayScheduleWbsByDate(@Param("date") Date date);

    List<JdglDayScheduleWbs> getComplateQuantity(@Param("date") Date date);
}
