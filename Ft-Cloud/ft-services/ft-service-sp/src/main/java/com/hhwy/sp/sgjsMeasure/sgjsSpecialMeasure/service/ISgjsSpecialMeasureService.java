package com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.service;

import java.util.List;

import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.domain.SgjsSpecialMeasure;

/**
 * 施工技术--测量管理--特殊工程监控量测
 *
 * @author lcf
 * @date 2024-03-12 14:54:37
 * @remark
 */
public interface ISgjsSpecialMeasureService {

    SgjsSpecialMeasure getSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure);

    List<SgjsSpecialMeasure> getSgjsSpecialMeasureList(SgjsSpecialMeasure sgjsSpecialMeasure);

    int insertSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure);

    int insertSgjsSpecialMeasureList(List<SgjsSpecialMeasure> sgjsSpecialMeasureList);

    int updateSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure);

    int updateSgjsSpecialMeasureList(List<SgjsSpecialMeasure> sgjsSpecialMeasureList);

    int deleteSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure);

    int deleteSgjsSpecialMeasureByPks(List<Long> sgjsSpecialMeasurePkList);
}
