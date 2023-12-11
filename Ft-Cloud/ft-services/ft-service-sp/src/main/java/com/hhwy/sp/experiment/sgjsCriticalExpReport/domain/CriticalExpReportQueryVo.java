package com.hhwy.sp.experiment.sgjsCriticalExpReport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author han
 * @date 2023-12-11 16:41:12
 * @remark sgjs_critical_exp_report
 */
@Data
public class CriticalExpReportQueryVo {

    /**
     * 字段描述：试验报告编号
     */
    private String expReportCode;
    /**
     * 字段描述：试验报告名称
     */
    private String expReportName;
    /**
     * 字段描述：实际提交日期-开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualCommitDateStart;
    /**
     * 字段描述：实际提交日期-结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualCommitDateEnd;
}
