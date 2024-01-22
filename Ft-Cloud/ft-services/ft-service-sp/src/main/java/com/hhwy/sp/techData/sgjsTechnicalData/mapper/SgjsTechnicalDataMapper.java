package com.hhwy.sp.techData.sgjsTechnicalData.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techData.sgjsTechnicalData.domain.SgjsTechnicalData;

/**
 * @author cjh
 * @date 2024-01-22 09:26:47
 * @remark
 */
public interface SgjsTechnicalDataMapper {

    SgjsTechnicalData getSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData);

    List<SgjsTechnicalData> getSgjsTechnicalDataList(SgjsTechnicalData sgjsTechnicalData);

    int insertSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData);

    int insertSgjsTechnicalDataList(@Param("sgjsTechnicalDataList") List<SgjsTechnicalData> sgjsTechnicalDataList);

    int updateSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData);

    int updateSgjsTechnicalDataList(@Param("list") List<SgjsTechnicalData> sgjsTechnicalDataList);

    int deleteSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData);

    int deleteSgjsTechnicalDataByPks(@Param("sgjsTechnicalDataPkList") List<Long> sgjsTechnicalDataPkList);
}
