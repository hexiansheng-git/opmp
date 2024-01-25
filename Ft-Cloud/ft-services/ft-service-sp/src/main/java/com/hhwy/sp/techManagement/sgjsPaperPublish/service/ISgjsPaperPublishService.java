package com.hhwy.sp.techManagement.sgjsPaperPublish.service;

import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.SgjsPaperPublish;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:37
 * @remark
 */
public interface ISgjsPaperPublishService {

    SgjsPaperPublish getSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    List<SgjsPaperPublish> getSgjsPaperPublishList(SgjsPaperPublish sgjsPaperPublish);

    int insertSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    int insertSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList);

    int updateSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    int updateSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList);

    int deleteSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    int deleteSgjsPaperPublishByPks(List<Long> sgjsPaperPublishPkList);
}
