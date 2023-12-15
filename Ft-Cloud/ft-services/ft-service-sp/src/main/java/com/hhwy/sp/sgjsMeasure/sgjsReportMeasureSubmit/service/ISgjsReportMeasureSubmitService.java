package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmit;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmitVo;

import java.util.List;

/**
 * @author zmh
 * @date 2023-12-08 16:19:52
 * @remark
 */
public interface ISgjsReportMeasureSubmitService {

    SgjsReportMeasureSubmit getSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    SgjsReportMeasureSubmitVo list(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    int insertSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    AjaxResult batchAdd(SgjsReportMeasureSubmitVo sgjsReportMeasureSubmitVo);

    int updateSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    int updateSgjsReportMeasureSubmitList(List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitList);

    int deleteSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    int deleteSgjsReportMeasureSubmitByPks(List<Long> sgjsReportMeasureSubmitPkList);

    List<SgjsReportMeasureSubmit> getIds(List<Long> ids);

    /**
     * 查询附件组id
     *
     * @param submit
     */
    List<String> bathExportZip(SgjsReportMeasureSubmit submit);
}
