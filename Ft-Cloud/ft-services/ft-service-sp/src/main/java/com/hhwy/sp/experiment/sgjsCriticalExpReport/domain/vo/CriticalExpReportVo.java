package com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo;

import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.SgjsCriticalExpReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-12-11 16:41:12
 * @remark sgjs_critical_exp_report
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriticalExpReportVo {

   private List<Long> delIdList;

   private List<SgjsCriticalExpReport> reportList;
}
