package com.hhwy.pm.qqch.sgch.mainpl.service;

import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanLog;

import java.util.List;


/**
 * @author cjh
 * @date 2023-12-15 17:00:32
 * @remark
 */
public interface IQqchMainPlanLogService {

    QqchMainPlanLog getQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog);

    List<QqchMainPlanLog> getQqchMainPlanLogList(QqchMainPlanLog qqchMainPlanLog);

    int insertQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog);

    int insertQqchMainPlanLogList(List<QqchMainPlanLog> qqchMainPlanLogList);

    int updateQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog);

    int updateQqchMainPlanLogList(List<QqchMainPlanLog> qqchMainPlanLogList);

    int deleteQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog);

    int deleteQqchMainPlanLogByPks(List<Long> qqchMainPlanLogPkList);

    int updateQqchMainPlanLog(String operation, String logContent, int timeNum, String timeNumUnit);

}
