package com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.service;

import java.util.List;

import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.domain.SgjsControlPointRetest;

/**
 * 施工技术--测量管理--控制点复测
 *
 * @author lcf
 * @date 2024-03-12 14:54:13
 * @remark
 */
public interface ISgjsControlPointRetestService {

    SgjsControlPointRetest getSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest);

    List<SgjsControlPointRetest> getSgjsControlPointRetestList(SgjsControlPointRetest sgjsControlPointRetest);

    int insertSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest);

    int insertSgjsControlPointRetestList(List<SgjsControlPointRetest> sgjsControlPointRetestList);

    int updateSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest);

    int updateSgjsControlPointRetestList(List<SgjsControlPointRetest> sgjsControlPointRetestList);

    int deleteSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest);

    int deleteSgjsControlPointRetestByPks(List<Long> sgjsControlPointRetestPkList);
}
