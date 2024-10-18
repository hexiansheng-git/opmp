package com.hhwy.sp.techFile.sgjsCheckDataCatalog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techFile.sgjsCheckDataCatalog.domain.SgjsCheckDataCatalog;

/**
 * @author xuzl
 * @date 2024-10-18 16:45:49
 * @remark
 */
public interface SgjsCheckDataCatalogMapper {

    SgjsCheckDataCatalog getSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    List<SgjsCheckDataCatalog> getSgjsCheckDataCatalogList(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    int insertSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    int insertSgjsCheckDataCatalogList(@Param("sgjsCheckDataCatalogList") List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList);

    int updateSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    int updateSgjsCheckDataCatalogList(@Param("sgjsCheckDataCatalogList") List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList);

    int deleteSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog);

    int deleteSgjsCheckDataCatalogByPks(@Param("sgjsCheckDataCatalogPkList") List<Long> sgjsCheckDataCatalogPkList);
}
