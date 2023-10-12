package com.hhwy.pm.qqch.qqchPerformInspection.mapper;

import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zqq
 * @date 2023-08-17 10:58:09
 * @remark
 */
@Repository
public interface QqchPerformInspectionMapper {

    QqchPerformInspection getQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    List<QqchPerformInspection> getQqchPerformInspectionList(QqchPerformInspection qqchPerformInspection);

    List<Map> getSummaryList();

    int insertQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    int insertQqchPerformInspectionList(@Param("qqchPerformInspectionList") List<QqchPerformInspection> qqchPerformInspectionList);

    int updateQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    int updateQqchPerformInspectionList(@Param("qqchPerformInspectionList") List<QqchPerformInspection> qqchPerformInspectionList);

    int deleteQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    int deleteQqchPerformInspectionByPks(@Param("qqchPerformInspectionPkList") List<Long> qqchPerformInspectionPkList);
}
