package com.hhwy.pm.qqch.preparation.technique.manage.mapper;

import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchTechManageModeComparison;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-11 15:17:31
 * @remark 3.3.1技术管理模式比选
 */
public interface QqchTechManageModeComparisonMapper {

    QqchTechManageModeComparison getQqchTechManageModeComparison(
        QqchTechManageModeComparison qqchTechManageModeComparison);

    List<QqchTechManageModeComparison> getQqchTechManageModeComparisonList(
        QqchTechManageModeComparison qqchTechManageModeComparison);

    int insertQqchTechManageModeComparison(QqchTechManageModeComparison qqchTechManageModeComparison);

    int insertQqchTechManageModeComparisonList(
        @Param("qqchTechManageModeComparisonList") List<QqchTechManageModeComparison> qqchTechManageModeComparisonList);

    int updateQqchTechManageModeComparison(QqchTechManageModeComparison qqchTechManageModeComparison);

    int updateQqchTechManageModeComparisonList(
        @Param("list") List<QqchTechManageModeComparison> qqchTechManageModeComparisonList);

    int deleteQqchTechManageModeComparison(QqchTechManageModeComparison qqchTechManageModeComparison);

    int deleteQqchTechManageModeComparisonByPks(
        @Param("qqchTechManageModeComparisonPkList") List<Long> qqchTechManageModeComparisonPkList);
}
