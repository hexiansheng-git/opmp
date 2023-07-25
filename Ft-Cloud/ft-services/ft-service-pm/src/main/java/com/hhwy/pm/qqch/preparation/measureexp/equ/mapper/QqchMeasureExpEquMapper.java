package com.hhwy.pm.qqch.preparation.measureexp.equ.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.QqchMeasureExpEqu;
import org.apache.ibatis.annotations.Param;

/**
 * @author mls
 * @date 2023-07-25 18:00:16
 * @remark 
 */
public interface QqchMeasureExpEquMapper {
                                                                                                                                                                                                                                                                                                                                                    
    QqchMeasureExpEqu getQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

    List<QqchMeasureExpEqu> getQqchMeasureExpEquList(QqchMeasureExpEqu qqchMeasureExpEqu);

    int insertQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

    int insertQqchMeasureExpEquList(@Param("qqchMeasureExpEquList") List<QqchMeasureExpEqu> qqchMeasureExpEquList);

    int updateQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

            int updateQqchMeasureExpEquList(@Param("qqchMeasureExpEquList") List<QqchMeasureExpEqu> qqchMeasureExpEquList);
    
    int deleteQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

            int deleteQqchMeasureExpEquByPks(@Param("qqchMeasureExpEquPkList") List<Long> qqchMeasureExpEquPkList);
    }
