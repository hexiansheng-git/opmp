package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.QqchSpecialBigEquRiskMeasure;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 15:22:12
 * @remark 
 */
public interface QqchSpecialBigEquRiskMeasureMapper {
                                                                                                                                                                                                                                                                                                                                                                
    QqchSpecialBigEquRiskMeasure getQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

    List<QqchSpecialBigEquRiskMeasure> getQqchSpecialBigEquRiskMeasureList(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

    int insertQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

    int insertQqchSpecialBigEquRiskMeasureList(@Param("qqchSpecialBigEquRiskMeasureList") List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureList);

    int updateQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

            int updateQqchSpecialBigEquRiskMeasureList(@Param("qqchSpecialBigEquRiskMeasureList") List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureList);
    
    int deleteQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure);

            int deleteQqchSpecialBigEquRiskMeasureByPks(@Param("qqchSpecialBigEquRiskMeasurePkList") List<Long> qqchSpecialBigEquRiskMeasurePkList);
    }
