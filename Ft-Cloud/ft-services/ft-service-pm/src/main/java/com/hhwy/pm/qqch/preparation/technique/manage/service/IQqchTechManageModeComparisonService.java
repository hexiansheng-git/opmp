package com.hhwy.pm.qqch.preparation.technique.manage.service;

import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchTechManageModeComparison;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-11 15:17:31
 * @remark 3.3.1技术管理模式比选
 */
public interface IQqchTechManageModeComparisonService {

    List<QqchTechManageModeComparison> getQqchTechManageModeComparisonList(
        QqchTechManageModeComparison qqchTechManageModeComparison);

    void batchSave(List<QqchTechManageModeComparison> qqchTechManageModeComparisonList);

    int deleteQqchTechManageModeComparisonByPks(List<Long> qqchTechManageModeComparisonPkList);
}
