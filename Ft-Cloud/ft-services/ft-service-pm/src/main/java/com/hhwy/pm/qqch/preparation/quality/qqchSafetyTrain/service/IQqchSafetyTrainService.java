package com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.service;

import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.QqchSafetyTrain;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.vo.QqchSafetyTrainVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-10 18:38:55
 * @remark
 */
public interface IQqchSafetyTrainService {

    QqchSafetyTrain getQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain);

    QqchSafetyTrainVo getQqchSafetyTrainList(QqchSafetyTrain qqchSafetyTrain);

    int insertQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain);

    int updateQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain);

    int updateQqchSafetyTrainList(List<QqchSafetyTrain> qqchSafetyTrainList);

    int deleteQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain);

    int deleteQqchSafetyTrainByPks(List<Long> qqchSafetyTrainPkList);

    void save(QqchSafetyTrainVo vo);

    void insertQqchSafetyTrainList(List<QqchSafetyTrain> qqchSafetyTrainList, BigDecimal version);

    void workGroupSetUpWarn();
}
