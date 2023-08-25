package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service;

import java.util.Date;
import java.util.List;

import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;

/**
 * @author cjh
 * @date 2023-08-24 14:05:49
 * @remark
 */
public interface IJdglDayScheduleBillService {

    JdglDayScheduleBill getJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill);

    List<JdglDayScheduleBill> getJdglDayScheduleBillList(JdglDayScheduleBill jdglDayScheduleBill);

    int insertJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill);

    int insertJdglDayScheduleBillList(List<JdglDayScheduleBill> jdglDayScheduleBillList);

    int updateJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill);

    int updateJdglDayScheduleBillList(List<JdglDayScheduleBill> jdglDayScheduleBillList, Long dayScheduleId, String wbsCode);

    int deleteJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill);

    int deleteJdglDayScheduleBillByPks(List<Long> jdglDayScheduleBillPkList);

    List<JdglDayScheduleWbs> getInitBill(List<JdglDayScheduleWbs> jdglDayScheduleWbsList, Date date);

    void deleteJdglDayScheduleBillByDayScheduleId(Long dayScheduleId);
}
