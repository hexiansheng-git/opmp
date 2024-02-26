package com.hhwy.sp.common.sgjsAuthenticateEvaluate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.domain.SgjsAuthenticateEvaluate;

/**
 * @author fsd
 * @date 2024-01-25 10:17:37
 * @remark
 */
public interface SgjsAuthenticateEvaluateMapper {

    SgjsAuthenticateEvaluate getShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    List<SgjsAuthenticateEvaluate> getShjsAuthenticateEvaluateList(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int insertShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int insertShjsAuthenticateEvaluateList(@Param("shjsAuthenticateEvaluateList") List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int updateShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int updateShjsAuthenticateEvaluateList(@Param("shjsAuthenticateEvaluateList") List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int deleteShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int deleteShjsAuthenticateEvaluateByPks(@Param("shjsAuthenticateEvaluatePkList") List<Long> shjsAuthenticateEvaluatePkList);

    List<SgjsAuthenticateEvaluate> getListByForeignList(@Param("foreignIds") List<Long> foreignIds);
}
