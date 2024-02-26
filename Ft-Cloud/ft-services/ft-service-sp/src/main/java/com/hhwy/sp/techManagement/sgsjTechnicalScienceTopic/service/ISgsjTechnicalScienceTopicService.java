package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service;

import java.util.List;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopicDTO;

/**
 * @author fsd
 * @date 2024-01-29 14:11:17
 * @remark
 */
public interface ISgsjTechnicalScienceTopicService {

    SgsjTechnicalScienceTopic getSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    List<SgsjTechnicalScienceTopic> getSgsjTechnicalScienceTopicList(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    SgsjTechnicalScienceTopic insertSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int insertSgsjTechnicalScienceTopicList(List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList);

    int updateSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int updateSgsjTechnicalScienceTopicList(List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList);

    int deleteSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int deleteSgsjTechnicalScienceTopicByPks(List<Long> sgsjTechnicalScienceTopicPkList);

    SgsjTechnicalScienceTopic getDetail(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    void applyAdd(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    SgsjTechnicalScienceTopic applyDetail(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    void deleteById(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    AjaxResult messagePublic();

    List<SgsjTechnicalScienceTopicDTO> export(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);
}
