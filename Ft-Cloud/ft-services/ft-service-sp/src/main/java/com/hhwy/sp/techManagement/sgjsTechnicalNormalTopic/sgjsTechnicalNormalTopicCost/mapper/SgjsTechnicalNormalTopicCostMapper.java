package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;

/**
 * @author fsd
 * @date 2024-01-25 13:25:50
 * @remark
 */
public interface SgjsTechnicalNormalTopicCostMapper {

    SgjsTechnicalNormalTopicCost getSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    List<SgjsTechnicalNormalTopicCost> getSgjsTechnicalNormalTopicCostList(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    int insertSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    int insertSgjsTechnicalNormalTopicCostList(@Param("sgjsTechnicalNormalTopicCostList") List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostList);

    int updateSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    int updateSgjsTechnicalNormalTopicCostList(@Param("sgjsTechnicalNormalTopicCostList") List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostList);

    int deleteSgjsTechnicalNormalTopicCost(SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost);

    int deleteSgjsTechnicalNormalTopicCostByPks(@Param("sgjsTechnicalNormalTopicCostPkList") List<Long> sgjsTechnicalNormalTopicCostPkList);
}
