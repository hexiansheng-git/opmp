package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;

import javax.servlet.http.HttpServletResponse;

/**
 * @author fsd
 * @date 2024-01-25 10:22:49
 * @remark
 */
public interface ISgjsTechnicalNormalTopicService {

    SgjsTechnicalNormalTopic getSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    List<SgjsTechnicalNormalTopic> getSgjsTechnicalNormalTopicList(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int insertSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    void insertSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList);

    int updateSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int updateSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList);

    int deleteSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic);

    int deleteSgjsTechnicalNormalTopicByPks(List<Long> sgjsTechnicalNormalTopicPkList);

    void export(HttpServletResponse response, SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) throws Exception;

    AjaxResult importData(List<Map<Integer, String>> headList, List<Map<Integer, String>> dataList);
}
