package com.hhwy.sp.techData.sgjsTechnicalDataCatalog.service;

import java.util.List;

import com.hhwy.sp.techData.sgjsTechnicalDataCatalog.domain.SgjsTechnicalDataCatalog;

/**
 * @author cjh
 * @date 2024-01-22 09:26:41
 * @remark
 */
public interface ISgjsTechnicalDataCatalogService {

    SgjsTechnicalDataCatalog getSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    List<SgjsTechnicalDataCatalog> getSgjsTechnicalDataCatalogList(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    int insertSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    int insertSgjsTechnicalDataCatalogList(List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogList);

    int updateSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    int updateSgjsTechnicalDataCatalogList(List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogList);

    int deleteSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    int deleteSgjsTechnicalDataCatalogByPks(List<Long> sgjsTechnicalDataCatalogPkList);

    List<SgjsTechnicalDataCatalog> getList(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);
}
