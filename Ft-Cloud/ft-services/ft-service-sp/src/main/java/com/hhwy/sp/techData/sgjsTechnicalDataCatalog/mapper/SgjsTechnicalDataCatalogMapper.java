package com.hhwy.sp.techData.sgjsTechnicalDataCatalog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techData.sgjsTechnicalDataCatalog.domain.SgjsTechnicalDataCatalog;

/**
 * @author cjh
 * @date 2024-01-22 09:26:41
 * @remark
 */
public interface SgjsTechnicalDataCatalogMapper {

    SgjsTechnicalDataCatalog getSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    List<SgjsTechnicalDataCatalog> getSgjsTechnicalDataCatalogList(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    int insertSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    int insertSgjsTechnicalDataCatalogList(@Param("sgjsTechnicalDataCatalogList") List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogList);

    int updateSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    int updateSgjsTechnicalDataCatalogList(@Param("list") List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogList);

    int deleteSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog);

    int deleteSgjsTechnicalDataCatalogByPks(@Param("sgjsTechnicalDataCatalogPkList") List<Long> sgjsTechnicalDataCatalogPkList);
}
