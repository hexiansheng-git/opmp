package com.hhwy.sd.planProcess.kcsjPlanWeekReport.service;

import java.util.Date;
import java.util.List;

import com.hhwy.sd.planProcess.kcsjPlanWeekReport.domain.KcsjPlanWeekReport;

/**
 * @author cjh
 * @date 2023-12-18 11:12:15
 * @remark
 */
public interface IKcsjPlanWeekReportService {

    KcsjPlanWeekReport getKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport);

    List<KcsjPlanWeekReport> getKcsjPlanWeekReportList(KcsjPlanWeekReport kcsjPlanWeekReport);

    int insertKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport);

    int insertKcsjPlanWeekReportList(List<KcsjPlanWeekReport> kcsjPlanWeekReportList);

    int updateKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport);

    int updateKcsjPlanWeekReportList(List<KcsjPlanWeekReport> kcsjPlanWeekReportList);

    int deleteKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport);

    int deleteKcsjPlanWeekReportByPks(List<Long> kcsjPlanWeekReportPkList);

    int produceData();

    int produceDataByPeriod(Date period);
}
