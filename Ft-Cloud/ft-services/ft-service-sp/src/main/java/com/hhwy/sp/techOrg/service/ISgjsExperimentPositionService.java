package com.hhwy.sp.techOrg.service;

import com.hhwy.sp.techOrg.domain.SgjsExperimentPosition;

import java.util.List;

/**
 * @author lcf
 * @date 2023-11-20 11:39:49
 * @remark
 */
public interface ISgjsExperimentPositionService {

    SgjsExperimentPosition getSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition);

    List<SgjsExperimentPosition> getSgjsExperimentPositionList(SgjsExperimentPosition sgjsExperimentPosition);

    int insertSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition);

    int insertSgjsExperimentPositionList(List<SgjsExperimentPosition> sgjsExperimentPositionList);

    int updateSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition);

    int updateSgjsExperimentPositionList(List<SgjsExperimentPosition> sgjsExperimentPositionList);

    int deleteSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition);

    int deleteSgjsExperimentPositionByPks(List<Long> sgjsExperimentPositionPkList);
}
