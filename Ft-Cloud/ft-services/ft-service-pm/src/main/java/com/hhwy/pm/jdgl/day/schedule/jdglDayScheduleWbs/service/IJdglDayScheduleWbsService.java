package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service;

import java.util.Date;
import java.util.List;

import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Add;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;

/**
 * @author cjh
 * @date 2023-08-24 14:05:56
 * @remark
 */
public interface IJdglDayScheduleWbsService {

    JdglDayScheduleWbs getJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs);

    List<JdglDayScheduleWbs> getJdglDayScheduleWbsList(JdglDayScheduleWbs jdglDayScheduleWbs);

    int insertJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs);

    int insertJdglDayScheduleWbsList(List<JdglDayScheduleWbs> jdglDayScheduleWbsList);

    int updateJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs);

    int updateJdglDayScheduleWbsList(List<JdglDayScheduleWbs> jdglDayScheduleWbsList, Long dayScheduleId);

    int deleteJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs);

    int deleteJdglDayScheduleWbsByPks(List<Long> jdglDayScheduleWbsPkList);

    List<JdglDayScheduleWbs> getJdglDayScheduleWbsLazyList(JdglDayScheduleWbs jdglDayScheduleWbsParam);

    List<JdglDayScheduleWbs> getInitWbs(Date date);

    List<JdglDayScheduleWbs4Value> getWbsListByDateRange(Date startDate, Date endDate);

    List<JdglDayScheduleWbs4Value> getTotalWbsListByDateRange(Date endDate);

    List<JdglDayScheduleWbs> getJdglDayScheduleWbsListByPerson(JdglDayScheduleWbs jdglDayScheduleWbs);

    /**
     * 获取wbs及图纸复核数据并过滤当前日报的wbs
     * @return
     */
    List<JdglDayScheduleWbs> getLazyWbs4NoThis(JdglDayScheduleWbs jdglDayScheduleWbsParam);

    /**
     * 获取末级节点wbs数据
     * @param jdglDayScheduleWbsParam
     * @return
     */
    List<JdglDayScheduleWbs> getLeafWbsList(JdglDayScheduleWbs jdglDayScheduleWbsParam);

//    int  addWbsList(List<JdglDayScheduleWbs> jdglDayScheduleWbsListParam);
    int  addWbsList(JdglDayScheduleWbs4Add jdglDayScheduleWbsListParam);
}
