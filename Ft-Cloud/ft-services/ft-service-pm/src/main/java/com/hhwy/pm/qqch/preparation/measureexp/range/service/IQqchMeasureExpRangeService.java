package com.hhwy.pm.qqch.preparation.measureexp.range.service;

import java.util.List;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;

/**
 * @author mls
 * @date 2023-07-25 18:01:34
 * @remark 
 */
public interface IQqchMeasureExpRangeService {
                                                                                                                                                                                                                                                                                        
    QqchMeasureExpRange getQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange);

    List<QqchMeasureExpRange> getQqchMeasureExpRangeList(QqchMeasureExpRange qqchMeasureExpRange);

    int insertQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange);

    int insertQqchMeasureExpRangeList(List<QqchMeasureExpRange> qqchMeasureExpRangeList);

    int updateQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange);

            int updateQqchMeasureExpRangeList(List<QqchMeasureExpRange> qqchMeasureExpRangeList);
    
    int deleteQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange);

            int deleteQqchMeasureExpRangeByPks(List<Long> qqchMeasureExpRangePkList);
    }
