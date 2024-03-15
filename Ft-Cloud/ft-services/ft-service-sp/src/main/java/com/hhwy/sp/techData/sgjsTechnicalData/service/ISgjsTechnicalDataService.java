package com.hhwy.sp.techData.sgjsTechnicalData.service;

import java.util.List;

import com.hhwy.sp.techData.sgjsTechnicalData.domain.SgjsTechnicalData;

/**
 * @author cjh
 * @date 2024-01-22 09:26:47
 * @remark
 */
public interface ISgjsTechnicalDataService {

    SgjsTechnicalData getSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData);

    List<SgjsTechnicalData> getSgjsTechnicalDataList(SgjsTechnicalData sgjsTechnicalData);

    int insertSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData);

    int insertSgjsTechnicalDataList(List<SgjsTechnicalData> sgjsTechnicalDataList);

    int updateSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData);

    int updateSgjsTechnicalDataList(Long dataCatalogId,List<SgjsTechnicalData> sgjsTechnicalDataList);

    int deleteSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData);

    int deleteSgjsTechnicalDataByCatalog(Long dataCatalogId);

    int deleteSgjsTechnicalDataByPks(List<Long> sgjsTechnicalDataPkList);

    List<SgjsTechnicalData> getList(SgjsTechnicalData sgjsTechnicalData);
}
