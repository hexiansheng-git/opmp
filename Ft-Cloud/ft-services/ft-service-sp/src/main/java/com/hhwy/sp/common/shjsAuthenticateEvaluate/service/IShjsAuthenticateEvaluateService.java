package com.hhwy.sp.common.shjsAuthenticateEvaluate.service;

import java.util.List;

import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;

/**
 * @author fsd
 * @date 2024-01-25 10:17:37
 * @remark
 */
public interface IShjsAuthenticateEvaluateService {

    ShjsAuthenticateEvaluate getShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    List<ShjsAuthenticateEvaluate> getShjsAuthenticateEvaluateList(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int insertShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int insertShjsAuthenticateEvaluateList(List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int updateShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int updateShjsAuthenticateEvaluateList(List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int deleteShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int deleteShjsAuthenticateEvaluateByPks(List<Long> shjsAuthenticateEvaluatePkList);
}
