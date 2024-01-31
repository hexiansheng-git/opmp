package com.hhwy.sp.common.shjsAuthenticateEvaluate.service;

import java.util.List;

import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;

/**
 * @author fsd
 * @date 2024-01-25 10:17:37
 * @remark
 */
public interface IShjsAuthenticateEvaluateService {

    ShjsAuthenticateEvaluate getShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    List<ShjsAuthenticateEvaluate> getShjsAuthenticateEvaluateList(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    /**
     * 保存评价数据集
     * @param foreignId 外键id
     * @param belongBusiness 所属功能
     * @param saveList 成果数据集
     */
    void saveEvaluate(Long foreignId, String belongBusiness, List<ShjsAuthenticateEvaluate> saveList);

    int insertShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int insertShjsAuthenticateEvaluateList(List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int updateShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int updateShjsAuthenticateEvaluateList(List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int deleteShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int deleteShjsAuthenticateEvaluateByPks(List<Long> shjsAuthenticateEvaluatePkList);

    int saveShjsAuthenticateEvaluateList(Long foreignId, String belongBusiness, List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    List<ShjsAuthenticateEvaluate> getListByForeignIds(Long[] ids);
}
