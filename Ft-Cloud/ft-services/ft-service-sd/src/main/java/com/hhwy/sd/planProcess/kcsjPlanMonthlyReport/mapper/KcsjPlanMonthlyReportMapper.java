package com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.mapper;

import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.KcsjPlanMonthlyReport;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.vo.PlanMonthlyReportQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:21:39
 * @remark
 */
@Repository
public interface KcsjPlanMonthlyReportMapper {

    KcsjPlanMonthlyReport getKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport);

    List<KcsjPlanMonthlyReport> getKcsjPlanMonthlyReportList(PlanMonthlyReportQueryVo queryVo);

    int insertKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport);

    int insertKcsjPlanMonthlyReportList(@Param("kcsjPlanMonthlyReportList") List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList);

    int updateKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport);

    int updateKcsjPlanMonthlyReportList(@Param("list") List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList);

    int deleteKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport);

    int deleteKcsjPlanMonthlyReportByPks(@Param("kcsjPlanMonthlyReportPkList") List<Long> kcsjPlanMonthlyReportPkList);

    void deleteMonthlyReportByYearMonth(@Param("yearMonth") String yearMonth);
}
