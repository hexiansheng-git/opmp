package com.hhwy.pm.qqch.preparation.quality.problem.mapper;

import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemTrain;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:51
 * @remark 9.2.3 质量通病培训策划
 */
public interface QqchQualityProblemTrainMapper {

    QqchQualityProblemTrain getQqchQualityProblemTrain(QqchQualityProblemTrain qqchQualityProblemTrain);

    List<QqchQualityProblemTrain> getQqchQualityProblemTrainList(QqchQualityProblemTrain qqchQualityProblemTrain);

    int insertQqchQualityProblemTrain(QqchQualityProblemTrain qqchQualityProblemTrain);

    int insertQqchQualityProblemTrainList(
        @Param("qqchQualityProblemTrainList") List<QqchQualityProblemTrain> qqchQualityProblemTrainList);

    int updateQqchQualityProblemTrain(QqchQualityProblemTrain qqchQualityProblemTrain);

    int updateQqchQualityProblemTrainList(@Param("list") List<QqchQualityProblemTrain> qqchQualityProblemTrainList);

    int deleteQqchQualityProblemTrain(QqchQualityProblemTrain qqchQualityProblemTrain);

    int deleteQqchQualityProblemTrainByPks(
        @Param("qqchQualityProblemTrainPkList") List<Long> qqchQualityProblemTrainPkList);
}
