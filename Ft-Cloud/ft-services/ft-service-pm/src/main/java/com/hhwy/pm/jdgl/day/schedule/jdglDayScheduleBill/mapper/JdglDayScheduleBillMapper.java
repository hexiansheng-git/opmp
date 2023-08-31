package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;

/**
 * @author cjh
 * @date 2023-08-24 14:05:49
 * @remark
 */
public interface JdglDayScheduleBillMapper {

    JdglDayScheduleBill getJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill);

    List<JdglDayScheduleBill> getJdglDayScheduleBillList(JdglDayScheduleBill jdglDayScheduleBill);

    int insertJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill);

    int insertJdglDayScheduleBillList(@Param("jdglDayScheduleBillList") List<JdglDayScheduleBill> jdglDayScheduleBillList);

    int updateJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill);

    int updateJdglDayScheduleBillList(@Param("jdglDayScheduleBillList") List<JdglDayScheduleBill> jdglDayScheduleBillList);

    int deleteJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill);

    int deleteJdglDayScheduleBillByDayScheduleIdAndWbsCode(@Param("dayScheduleId") Long dayScheduleId,@Param("wbsCode") String wbsCode);

    int deleteJdglDayScheduleBillByPks(@Param("jdglDayScheduleBillPkList") List<Long> jdglDayScheduleBillPkList);

    List<JdglDayScheduleBill> getHistoryBill(@Param("date") Date date);

    int deleteJdglDayScheduleBillByDayScheduleId(@Param("dayScheduleId") Long dayScheduleId);

    List<JdglDayScheduleBill> getBillValueListByRangeDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    List<JdglDayScheduleBill> getBillValueListByEndDate(@Param("endDate") Date endDate);


}
