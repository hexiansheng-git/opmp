package com.hhwy.sp.techTrain.mapper;

import com.hhwy.sp.techTrain.domain.SgjsTechnicalTraining;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll-技术培训管理
 * @date 2023-12-07 18:05:53
 * @remark
 */
public interface SgjsTechnicalTrainingMapper {

    SgjsTechnicalTraining getSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining);

    List<SgjsTechnicalTraining> getSgjsTechnicalTrainingList(SgjsTechnicalTraining sgjsTechnicalTraining);

    int insertSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining);

    int insertSgjsTechnicalTrainingList(@Param("sgjsTechnicalTrainingList") List<SgjsTechnicalTraining> sgjsTechnicalTrainingList);

    int updateSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining);

    int updateSgjsTechnicalTrainingList(@Param("sgjsTechnicalTrainingList") List<SgjsTechnicalTraining> sgjsTechnicalTrainingList);

    int deleteSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining);

    int deleteSgjsTechnicalTrainingByPks(@Param("sgjsTechnicalTrainingPkList") List<Long> sgjsTechnicalTrainingPkList, @Param("delUser") String delUser);

    List<SgjsTechnicalTraining> getByIds(@Param("ids") List<Long> ids);
}
