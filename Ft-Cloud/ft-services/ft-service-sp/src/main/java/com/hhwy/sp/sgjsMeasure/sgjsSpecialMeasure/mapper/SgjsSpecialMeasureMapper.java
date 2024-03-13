package com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.domain.SgjsSpecialMeasure;

/**
 * 施工技术--测量管理--特殊工程监控量测
 *
 * @author lcf
 * @date 2024-03-12 14:54:37
 * @remark
 */
public interface SgjsSpecialMeasureMapper {

    SgjsSpecialMeasure getSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure);

    List<SgjsSpecialMeasure> getSgjsSpecialMeasureList(SgjsSpecialMeasure sgjsSpecialMeasure);

    int insertSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure);

    int insertSgjsSpecialMeasureList(@Param("sgjsSpecialMeasureList") List<SgjsSpecialMeasure> sgjsSpecialMeasureList);

    int updateSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure);

    int updateSgjsSpecialMeasureList(@Param("sgjsSpecialMeasureList") List<SgjsSpecialMeasure> sgjsSpecialMeasureList);

    int deleteSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure);

    int deleteSgjsSpecialMeasureByPks(@Param("sgjsSpecialMeasurePkList") List<Long> sgjsSpecialMeasurePkList);
}
