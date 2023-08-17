package com.hhwy.pm.qqch.qqchPerformInspection.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;

/**
 * @author zqq
 * @date 2023-08-17 10:58:09
 * @remark 
 */
public interface QqchPerformInspectionMapper {
                                                                                                                                                                                                                                                                
    QqchPerformInspection getQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    List<QqchPerformInspection> getQqchPerformInspectionList(QqchPerformInspection qqchPerformInspection);

    int insertQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    int insertQqchPerformInspectionList(@Param("qqchPerformInspectionList") List<QqchPerformInspection> qqchPerformInspectionList);

    int updateQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

            int updateQqchPerformInspectionList(@Param("qqchPerformInspectionList") List<QqchPerformInspection> qqchPerformInspectionList);
    
    int deleteQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

            int deleteQqchPerformInspectionByPks(@Param("qqchPerformInspectionPkList") List<Long> qqchPerformInspectionPkList);
    }
