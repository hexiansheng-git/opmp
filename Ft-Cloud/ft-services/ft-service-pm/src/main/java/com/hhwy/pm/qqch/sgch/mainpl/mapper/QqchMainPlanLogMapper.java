package com.hhwy.pm.qqch.sgch.mainpl.mapper;

import java.util.List;

import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanLog;
import org.apache.ibatis.annotations.Param;

/**
 * @author cjh
 * @date 2023-12-15 17:00:32
 * @remark
 */
public interface QqchMainPlanLogMapper {

    QqchMainPlanLog getQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog);

    List<QqchMainPlanLog> getQqchMainPlanLogList(QqchMainPlanLog qqchMainPlanLog);

    int insertQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog);

    int insertQqchMainPlanLogList(@Param("qqchMainPlanLogList") List<QqchMainPlanLog> qqchMainPlanLogList);

    int updateQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog);

    int updateQqchMainPlanLogList(@Param("qqchMainPlanLogList") List<QqchMainPlanLog> qqchMainPlanLogList);

    int deleteQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog);

    int deleteQqchMainPlanLogByPks(@Param("qqchMainPlanLogPkList") List<Long> qqchMainPlanLogPkList);
}
