package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.mapper;

import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.domain.SgsjTechnicalScienceTopicModify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fsd
 * @date 2024-01-29 14:14:11
 * @remark
 */
public interface SgsjTechnicalScienceTopicModifyMapper {

    SgsjTechnicalScienceTopicModify getSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    List<SgsjTechnicalScienceTopicModify> getSgsjTechnicalScienceTopicModifyList(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    int insertSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    int insertSgsjTechnicalScienceTopicModifyList(@Param("sgsjTechnicalScienceTopicModifyList") List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList);

    int updateSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    int updateSgsjTechnicalScienceTopicModifyList(@Param("sgsjTechnicalScienceTopicModifyList") List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList);

    int deleteSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    int deleteSgsjTechnicalScienceTopicModifyByPks(@Param("sgsjTechnicalScienceTopicModifyPkList") List<Long> sgsjTechnicalScienceTopicModifyPkList);

    SgsjTechnicalScienceTopicModify getMaxCreateTimeDataByModifyContent(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);
}
