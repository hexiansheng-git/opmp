package com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han
 * @date 2023-12-18 11:21:39
 * @remark kcsj_plan_monthly_report
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanMonthlyReportQueryVo {
    /**
     * 字段描述：月报期次
     */
    private String period;
    /**
     * 字段描述：提交人姓名
     */
    private String submitUserName;
}
