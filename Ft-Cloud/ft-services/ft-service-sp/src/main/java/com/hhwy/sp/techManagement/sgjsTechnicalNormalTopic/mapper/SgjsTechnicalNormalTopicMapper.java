package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;

/**
 * @author fsd
 * @date 2024-01-25 10:22:49
 * @remark
 */
public interface SgjsTechnicalNormalTopicMapper {

    SgjsTechnicalNormalTopic getSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    List<SgjsTechnicalNormalTopic> getSgjsTechnicalNormalTopicList(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int insertSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int insertSgjsTechnicalNormalTopicList(@Param("sgjsTechnicalNormalTopicList") List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList);

    int updateSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int updateSgjsTechnicalNormalTopicList(@Param("sgjsTechnicalNormalTopicList") List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList);

    int deleteSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int deleteSgjsTechnicalNormalTopicByPks(@Param("sgjsTechnicalNormalTopicPkList") List<Long> sgjsTechnicalNormalTopicPkList);
}
