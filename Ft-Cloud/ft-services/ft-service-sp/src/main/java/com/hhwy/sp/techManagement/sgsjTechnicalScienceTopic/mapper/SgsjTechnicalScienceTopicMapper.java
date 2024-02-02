package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;

/**
 * @author fsd
 * @date 2024-01-29 14:11:17
 * @remark
 */
public interface SgsjTechnicalScienceTopicMapper {

    SgsjTechnicalScienceTopic getSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    List<SgsjTechnicalScienceTopic> getSgsjTechnicalScienceTopicList(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int insertSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int insertSgsjTechnicalScienceTopicList(@Param("sgsjTechnicalScienceTopicList") List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList);

    int updateSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int updateSgsjTechnicalScienceTopicList(@Param("sgsjTechnicalScienceTopicList") List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList);

    int deleteSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int deleteSgsjTechnicalScienceTopicByPks(@Param("sgsjTechnicalScienceTopicPkList") List<Long> sgsjTechnicalScienceTopicPkList);

    int deleteById(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);
}
