package com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.domain.SgjsControlPointRetest;

/**
 * 施工技术--测量管理--控制点复测
 *
 * @author lcf
 * @date 2024-03-12 14:54:13
 * @remark
 */
public interface SgjsControlPointRetestMapper {

    SgjsControlPointRetest getSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest);

    List<SgjsControlPointRetest> getSgjsControlPointRetestList(SgjsControlPointRetest sgjsControlPointRetest);

    int insertSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest);

    int insertSgjsControlPointRetestList(@Param("sgjsControlPointRetestList") List<SgjsControlPointRetest> sgjsControlPointRetestList);

    int updateSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest);

    int updateSgjsControlPointRetestList(@Param("sgjsControlPointRetestList") List<SgjsControlPointRetest> sgjsControlPointRetestList);

    int deleteSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest);

    int deleteSgjsControlPointRetestByPks(@Param("sgjsControlPointRetestPkList") List<Long> sgjsControlPointRetestPkList);
}
