package com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.QqchSafetyTrain;

/**
 * @author ldd
 * @date 2023-08-10 18:38:55
 * @remark 
 */
public interface QqchSafetyTrainMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchSafetyTrain getQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain);

    List<QqchSafetyTrain> getQqchSafetyTrainList(QqchSafetyTrain qqchSafetyTrain);

    int insertQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain);

    int insertQqchSafetyTrainList(@Param("qqchSafetyTrainList") List<QqchSafetyTrain> qqchSafetyTrainList);

    int updateQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain);

            int updateQqchSafetyTrainList(@Param("qqchSafetyTrainList") List<QqchSafetyTrain> qqchSafetyTrainList);
    
    int deleteQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain);

            int deleteQqchSafetyTrainByPks(@Param("qqchSafetyTrainPkList") List<Long> qqchSafetyTrainPkList);
    }
