package com.hhwy.sp.experiment.sgjsCriticalExpReport.service;

import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.CriticalExpReportQueryVo;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.SgjsCriticalExpReport;

import java.util.List;

/**
 * @author han
 * @date 2023-12-11 16:41:12
 * @remark
 */
public interface ISgjsCriticalExpReportService {

    SgjsCriticalExpReport getSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport);

    List<SgjsCriticalExpReport> getSgjsCriticalExpReportList(CriticalExpReportQueryVo queryVo);

    int insertSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport);

    int insertSgjsCriticalExpReportList(List<SgjsCriticalExpReport> sgjsCriticalExpReportList);

    int updateSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport);

    int updateSgjsCriticalExpReportList(List<SgjsCriticalExpReport> sgjsCriticalExpReportList);

    int deleteSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport);

    int deleteSgjsCriticalExpReportByPks(List<Long> sgjsCriticalExpReportPkList);

    List<SgjsCriticalExpReport> getListByIds(List<Long> ids);
}
