package com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.service;

import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.KcsjPlanMonthlyReport;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.vo.PlanMonthlyReportQueryVo;

import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:21:39
 * @remark
 */
public interface IKcsjPlanMonthlyReportService {

    KcsjPlanMonthlyReport getKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport);

    List<KcsjPlanMonthlyReport> getKcsjPlanMonthlyReportList(PlanMonthlyReportQueryVo queryVo);

    int insertKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport);

    int insertKcsjPlanMonthlyReportList(List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList);

    int updateKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport);

    int updateKcsjPlanMonthlyReportList(List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList);

    int deleteKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport);

    int deleteKcsjPlanMonthlyReportByPks(List<Long> kcsjPlanMonthlyReportPkList);

    void generateMonthlyReport();

    void generateMonthlyReportByDate(Date date);
}
