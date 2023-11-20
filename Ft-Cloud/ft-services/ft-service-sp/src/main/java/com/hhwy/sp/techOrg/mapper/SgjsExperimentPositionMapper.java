package com.hhwy.sp.techOrg.mapper;

import java.util.List;

import com.hhwy.sp.techOrg.domain.SgjsExperimentPosition;
import org.apache.ibatis.annotations.Param;


/**
 * @author lcf
 * @date 2023-11-20 11:39:49
 * @remark
 */
public interface SgjsExperimentPositionMapper {

    SgjsExperimentPosition getSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition);

    List<SgjsExperimentPosition> getSgjsExperimentPositionList(SgjsExperimentPosition sgjsExperimentPosition);

    int insertSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition);

    int insertSgjsExperimentPositionList(@Param("sgjsExperimentPositionList") List<SgjsExperimentPosition> sgjsExperimentPositionList);

    int updateSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition);

    int updateSgjsExperimentPositionList(@Param("sgjsExperimentPositionList") List<SgjsExperimentPosition> sgjsExperimentPositionList);

    int deleteSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition);

    int deleteSgjsExperimentPositionByPks(@Param("sgjsExperimentPositionPkList") List<Long> sgjsExperimentPositionPkList);
}
