package com.hhwy.pm.qqch.preparation.measureexp.range.mapper;

import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-25 18:01:34
 * @remark
 */
public interface QqchMeasureExpRangeMapper {

    QqchMeasureExpRange getQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange);

    List<QqchMeasureExpRange> getQqchMeasureExpRangeList(QqchMeasureExpRange qqchMeasureExpRange);

    int insertQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange);

    int insertQqchMeasureExpRangeList(@Param("qqchMeasureExpRangeList") List<QqchMeasureExpRange> qqchMeasureExpRangeList);

    int updateQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange);

    int updateQqchMeasureExpRangeList(@Param("qqchMeasureExpRangeList") List<QqchMeasureExpRange> qqchMeasureExpRangeList);

    int deleteQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange);

    int deleteQqchMeasureExpRangeByPks(@Param("qqchMeasureExpRangePkList") List<Long> qqchMeasureExpRangePkList);
}
