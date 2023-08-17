package com.hhwy.pm.qqch.qqchPerformInspection.mapper;

import java.util.List;

import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspectionDetail;
import org.apache.ibatis.annotations.Param;

/**
 * @author zqq
 * @date 2023-08-17 10:58:17
 * @remark
 */
public interface QqchPerformInspectionDetailMapper {

    QqchPerformInspectionDetail getQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    List<QqchPerformInspectionDetail> getQqchPerformInspectionDetailList(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    int insertQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    int insertQqchPerformInspectionDetailList(@Param("qqchPerformInspectionDetailList") List<QqchPerformInspectionDetail> qqchPerformInspectionDetailList);

    int updateQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    int updateQqchPerformInspectionDetailList(@Param("qqchPerformInspectionDetailList") List<QqchPerformInspectionDetail> qqchPerformInspectionDetailList);

    int deleteQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    int deleteQqchPerformInspectionDetailByPks(@Param("qqchPerformInspectionDetailPkList") List<Long> qqchPerformInspectionDetailPkList);
}
