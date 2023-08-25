package com.hhwy.pm.qqch.qqchPerformInspection.service;

import java.util.List;
import java.util.Map;

import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspectionDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;

/**
 * @author zqq
 * @date 2023-08-17 10:58:09
 * @remark
 */
public interface IQqchPerformInspectionService {

    QqchPerformInspection getQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    List<QqchPerformInspection> getQqchPerformInspectionList(QqchPerformInspection qqchPerformInspection);

    int insertQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    int insertQqchPerformInspectionList(List<QqchPerformInspection> qqchPerformInspectionList);

    int updateQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    int updateQqchPerformInspectionList(List<QqchPerformInspection> qqchPerformInspectionList);

    int deleteQqchPerformInspection(QqchPerformInspection qqchPerformInspection);

    int deleteQqchPerformInspectionByPks(List<Long> qqchPerformInspectionPkList);

    List<QqchPerformInspectionDetail> getChEditMenuList();

    QqchPerformInspection detail(Long id);

    
    
    
}
