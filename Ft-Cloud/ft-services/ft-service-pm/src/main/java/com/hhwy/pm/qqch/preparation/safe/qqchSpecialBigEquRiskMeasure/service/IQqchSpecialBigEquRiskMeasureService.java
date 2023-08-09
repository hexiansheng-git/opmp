package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.service;

import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.QqchSpecialBigEquRiskMeasure;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.vo.QqchSpecialBigEquRiskMeasureVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 15:22:12
 * @remark
 */
public interface IQqchSpecialBigEquRiskMeasureService {

    QqchSpecialBigEquRiskMeasure getQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

    QqchSpecialBigEquRiskMeasureVo getQqchSpecialBigEquRiskMeasureList(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

    int insertQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

    int updateQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

    int updateQqchSpecialBigEquRiskMeasureList(List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureList);

    int deleteQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

    int deleteQqchSpecialBigEquRiskMeasureByPks(List<Long> qqchSpecialBigEquRiskMeasurePkList);

    void save(QqchSpecialBigEquRiskMeasureVo vo);
}
