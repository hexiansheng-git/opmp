package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service;

import java.util.List;

import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;

/**
 * @author fsd
 * @date 2024-01-25 10:22:49
 * @remark
 */
public interface ISgjsTechnicalNormalTopicService {

    SgjsTechnicalNormalTopic getSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    List<SgjsTechnicalNormalTopic> getSgjsTechnicalNormalTopicList(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int insertSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int insertSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList);

    int updateSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int updateSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList);

    int deleteSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int deleteSgjsTechnicalNormalTopicByPks(List<Long> sgjsTechnicalNormalTopicPkList);
}
