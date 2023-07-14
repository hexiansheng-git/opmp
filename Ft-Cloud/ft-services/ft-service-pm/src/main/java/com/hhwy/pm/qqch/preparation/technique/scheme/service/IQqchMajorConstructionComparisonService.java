package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchMajorConstructionComparison;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:03
 * @remark 3.4.1重大施工方案比选
 */
public interface IQqchMajorConstructionComparisonService {

    List<QqchMajorConstructionComparison> getQqchMajorConstructionComparisonList(
        QqchMajorConstructionComparison qqchMajorConstructionComparison);

    void batchSave(List<QqchMajorConstructionComparison> qqchMajorConstructionComparisonList);

    int deleteQqchMajorConstructionComparisonByPks(List<Long> qqchMajorConstructionComparisonPkList);
}
