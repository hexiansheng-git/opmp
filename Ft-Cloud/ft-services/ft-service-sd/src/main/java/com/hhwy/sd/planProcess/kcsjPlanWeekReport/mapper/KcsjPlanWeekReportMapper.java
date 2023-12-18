package com.hhwy.sd.planProcess.kcsjPlanWeekReport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sd.planProcess.kcsjPlanWeekReport.domain.KcsjPlanWeekReport;

/**
 * @author cjh
 * @date 2023-12-18 11:12:15
 * @remark
 */
public interface KcsjPlanWeekReportMapper {

    KcsjPlanWeekReport getKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport);

    List<KcsjPlanWeekReport> getKcsjPlanWeekReportList(KcsjPlanWeekReport kcsjPlanWeekReport);

    int insertKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport);

    int insertKcsjPlanWeekReportList(@Param("kcsjPlanWeekReportList") List<KcsjPlanWeekReport> kcsjPlanWeekReportList);

    int updateKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport);

    int updateKcsjPlanWeekReportList(@Param("kcsjPlanWeekReportList") List<KcsjPlanWeekReport> kcsjPlanWeekReportList);

    int deleteKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport);

    int deleteKcsjPlanWeekReportByPks(@Param("kcsjPlanWeekReportPkList") List<Long> kcsjPlanWeekReportPkList);
}
