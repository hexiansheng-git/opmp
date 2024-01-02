package com.hhwy.pm.qqch.preparation.measureexp.range.service;

import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.measureexp.range.dto.QqchMeasureExpDTO;

import java.math.BigDecimal;
import java.util.List;

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

    List<QqchMeasureExpRange> getQqchMeasureExpRangeListByVersion(QqchMeasureExpRange qqchMeasureExpRangeParam);
    

    void saveTreeList(QqchMeasureExpDTO expVO);

    void saveAll(QqchMeasureExpDTO expVO);

    BigDecimal getMaxVersion(BigDecimal version, String dataType);
}
