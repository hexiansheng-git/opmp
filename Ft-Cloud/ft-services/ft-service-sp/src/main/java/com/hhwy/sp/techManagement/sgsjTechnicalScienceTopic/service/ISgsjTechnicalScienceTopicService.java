package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopicDTO;

import java.util.List;
import java.util.Map;

/**
 * @author fsd
 * @date 2024-01-29 14:11:17
 * @remark
 */
public interface ISgsjTechnicalScienceTopicService {

    SgsjTechnicalScienceTopic getSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    List<SgsjTechnicalScienceTopic> getSgsjTechnicalScienceTopicList(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int insertSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int insertSgsjTechnicalScienceTopicList(List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList);

    int updateSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int updateSgsjTechnicalScienceTopicList(List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList);

    int deleteSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    int deleteSgsjTechnicalScienceTopicByPks(List<Long> sgsjTechnicalScienceTopicPkList);

    SgsjTechnicalScienceTopic getDetail(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    void applyAdd(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    SgsjTechnicalScienceTopic applyDetail(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    void deleteById(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    AjaxResult messagePublic(String title, String message);

    List<SgsjTechnicalScienceTopicDTO> export(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    String getRoleName();

    Map<String, Object> getExpertSuggest(SgsjTechnicalScienceTopic param);

    SgsjTechnicalScienceTopic lxAdd(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam);

    void addLxData(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic);

    void doSendGm(String tenantKey, String loginUserName, ProjectDto projectDto);
}
