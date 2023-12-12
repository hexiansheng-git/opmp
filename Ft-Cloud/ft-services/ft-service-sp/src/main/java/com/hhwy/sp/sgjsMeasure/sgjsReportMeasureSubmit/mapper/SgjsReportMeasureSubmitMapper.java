package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmit;

/**
 * @author zmh
 * @date 2023-12-08 16:19:52
 * @remark 
 */
public interface SgjsReportMeasureSubmitMapper {
                                                                                                                                                                                                                                                                                                                                                    
    SgjsReportMeasureSubmit getSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    List<SgjsReportMeasureSubmit> getSgjsReportMeasureSubmitList(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    int insertSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

    int batchAdd(@Param("sgjsReportMeasureSubmitList") List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitList);

    int updateSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

            int updateSgjsReportMeasureSubmitList(@Param("sgjsReportMeasureSubmitList") List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitList);
    
    int deleteSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit);

            int deleteSgjsReportMeasureSubmitByPks(@Param("sgjsReportMeasureSubmitPkList") List<Long> sgjsReportMeasureSubmitPkList);

    void deleteAll(SgjsReportMeasureSubmit info);

    void deleteWbsAll(SgjsReportMeasureSubmit info);
}
