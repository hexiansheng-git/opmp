package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.mapper;

import java.util.List;

import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.domain.SgsjTechnicalScienceTopicApply;
import org.apache.ibatis.annotations.Param;

/**
 * @author fushudong
 * @date 2024-03-05 16:56:06
 * @remark
 */
public interface SgsjTechnicalScienceTopicApplyMapper {

    SgsjTechnicalScienceTopicApply getSgsjTechnicalScienceTopicApply(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply);

    List<SgsjTechnicalScienceTopicApply> getSgsjTechnicalScienceTopicApplyList(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply);

    int insertSgsjTechnicalScienceTopicApply(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply);

    int insertSgsjTechnicalScienceTopicApplyList(@Param("sgsjTechnicalScienceTopicApplyList") List<SgsjTechnicalScienceTopicApply> sgsjTechnicalScienceTopicApplyList);

    int updateSgsjTechnicalScienceTopicApply(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply);

    int updateSgsjTechnicalScienceTopicApplyList(@Param("sgsjTechnicalScienceTopicApplyList") List<SgsjTechnicalScienceTopicApply> sgsjTechnicalScienceTopicApplyList);

    int deleteSgsjTechnicalScienceTopicApply(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply);

    int deleteSgsjTechnicalScienceTopicApplyByPks(@Param("sgsjTechnicalScienceTopicApplyPkList") List<Long> sgsjTechnicalScienceTopicApplyPkList);
}
