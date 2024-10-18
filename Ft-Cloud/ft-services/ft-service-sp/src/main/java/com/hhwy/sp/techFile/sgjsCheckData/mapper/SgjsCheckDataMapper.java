package com.hhwy.sp.techFile.sgjsCheckData.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techFile.sgjsCheckData.domain.SgjsCheckData;

/**
 * @author xuzl
 * @date 2024-10-18 15:52:00
 * @remark
 */
public interface SgjsCheckDataMapper {

    SgjsCheckData getSgjsCheckData(SgjsCheckData sgjsCheckData);

    List<SgjsCheckData> getSgjsCheckDataList(SgjsCheckData sgjsCheckData);

    int insertSgjsCheckData(SgjsCheckData sgjsCheckData);

    int insertSgjsCheckDataList(@Param("sgjsCheckDataList") List<SgjsCheckData> sgjsCheckDataList);

    int updateSgjsCheckData(SgjsCheckData sgjsCheckData);

    int updateSgjsCheckDataList(@Param("sgjsCheckDataList") List<SgjsCheckData> sgjsCheckDataList);

    int deleteSgjsCheckData(SgjsCheckData sgjsCheckData);

    int deleteSgjsCheckDataByPks(@Param("sgjsCheckDataPkList") List<Long> sgjsCheckDataPkList);
}
