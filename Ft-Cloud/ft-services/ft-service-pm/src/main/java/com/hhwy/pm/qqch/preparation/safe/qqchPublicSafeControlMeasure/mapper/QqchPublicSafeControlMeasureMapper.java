package com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.domain.QqchPublicSafeControlMeasure;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 09:30:52
 * @remark 
 */
public interface QqchPublicSafeControlMeasureMapper {
                                                                                                                                                                                                                                                                                                                                                                
    QqchPublicSafeControlMeasure getQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);

    List<QqchPublicSafeControlMeasure> getQqchPublicSafeControlMeasureList(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);

    int insertQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);

    int insertQqchPublicSafeControlMeasureList(@Param("qqchPublicSafeControlMeasureList") List<QqchPublicSafeControlMeasure> qqchPublicSafeControlMeasureList);

    int updateQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);

    int updateQqchPublicSafeControlMeasureList(@Param("qqchPublicSafeControlMeasureList") List<QqchPublicSafeControlMeasure> qqchPublicSafeControlMeasureList);
    
    int deleteQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure);

    int deleteQqchPublicSafeControlMeasureByPks(@Param("qqchPublicSafeControlMeasurePkList") List<Long> qqchPublicSafeControlMeasurePkList);
    }
