package com.hhwy.sp.common.sgjsAuthenticateEvaluate.service;

import java.util.List;

import com.hhwy.sp.common.sgjsAuthenticateEvaluate.domain.SgjsAuthenticateEvaluate;

/**
 * @author fsd
 * @date 2024-01-25 10:17:37
 * @remark
 */
public interface ISgjsAuthenticateEvaluateService {

    SgjsAuthenticateEvaluate getShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    List<SgjsAuthenticateEvaluate> getShjsAuthenticateEvaluateList(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    /**
     * 保存评价数据集
     * @param foreignId 外键id
     * @param belongBusiness 所属功能
     * @param saveList 成果数据集
     */
    void saveEvaluate(Long foreignId, String belongBusiness, List<SgjsAuthenticateEvaluate> saveList);

    int insertShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int insertShjsAuthenticateEvaluateList(List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int updateShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int updateShjsAuthenticateEvaluateList(List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    int deleteShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate);

    int deleteShjsAuthenticateEvaluateByPks(List<Long> shjsAuthenticateEvaluatePkList);

    int saveShjsAuthenticateEvaluateList(Long foreignId, String belongBusiness, List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList);

    List<SgjsAuthenticateEvaluate> getListByForeignIds(Long[] ids);
}
