package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.service;

import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.domain.SgsjTechnicalScienceTopicModify;

import java.util.List;


/**
 * @author fsd
 * @date 2024-01-29 14:14:11
 * @remark
 */
public interface ISgsjTechnicalScienceTopicModifyService {

    SgsjTechnicalScienceTopicModify getSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    List<SgsjTechnicalScienceTopicModify> getSgsjTechnicalScienceTopicModifyList(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    int insertSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    int insertSgsjTechnicalScienceTopicModifyList(List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList);

    int updateSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    int updateSgsjTechnicalScienceTopicModifyList(List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList);

    int deleteSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify);

    int deleteSgsjTechnicalScienceTopicModifyByPks(List<Long> sgsjTechnicalScienceTopicModifyPkList);
}
