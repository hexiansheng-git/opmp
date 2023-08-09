package com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.service;

import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.domain.QqchPublicSafeControlMeasure;
import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.domain.vo.QqchPublicSafeControlMeasureVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 09:30:52
 * @remark
 */
public interface IQqchPublicSafeControlMeasureService {

    QqchPublicSafeControlMeasure getQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);

    QqchPublicSafeControlMeasureVo getQqchPublicSafeControlMeasureList(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);

    int insertQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);


    int updateQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);

    int updateQqchPublicSafeControlMeasureList(List<QqchPublicSafeControlMeasure> qqchPublicSafeControlMeasureList);

    int deleteQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);

    int deleteQqchPublicSafeControlMeasureByPks(List<Long> qqchPublicSafeControlMeasurePkList);

    void save(QqchPublicSafeControlMeasureVo vo);
}
