package com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service;

import java.util.List;

import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;

/**
 * @author cjh
 * @date 2023-08-24 14:05:37
 * @remark
 */
public interface IJdglDayScheduleService {

    JdglDaySchedule getJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    List<JdglDaySchedule> getJdglDayScheduleList(JdglDaySchedule jdglDaySchedule);

    int insertJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    int insertJdglDayScheduleList(List<JdglDaySchedule> jdglDayScheduleList);

    int updateJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    int updateJdglDayScheduleList(List<JdglDaySchedule> jdglDayScheduleList);

    int deleteJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    int deleteJdglDayScheduleByPks(List<Long> jdglDaySchedulePkList);

    JdglDaySchedule getInit(JdglDaySchedule jdglDayScheduleParam);
}
