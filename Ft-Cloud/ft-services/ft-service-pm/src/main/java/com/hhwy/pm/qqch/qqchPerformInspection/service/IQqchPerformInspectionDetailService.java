package com.hhwy.pm.qqch.qqchPerformInspection.service;

import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspectionDetail;

import java.util.List;

/**
 * @author zqq
 * @date 2023-08-17 10:58:17
 * @remark
 */
public interface IQqchPerformInspectionDetailService {

    QqchPerformInspectionDetail getQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    List<QqchPerformInspectionDetail> getQqchPerformInspectionDetailList(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    int insertQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    int insertQqchPerformInspectionDetailList(List<QqchPerformInspectionDetail> qqchPerformInspectionDetailList);

    int updateQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    int updateQqchPerformInspectionDetailList(List<QqchPerformInspectionDetail> qqchPerformInspectionDetailList);

    int deleteQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail);

    int deleteQqchPerformInspectionDetailByPks(List<Long> qqchPerformInspectionDetailPkList);
}
