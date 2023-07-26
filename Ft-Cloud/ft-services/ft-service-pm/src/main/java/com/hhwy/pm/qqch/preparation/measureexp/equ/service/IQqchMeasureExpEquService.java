package com.hhwy.pm.qqch.preparation.measureexp.equ.service;

import java.util.List;
import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.QqchMeasureExpEqu;

/**
 * @author mls
 * @date 2023-07-25 18:00:16
 * @remark 
 */
public interface IQqchMeasureExpEquService {
                                                                                                                                                                                                                                                                                                                                                    
    QqchMeasureExpEqu getQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

    List<QqchMeasureExpEqu> getQqchMeasureExpEquList(QqchMeasureExpEqu qqchMeasureExpEqu);

    int insertQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

    int insertQqchMeasureExpEquList(List<QqchMeasureExpEqu> qqchMeasureExpEquList);

    int updateQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

            int updateQqchMeasureExpEquList(List<QqchMeasureExpEqu> qqchMeasureExpEquList);
    
    int deleteQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

            int deleteQqchMeasureExpEquByPks(List<Long> qqchMeasureExpEquPkList);
    }
