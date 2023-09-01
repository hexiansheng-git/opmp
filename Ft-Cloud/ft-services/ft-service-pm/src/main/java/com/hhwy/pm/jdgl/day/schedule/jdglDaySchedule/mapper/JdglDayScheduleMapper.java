package com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;

/**
 * @author cjh
 * @date 2023-08-24 14:05:37
 * @remark
 */
public interface JdglDayScheduleMapper {

    JdglDaySchedule getJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    List<JdglDaySchedule> getJdglDayScheduleList(JdglDaySchedule jdglDaySchedule);

    int insertJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    int insertJdglDayScheduleList(@Param("jdglDayScheduleList") List<JdglDaySchedule> jdglDayScheduleList);

    int updateJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    int updateJdglDayScheduleList(@Param("jdglDayScheduleList") List<JdglDaySchedule> jdglDayScheduleList);

    int deleteJdglDaySchedule(JdglDaySchedule jdglDaySchedule);

    int deleteJdglDayScheduleByPks(@Param("jdglDaySchedulePkList") List<Long> jdglDaySchedulePkList);

    int updateJdglDayScheduleValue(@Param("id") Long id, @Param("date") Date date);

    JdglDaySchedule getHistoryValue(@Param("date") Date date);

    BigDecimal getCountValue(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    List<JdglDaySchedule> getListByDateRange(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
}
