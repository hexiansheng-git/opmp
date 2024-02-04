package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.service;

import java.util.List;

import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;

/**
 * @author fsd
 * @date 2024-01-25 13:25:50
 * @remark
 */
public interface ISgjsTechnicalNormalTopicCostService {

    SgjsTechnicalNormalTopicCost getSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    List<SgjsTechnicalNormalTopicCost> getSgjsTechnicalNormalTopicCostList(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    int insertSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    void insertSgjsTechnicalNormalTopicCostList(List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostList);

    int updateSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    int updateSgjsTechnicalNormalTopicCostList(List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostList);

    int deleteSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    int deleteSgjsTechnicalNormalTopicCostByPks(List<Long> sgjsTechnicalNormalTopicCostPkList);
}
