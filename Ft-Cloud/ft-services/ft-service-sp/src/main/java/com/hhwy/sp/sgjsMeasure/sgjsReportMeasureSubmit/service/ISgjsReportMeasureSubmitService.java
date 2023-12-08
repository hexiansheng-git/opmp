package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.service;

import java.util.List;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmit;

/**
 * @author zmh
 * @date 2023-12-08 16:19:52
 * @remark 
 */
public interface ISgjsReportMeasureSubmitService {
                                                                                                                                                                                                                                                                                                                                                    
    SgjsReportMeasureSubmit getSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    List<SgjsReportMeasureSubmit> getSgjsReportMeasureSubmitList(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    int insertSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    int insertSgjsReportMeasureSubmitList(List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitList);

    int updateSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

            int updateSgjsReportMeasureSubmitList(List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitList);
    
    int deleteSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

            int deleteSgjsReportMeasureSubmitByPks(List<Long> sgjsReportMeasureSubmitPkList);
    }
