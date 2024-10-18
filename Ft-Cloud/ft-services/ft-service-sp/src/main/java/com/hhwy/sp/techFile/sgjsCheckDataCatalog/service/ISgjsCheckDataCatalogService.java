package com.hhwy.sp.techFile.sgjsCheckDataCatalog.service;

import java.util.List;

import com.hhwy.sp.techFile.sgjsCheckDataCatalog.domain.SgjsCheckDataCatalog;

/**
 * @author xuzl
 * @date 2024-10-18 16:45:49
 * @remark
 */
public interface ISgjsCheckDataCatalogService {

    SgjsCheckDataCatalog getSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    List<SgjsCheckDataCatalog> getSgjsCheckDataCatalogList(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    int insertSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    int insertSgjsCheckDataCatalogList(List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList);

    int updateSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    int updateSgjsCheckDataCatalogList(List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList);

    int deleteSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    int deleteSgjsCheckDataCatalogByPks(List<Long> sgjsCheckDataCatalogPkList);
}
