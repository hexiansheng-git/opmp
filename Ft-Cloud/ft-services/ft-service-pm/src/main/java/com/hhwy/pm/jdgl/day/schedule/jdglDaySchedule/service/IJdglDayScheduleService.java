package com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service;

import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cjh
 * @date 2023-08-24 14:05:37
 * @remark
 */
public interface IJdglDayScheduleService {

    JdglDaySchedule getJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    List<JdglDaySchedule> getJdglDayScheduleList(JdglDaySchedule jdglDaySchedule);

    List<JdglDaySchedule> getListBy(JdglDaySchedule jdglDaySchedule);

    int insertJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    int insertJdglDayScheduleList(List<JdglDaySchedule> jdglDayScheduleList);

    int updateJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    int updateJdglDayScheduleList(List<JdglDaySchedule> jdglDayScheduleList);

    int deleteJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    int deleteJdglDayScheduleByPks(List<Long> jdglDaySchedulePkList);

    JdglDaySchedule getInit(JdglDaySchedule jdglDayScheduleParam);

    BigDecimal getCountValue(Date startDate, Date endDate);

    List<JdglDaySchedule>  getListByDateRange(Date startDate, Date endDate);

    JdglDaySchedule getJdglDayScheduleByPerson(JdglDaySchedule jdglDayScheduleParam);

    Map<String, BigDecimal> getMonthScheduleByMonthRange(Date startPeriod, Date endPeriod);

    void updateTaskStatus(Long id);

    BigDecimal getCountValueNotApprove(Date startDate, Date endDate);

    List<JdglDaySchedule> getAllListByDateRange(Date startDate, Date endDate);

    JdglDaySchedule getJdglDayScheduleById(Long dayScheduleId);
}
