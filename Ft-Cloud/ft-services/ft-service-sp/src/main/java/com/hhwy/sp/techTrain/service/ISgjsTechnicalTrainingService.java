package com.hhwy.sp.techTrain.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.techTrain.domain.SgjsTechnicalTraining;

import java.util.List;

/**
 * @author wll-技术培训管理
 * @date 2023-12-07 18:05:53
 * @remark
 */
public interface ISgjsTechnicalTrainingService {

    SgjsTechnicalTraining getSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining);

    /**
     * 列表查询
     * @param sgjsTechnicalTraining
     * @return
     */
    List<SgjsTechnicalTraining> getSgjsTechnicalTrainingList(SgjsTechnicalTraining sgjsTechnicalTraining);

    AjaxResult insertSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining);

    int insertSgjsTechnicalTrainingList(List<SgjsTechnicalTraining> sgjsTechnicalTrainingList);

    int updateSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining);

    int updateSgjsTechnicalTrainingList(List<SgjsTechnicalTraining> sgjsTechnicalTrainingList);

    int deleteSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining);

    int deleteSgjsTechnicalTrainingByPks(List<Long> sgjsTechnicalTrainingPkList);
}
