package com.hhwy.pm.qqch.preparation.technique.manage.service;

import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchTechManageModeComparisonVo;

/**
 * @author zhenglili
 * @date 2023-07-11 15:17:31
 * @remark 3.3.1技术管理模式比选
 */
public interface IQqchTechManageModeComparisonService {

    QqchTechManageModeComparisonVo getQqchTechManageModeComparisonList();

    void batchSave(QqchTechManageModeComparisonVo qqchTechManageModeComparisonVo);
}
