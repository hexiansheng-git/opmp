package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.vo;

import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmit;
import lombok.Data;

import java.util.List;

/**
 * @author zmh
 * @date 2023-12-08 16:19:52
 * @remark
 */
@Data
public class ReportMeasureSubmitPushVo {

    private List<SgjsReportMeasureSubmit> insertList;

    private List<SgjsReportMeasureSubmit> updateList;

    private List<String> delIdList;

}
