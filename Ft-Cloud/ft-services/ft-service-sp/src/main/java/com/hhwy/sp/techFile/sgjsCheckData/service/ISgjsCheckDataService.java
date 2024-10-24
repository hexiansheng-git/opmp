package com.hhwy.sp.techFile.sgjsCheckData.service;

import java.util.List;

import com.hhwy.sp.techFile.sgjsCheckData.domain.SgjsCheckData;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprint;

/**
 * @author xuzl
 * @date 2024-10-18 15:52:00
 * @remark
 */
public interface ISgjsCheckDataService {

    SgjsCheckData getSgjsCheckData(SgjsCheckData sgjsCheckData);

    List<SgjsCheckData> getSgjsCheckDataList(SgjsCheckData sgjsCheckData);

    int insertSgjsCheckData(SgjsCheckData sgjsCheckData);

    int insertSgjsCheckDataList(List<SgjsCheckData> sgjsCheckDataList);

    //void insertSgjsCheckDataList(List<SgjsCheckData> sgjsCheckDataList);

    int updateSgjsCheckData(SgjsCheckData sgjsCheckData);

    int updateSgjsCheckDataList(List<SgjsCheckData> sgjsCheckDataList);

    int deleteSgjsCheckData(SgjsCheckData sgjsCheckData);

    int deleteSgjsCheckDataByPks(List<Long> sgjsCheckDataPkList);
}
