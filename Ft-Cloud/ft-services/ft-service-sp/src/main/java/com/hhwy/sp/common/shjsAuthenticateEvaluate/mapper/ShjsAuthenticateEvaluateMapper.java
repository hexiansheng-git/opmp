package com.hhwy.sp.common.shjsAuthenticateEvaluate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;

/**
 * @author fsd
 * @date 2024-01-25 10:17:37
 * @remark
 */
public interface ShjsAuthenticateEvaluateMapper {

    ShjsAuthenticateEvaluate getShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    List<ShjsAuthenticateEvaluate> getShjsAuthenticateEvaluateList(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int insertShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int insertShjsAuthenticateEvaluateList(@Param("shjsAuthenticateEvaluateList") List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int updateShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int updateShjsAuthenticateEvaluateList(@Param("shjsAuthenticateEvaluateList") List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int deleteShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int deleteShjsAuthenticateEvaluateByPks(@Param("shjsAuthenticateEvaluatePkList") List<Long> shjsAuthenticateEvaluatePkList);
}
