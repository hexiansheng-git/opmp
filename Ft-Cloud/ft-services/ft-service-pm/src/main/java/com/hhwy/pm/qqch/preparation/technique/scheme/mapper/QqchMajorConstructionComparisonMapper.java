package com.hhwy.pm.qqch.preparation.technique.scheme.mapper;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchMajorConstructionComparison;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:03
 * @remark 3.4.1重大施工方案比选
 */
public interface QqchMajorConstructionComparisonMapper {

    QqchMajorConstructionComparison getQqchMajorConstructionComparison(
        QqchMajorConstructionComparison qqchMajorConstructionComparison);

    List<QqchMajorConstructionComparison> getQqchMajorConstructionComparisonList(
        QqchMajorConstructionComparison qqchMajorConstructionComparison);

    int insertQqchMajorConstructionComparison(QqchMajorConstructionComparison qqchMajorConstructionComparison);

    int insertQqchMajorConstructionComparisonList(
        @Param("qqchMajorConstructionComparisonList") List<QqchMajorConstructionComparison> qqchMajorConstructionComparisonList);

    int updateQqchMajorConstructionComparison(QqchMajorConstructionComparison qqchMajorConstructionComparison);

    int updateQqchMajorConstructionComparisonList(
        @Param("list") List<QqchMajorConstructionComparison> qqchMajorConstructionComparisonList);

    int deleteQqchMajorConstructionComparison(QqchMajorConstructionComparison qqchMajorConstructionComparison);

    int deleteQqchMajorConstructionComparisonByPks(
        @Param("qqchMajorConstructionComparisonPkList") List<Long> qqchMajorConstructionComparisonPkList);
}
